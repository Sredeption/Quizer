package hku.cs.smp.quizer.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.Toast;
import hku.cs.smp.quizer.R;
import hku.cs.smp.quizer.question.LinearQuestion;
import hku.cs.smp.quizer.question.QuadraticQuestion;
import hku.cs.smp.quizer.question.Question;
import hku.cs.smp.quizer.summary.SummaryActivity;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private QuizAdapter quizAdapter;
    private CoordinatorLayout mainContent;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    private ProgressBar progressBar;

    private Chronometer chronometer;
    private Long selectTime;
    private int selectFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mainContent = (CoordinatorLayout) findViewById(R.id.main_content);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        final ArrayList<Question> questions = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            questions.add(LinearQuestion.random());
        }
        for (int i = 0; i < 5; i++) {
            questions.add(QuadraticQuestion.random());
        }
        quizAdapter = new QuizAdapter(getSupportFragmentManager(), questions);


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(quizAdapter);
        progressBar.setProgress((mViewPager.getCurrentItem() + 1) * 100 / questions.size());
        selectFrag = mViewPager.getCurrentItem();
        selectTime = System.currentTimeMillis();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Long currentTime = System.currentTimeMillis();
                questions.get(selectFrag).addDuration(currentTime - selectTime);
                selectTime = currentTime;
                selectFrag = position;
                progressBar.setProgress((position + 1) * 100 / questions.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        FloatingActionButton submitButton = (FloatingActionButton) findViewById(R.id.quiz_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Question unSubmittedQuestion = check(questions);
                if (unSubmittedQuestion == null) {
                    chronometer.stop();
                    Intent intent = new Intent(QuizActivity.this, SummaryActivity.class);
                    intent.putExtra(Question.QUESTION, questions);
                    intent.putExtra(Question.DURATION, chronometer.getText().toString());
                    startActivity(intent);
                    finish();
                } else if (!quizAdapter.getCurrentFragment().isSubmitted()) {
                    quizAdapter.getCurrentFragment().submit();
                } else {
                    mViewPager.setCurrentItem(unSubmittedQuestion.getPosition(), true);
                    Snackbar.make(mainContent, R.string.warning_submit, Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        chronometer.start();
    }

    private Question check(ArrayList<Question> questions) {
        for (Question q : questions) {
            if (!q.isSubmitted()) {
                return q;
            }
        }
        return null;
    }

}

package hku.cs.smp.quizer.summary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;
import hku.cs.smp.quizer.R;
import hku.cs.smp.quizer.question.LinearQuestion;
import hku.cs.smp.quizer.question.QuadraticQuestion;
import hku.cs.smp.quizer.question.Question;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SummaryActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ArrayList<Question> questions = (ArrayList<Question>) getIntent().getSerializableExtra(Question.QUESTION);
        String duration = getIntent().getStringExtra(Question.DURATION);
        SummaryAdapter summaryAdapter = new SummaryAdapter(this, questions);
        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        TextView durationView = (TextView) findViewById(R.id.duration);
        Long durationForLinear = 0L;
        int linearCorrect = 0;
        int linearWrong = 0;
        int linearGiveUp = 0;

        Long durationForQuadratic = 0L;
        int quadraticCorrect = 0;
        int quadraticWrong = 0;
        int quadraticGiveup = 0;

        for (Question question : questions) {
            if (question instanceof LinearQuestion) {
                if (question.isGiveUp()) {
                    linearGiveUp += 1;
                } else {
                    durationForLinear += question.getDuration();
                    if (question.isCorrect())
                        linearCorrect += 1;
                    else
                        linearWrong += 1;
                }
            } else if (question instanceof QuadraticQuestion) {
                if (question.isGiveUp()) {
                    quadraticGiveup += 1;
                } else {
                    durationForQuadratic += question.getDuration();
                    if (question.isCorrect())
                        quadraticCorrect += 1;
                    else
                        quadraticWrong += 1;
                }
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss", Locale.getDefault());
        String linearTimeText;
        if (linearCorrect + linearWrong != 0) {
            linearTimeText = sdf.format(new Date(durationForLinear / (linearCorrect + linearWrong)));
        } else {
            linearTimeText = "N/A";
        }
        String quadraticTimeText;
        if (quadraticCorrect + quadraticWrong != 0) {
            quadraticTimeText = sdf.format(new Date(durationForQuadratic / (quadraticCorrect + quadraticWrong)));
        } else {
            quadraticTimeText = "N/A";
        }

        durationView.setText(String.format("Total time:\n" +
                        "%s\n" +
                        "Average time for Linear Equation:\n" +
                        "%s(Correct:%d, Wrong:%d, Give up:%d)\n" +
                        "Average time for Quadratic Equation:\n" +
                        "%s(Correct:%d, Wrong:%d, Give up:%d)",
                duration,
                linearTimeText, linearCorrect, linearWrong, linearGiveUp,
                quadraticTimeText, quadraticCorrect, quadraticWrong, quadraticGiveup));
        list.setAdapter(summaryAdapter);
    }
}

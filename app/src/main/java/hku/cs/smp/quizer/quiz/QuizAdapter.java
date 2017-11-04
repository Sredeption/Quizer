package hku.cs.smp.quizer.quiz;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import hku.cs.smp.quizer.question.Question;

import java.util.ArrayList;

public class QuizAdapter extends FragmentPagerAdapter {
    private ArrayList<Question> questions;
    private QuestionFragment currentFragment;

    public QuizAdapter(FragmentManager fm, ArrayList<Question> questions) {
        super(fm);
        this.questions = questions;
    }

    @Override
    public Fragment getItem(int position) {
        Question question = questions.get(position);
        question.setPosition(position);
        return QuestionFragment.newInstance(question);
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        currentFragment = (QuestionFragment) object;
    }

    public QuestionFragment getCurrentFragment() {
        return currentFragment;
    }
}

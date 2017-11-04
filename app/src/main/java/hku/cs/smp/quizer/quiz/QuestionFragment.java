package hku.cs.smp.quizer.quiz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import hku.cs.smp.quizer.R;
import hku.cs.smp.quizer.question.Question;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class QuestionFragment extends Fragment {
    private Question question;
    private ViewGroup quizView;
    private List<EditText> editTexts;

    public QuestionFragment() {
        editTexts = new ArrayList<>();
    }

    static QuestionFragment newInstance(Question question) {
        QuestionFragment fragment = new QuestionFragment();
        fragment.question = question;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (quizView != null)
            return quizView;
        quizView = (ViewGroup) inflater.inflate(R.layout.fragment_quiz, container, false);
        TextView questionView = quizView.findViewById(R.id.question_view);
        questionView.setText(question.getQuestion());
        System.out.println(question.getQuestion());
        for (Double a : question.getCorrectAnswers()) {
            System.out.println(a);
        }

        if (question.isSingleRoot()) {
            TextInputLayout xBox = (TextInputLayout) inflater.inflate(R.layout.answer_box, quizView, false);
            EditText xEditBox = xBox.findViewById(R.id.answer);
            xBox.setHint(getResources().getString(R.string.prompt_x));
            editTexts.add(xEditBox);
            quizView.addView(xBox);
        } else {

            TextInputLayout x1Box = (TextInputLayout) inflater.inflate(R.layout.answer_box, quizView, false);
            EditText x1EditBox = x1Box.findViewById(R.id.answer);
            x1Box.setHint(getResources().getString(R.string.prompt_x1));
            quizView.addView(x1Box);
            editTexts.add(x1EditBox);

            TextInputLayout x2Box = (TextInputLayout) inflater.inflate(R.layout.answer_box, quizView, false);
            EditText x2EditBox = x2Box.findViewById(R.id.answer);
            x2Box.setHint(getResources().getString(R.string.prompt_x2));
            quizView.addView(x2Box);
            editTexts.add(x2EditBox);

        }

        return quizView;
    }

    void submit() {
        if (question.isSubmitted())
            return;

        for (EditText e : editTexts) {
            String content = e.getText().toString();
            if (TextUtils.isEmpty(content)) {
                continue;
            }
            try {
                Double.parseDouble(content);
            } catch (Exception ex) {
                e.setError("Input must be valid decimal.");
                e.requestFocus();
                return;
            }

            int point = content.indexOf('.');
            if (point != -1 && content.length() - point > 3) {
                e.setError("Please round the answer to 2 decimal places.");
                e.requestFocus();
                return;
            }
        }

        for (EditText e : editTexts) {
            e.setFocusable(false);
        }

        List<Double> answers = new ArrayList<>(question.getCorrectAnswers());
        boolean correct = true;

        String c1 = editTexts.get(0).getText().toString();
        if (!TextUtils.isEmpty(c1)) {
            Double a1 = Double.parseDouble(c1);
            question.answerX1(a1);
            if (!checkAnswer(answers, a1)) {
                editTexts.get(0).setError(getString(R.string.wrong_answer));
                correct = false;
            } else {
                editTexts.get(0).setTextColor(getResources().getColor(R.color.answerColor));
            }
        } else {
            editTexts.get(0).setError(getString(R.string.wrong_answer));
            correct = false;
            question.giveUp();
        }


        if (editTexts.size() == 2) {
            String c2 = editTexts.get(1).getText().toString();
            if (!TextUtils.isEmpty(c2)) {
                Double a2 = Double.parseDouble(c2);
                question.answerX2(Double.parseDouble(c2));
                if (!checkAnswer(answers, a2)) {
                    editTexts.get(1).setError(getString(R.string.wrong_answer));
                    correct = false;
                } else {
                    editTexts.get(1).setTextColor(getResources().getColor(R.color.answerColor));
                }
            } else {
                editTexts.get(1).setError(getString(R.string.wrong_answer));
                correct = false;
                question.giveUp();
            }
        }


        question.submit();
        if (correct) {
            question.correct();
            return;
        }
        LinearLayout answerLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.correct_answer, quizView, false);
        TextView correctAnswer = answerLayout.findViewById(R.id.correct_answer);
        StringBuilder res = new StringBuilder();
        List<Double> list = question.getCorrectAnswers();
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(2);
        format.setRoundingMode(RoundingMode.HALF_UP);
        if (question.isSingleRoot()) {
            res.append("x=").append(format.format(list.get(0)));
        } else {
            res
                    .append("x1=").append(format.format(list.get(0))).append("\n")
                    .append("x2=").append(format.format(list.get(1)));
        }
        correctAnswer.setText(res.toString());
        quizView.addView(answerLayout);
    }

    public boolean isSubmitted() {
        return question.isSubmitted();
    }

    private boolean areEqual(Double x, Double y) {
        return Math.abs(x - y) < 1e-6;
    }

    private boolean checkAnswer(List<Double> answers, Double answer) {
        for (Double a : answers) {
            if (areEqual(a, answer)) {
                answers.remove(a);
                return true;
            }
        }
        return false;
    }

}

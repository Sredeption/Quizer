package hku.cs.smp.quizer.question;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class LinearQuestion extends Question {
    private int a, b;
    private Double x, ans;

    public LinearQuestion(int a, int b) {
        this.a = a;
        this.b = b;
        this.ans = round((double) -b / a);
    }

    public static LinearQuestion random() {
        Random random = new Random();
        int a, b;
        for (; ; ) {
            a = random.nextInt(198) - 99;
            b = random.nextInt(198) - 99;
            if (a != 0)
                break;
        }

        return new LinearQuestion(a, b);
    }

    @Override
    public String getQuestion() {
        StringBuilder questionBuilder = new StringBuilder();
        questionBuilder.append(a).append("x");
        if (b > 0) {
            questionBuilder.append("+");
        }
        if (b != 0)
            questionBuilder.append(b);
        questionBuilder.append("=0");
        return questionBuilder.toString();
    }

    @Override
    public void answerX1(Double x1) {
        this.x = x1;
    }

    @Override
    public void answerX2(Double x2) {
    }

    @Override
    public void answer(Double x1, Double x2) {

    }

    @Override
    public boolean isSingleRoot() {
        return true;
    }

    @Override
    public List<Double> getAnswers() {
        List<Double> answers = new ArrayList<>();
        answers.add(x);
        return answers;
    }

    @Override
    public List<Double> getCorrectAnswers() {
        List<Double> answers = new ArrayList<>();
        answers.add(ans);
        return answers;
    }
}

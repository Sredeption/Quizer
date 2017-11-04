package hku.cs.smp.quizer.question;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class QuadraticQuestion extends Question {
    private int a, b, c;
    private Double x1, x2;
    private Double ans1, ans2;

    public QuadraticQuestion(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
        double delta = Math.sqrt(b * b - 4 * a * c);
        ans1 = round((-b - delta) / (2 * a));
        ans2 = round((-b + delta) / (2 * a));

    }

    public static QuadraticQuestion random() {
        Random random = new Random();
        int a, b, c;
        for (; ; ) {
            a = random.nextInt(198) - 99;
            b = random.nextInt(198) - 99;
            c = random.nextInt(198) - 99;
            if (b * b - 4 * a * c >= 0 && a != 0)
                break;
        }
        return new QuadraticQuestion(a, b, c);
    }

    @Override
    public String getQuestion() {
        StringBuilder questionBuilder = new StringBuilder();
        questionBuilder.append(a).append("x^2");
        if (b > 0) {
            questionBuilder.append("+");
        }
        if (b != 0) {
            questionBuilder.append(b).append("x");
        }
        if (c > 0) {
            questionBuilder.append("+");
        }
        if (c != 0) {
            questionBuilder.append(c);
        }
        questionBuilder.append("=0");
        return questionBuilder.toString();
    }

    @Override
    public void answerX1(Double x1) {
        this.x1 = x1;
    }

    @Override
    public void answerX2(Double x2) {
        this.x2 = x2;
    }

    @Override
    public void answer(Double x1, Double x2) {

    }

    @Override
    public boolean isSingleRoot() {
        return b * b - 4 * a * c == 0;
    }

    @Override
    public List<Double> getAnswers() {
        List<Double> answers = new ArrayList<>();
        answers.add(x1);
        answers.add(x2);
        return answers;
    }

    @Override
    public List<Double> getCorrectAnswers() {
        List<Double> answers = new ArrayList<>();
        answers.add(ans1);
        answers.add(ans2);
        return answers;
    }

}

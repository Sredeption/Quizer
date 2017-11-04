package hku.cs.smp.quizer.question;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

abstract public class Question implements Serializable {
    public final static String QUESTION = "QUESTION";
    public final static String DURATION = "DURATION";
    private boolean submitted = false;
    private int position;
    private boolean correct = false;
    private boolean giveUp = false;
    private Long duration = 0L;

    public abstract String getQuestion();

    public abstract void answerX1(Double x1);

    public abstract void answerX2(Double x2);

    public abstract void answer(Double x1, Double x2);

    public abstract boolean isSingleRoot();

    public abstract List<Double> getAnswers();

    public abstract List<Double> getCorrectAnswers();

    public void submit() {
        submitted = true;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void correct() {
        this.correct = true;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void giveUp() {
        giveUp = true;
    }

    public boolean isGiveUp() {
        return giveUp;
    }

    public void addDuration(Long d) {
        this.duration += d;
    }

    public Long getDuration() {
        return duration;
    }

    protected Double round(Double d) {
        BigDecimal bigDecimal = new BigDecimal(d);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}

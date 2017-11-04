package hku.cs.smp.quizer.summary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import hku.cs.smp.quizer.R;
import hku.cs.smp.quizer.question.Question;

import java.util.List;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> {

    private List<Question> questions;
    private Context context;

    public SummaryAdapter(Context context, List<Question> questions) {
        this.context = context;
        this.questions = questions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.summary_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.question = questions.get(position);
        holder.formula.setText(questions.get(position).getQuestion());
        StringBuilder answer = new StringBuilder();
        Double a;
        if (questions.get(position).isSingleRoot()) {
            a = questions.get(position).getAnswers().get(0);
            answer.append("x=");
            if (a != null) answer.append(a);
        } else {
            a = questions.get(position).getAnswers().get(0);
            answer.append("x1=");
            if (a != null)
                answer.append(a);
            answer.append("\n");
            a = questions.get(position).getAnswers().get(1);
            answer.append("x2=");
            if (a != null)
                answer.append(a);
        }

        holder.answer.setText(answer.toString());
        if (questions.get(position).isCorrect()) {
            holder.correct.setText(context.getString(R.string.correct));
            holder.correct.setTextColor(context.getResources().getColor(R.color.answerColor));
        } else if (questions.get(position).isGiveUp()) {
            holder.correct.setText(context.getString(R.string.give_up));
            holder.correct.setTextColor(context.getResources().getColor(R.color.giveUpColor));
        } else {
            holder.correct.setText(context.getString(R.string.wrong));
            holder.correct.setTextColor(context.getResources().getColor(R.color.wrongColor));
        }
    }


    @Override
    public int getItemCount() {
        return this.questions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Question question;
        private View view;
        private TextView formula, answer, correct;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.formula = view.findViewById(R.id.formula);
            this.answer = view.findViewById(R.id.answer);
            this.correct = view.findViewById(R.id.correct);
        }
    }
}

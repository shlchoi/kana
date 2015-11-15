package ca.uwaterloo.sh6choi.kana.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ca.uwaterloo.sh6choi.kana.model.FlashcardItem;

/**
 * Created by Samson on 2015-11-02.
 */
public class FlashcardAdapter<T extends FlashcardItem> extends RecyclerView.Adapter<FlashcardViewHolder> {
    private List<T> mFlashcardList;

    private int mCurIndex = -1;
    private int mFlashcardLayoutResId;

    public FlashcardAdapter(int flashcardLayoutResId) {
        mFlashcardList = new ArrayList<>();
        mFlashcardLayoutResId = flashcardLayoutResId;
    }

    @Override
    public FlashcardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mFlashcardLayoutResId, parent, false);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        params.height = parent.getHeight() - params.topMargin - params.bottomMargin;

        view.setLayoutParams(params);
        return new FlashcardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlashcardViewHolder holder, int position) {
        if (mFlashcardList.size() > 0) {
            holder.getItemTextView().setText(mFlashcardList.get(mCurIndex).getFlashcardText());
            holder.getHintTextView().setText(mFlashcardList.get(mCurIndex).getFlashcardHint());
            holder.getHintTextView().setVisibility(View.GONE);
            holder.itemView.setTag(mFlashcardList.get(mCurIndex));
        }
    }

    public void nextCard() {
        Random random = new Random(new Date().getTime());
        int nextInt;
        do {
            nextInt = random.nextInt(mFlashcardList.size());
        } while (nextInt == mCurIndex);

        mCurIndex = nextInt;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public void setFlashcardList(List<T> flashcardList) {
        mFlashcardList = flashcardList;
        nextCard();
        notifyDataSetChanged();
    }
}

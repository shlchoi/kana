package ca.uwaterloo.sh6choi.kana.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import ca.uwaterloo.sh6choi.kana.R;

/**
 * Created by Samson on 2015-11-02.
 */
public class FlashcardViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener  {
    private TextView mItemTextView;
    private TextView mHintTextView;

    public FlashcardViewHolder(View v) {
        super(v);
        mItemTextView = (TextView) v.findViewById(R.id.item_text_view);
        mItemTextView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mItemTextView.setOnTouchListener(this);

        mHintTextView = (TextView) v.findViewById(R.id.hint_text_view);
        mHintTextView.setVisibility(View.GONE);

        v.findViewById(R.id.flashcard_layout).setOnTouchListener(this);
    }

    public TextView getItemTextView() {
        return mItemTextView;
    }

    public TextView getHintTextView() {
        return mHintTextView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch (v.getId()) {
                    case R.id.item_text_view:
                        mHintTextView.setVisibility(View.VISIBLE);
                        return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                switch (v.getId()) {
                    case R.id.item_text_view:
                    case R.id.flashcard_layout:
                        mHintTextView.setVisibility(View.GONE);
                        return true;
                }
                break;
        }
        return false;
    }
}

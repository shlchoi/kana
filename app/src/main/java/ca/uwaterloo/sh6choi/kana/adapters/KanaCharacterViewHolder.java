package ca.uwaterloo.sh6choi.kana.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ca.uwaterloo.sh6choi.kana.R;

/**
 * Created by Samson on 2015-10-02.
 */
public class KanaCharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mTextView;

    private OnItemClickListener mOnItemClickListener;

    public KanaCharacterViewHolder(View v) {
        super(v);
        mTextView = (TextView) v.findViewById(R.id.kana_character_text_view);
        v.setOnClickListener(this);
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.kana_character_linear_layout) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }
}

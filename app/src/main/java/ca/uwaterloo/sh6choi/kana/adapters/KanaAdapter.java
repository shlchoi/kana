package ca.uwaterloo.sh6choi.kana.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ca.uwaterloo.sh6choi.kana.R;
import ca.uwaterloo.sh6choi.kana.model.KanaCharacter;


/**
 * Created by Samson on 2015-09-25.
 */
public class KanaAdapter extends RecyclerView.Adapter<KanaCharacterViewHolder> implements KanaCharacterViewHolder.OnItemClickListener {
    private List<KanaCharacter> mKanaCharacterList;

    private OnItemClickListener mOnItemClickListener;

    public KanaAdapter() {
        mKanaCharacterList = new ArrayList<>();
    }

    @Override
    public KanaCharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_kana, parent, false);
        KanaCharacterViewHolder viewHolder = new KanaCharacterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(KanaCharacterViewHolder holder, int position) {
        if (!TextUtils.isEmpty(mKanaCharacterList.get(position).getCharacter())) {
            holder.getTextView().setText(mKanaCharacterList.get(position).getCharacter());

            holder.itemView.setTag(mKanaCharacterList.get(position));
            holder.setOnItemClickListener(this);
        } else {
            holder.getTextView().setText("");
            holder.itemView.setTag(null);
            holder.setOnItemClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return mKanaCharacterList.size();
    }

    public KanaCharacter getItem(int position) {
        return mKanaCharacterList.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onItemClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view);
        }
    }

    public void setKanaCharacterList(List<KanaCharacter> kanaCharacterList) {
        mKanaCharacterList = kanaCharacterList;
        mKanaCharacterList.add(36, new KanaCharacter("yi", "", ""));
        mKanaCharacterList.add(38, new KanaCharacter("ye", "", ""));
        mKanaCharacterList.add(46, new KanaCharacter("wi", "", ""));
        mKanaCharacterList.add(47, new KanaCharacter("wi", "", ""));
        mKanaCharacterList.add(48, new KanaCharacter("wi", "", ""));
        mKanaCharacterList.add(50, new KanaCharacter("-a", "", ""));
        mKanaCharacterList.add(51, new KanaCharacter("-i", "", ""));
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }
}

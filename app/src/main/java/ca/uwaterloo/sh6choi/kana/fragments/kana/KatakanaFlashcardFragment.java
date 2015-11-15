package ca.uwaterloo.sh6choi.kana.fragments.kana;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ca.uwaterloo.sh6choi.kana.R;
import ca.uwaterloo.sh6choi.kana.activities.MainActivity;
import ca.uwaterloo.sh6choi.kana.adapters.FlashcardAdapter;
import ca.uwaterloo.sh6choi.kana.fragments.DrawerFragment;
import ca.uwaterloo.sh6choi.kana.model.KanaCharacter;
import ca.uwaterloo.sh6choi.kana.presentation.HiraganaCharacterPresenter;
import ca.uwaterloo.sh6choi.kana.presentation.KanaCharacterPresenter;
import ca.uwaterloo.sh6choi.kana.presentation.KatakanaCharacterPresenter;

/**
 * Created by Samson on 2015-09-22.
 */
public class KatakanaFlashcardFragment extends Fragment implements DrawerFragment, KanaCharacterPresenter.CharacterView {

    private static final String TAG = KatakanaFlashcardFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.kana.flashcards";

    private List<KanaCharacter> mKanaCharacterList;
    private KatakanaCharacterPresenter mPresenter;

    private int mCurIndex = -1;

    private RecyclerView mFlashcardRecyclerView;
    private FlashcardAdapter<KanaCharacter> mFlashcardAdapter;

    public static KatakanaFlashcardFragment getInstance(Bundle args) {
        KatakanaFlashcardFragment fragment = new KatakanaFlashcardFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(false);
        View contentView = inflater.inflate(R.layout.fragment_kana_flashcard, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mKanaCharacterList = new ArrayList<>();

        mFlashcardRecyclerView = (RecyclerView) view.findViewById(R.id.flashcard_recycler_view);
        mFlashcardAdapter = new FlashcardAdapter<>(R.layout.list_item_flashcard_character);

        ItemTouchHelper.Callback listItemTouchHelper = new ItemTouchHelper.Callback() {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.LEFT);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    mFlashcardAdapter.nextCard();
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(listItemTouchHelper);
        itemTouchHelper.attachToRecyclerView(mFlashcardRecyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        mFlashcardRecyclerView.setLayoutManager(manager);
        mFlashcardRecyclerView.setAdapter(mFlashcardAdapter);

        mPresenter  = new KatakanaCharacterPresenter(getContext(), this);
        mPresenter.obtainAllCharacters();
    }

    @Override
    public void refreshCharacterList(List<KanaCharacter> kanaCharacterList) {
        mFlashcardAdapter.setFlashcardList(kanaCharacterList);
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public int getTitleStringResId() {
        return R.string.toolbar_title_flashcards;
    }

    @Override
    public boolean shouldAddToBackstack() {
        return false;
    }

    @Override
    public boolean shouldShowUp() {
        return true;
    }

    @Override
    public boolean onBackPressed() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_KANA);

        startActivity(intent);
        return true;
    }
}

package ca.uwaterloo.sh6choi.kana.fragments.kana;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ca.uwaterloo.sh6choi.kana.R;
import ca.uwaterloo.sh6choi.kana.activities.MainActivity;
import ca.uwaterloo.sh6choi.kana.adapters.KanaAdapter;
import ca.uwaterloo.sh6choi.kana.fragments.DrawerFragment;
import ca.uwaterloo.sh6choi.kana.model.KanaCharacter;
import ca.uwaterloo.sh6choi.kana.presentation.HiraganaCharacterPresenter;
import ca.uwaterloo.sh6choi.kana.presentation.KanaCharacterPresenter;
import ca.uwaterloo.sh6choi.kana.presentation.KatakanaCharacterPresenter;
import ca.uwaterloo.sh6choi.kana.services.HiraganaWebIntentService;
import ca.uwaterloo.sh6choi.kana.services.KatakanaWebIntentService;

/**
 * Created by Samson on 2015-10-26.
 */
public class KatakanaListFragment extends Fragment implements KanaCharacterPresenter.CharacterView,
        KanaAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mKanaSwipeRefreshLayout;
    private RecyclerView mKanaRecyclerView;
    private KanaAdapter mKanaAdapter;

    private KatakanaCharacterPresenter mPresenter;
    private BroadcastReceiver mSuccessReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mPresenter != null) {
                mKanaSwipeRefreshLayout.setRefreshing(false);
                mPresenter.obtainRegularCharacters();
            }
        }
    };

    public static KatakanaListFragment getInstance(Bundle args) {
        KatakanaListFragment fragment = new KatakanaListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View contentView = inflater.inflate(R.layout.fragment_kana_list, container, false);
        setHasOptionsMenu(true);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mKanaSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.kana_swipe_refresh);
        mKanaSwipeRefreshLayout.setOnRefreshListener(this);
        mKanaSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        mKanaRecyclerView = (RecyclerView) view.findViewById(R.id.kana_recycler_view);
        mKanaRecyclerView.setHasFixedSize(true);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 30);
        GridLayoutManager.SpanSizeLookup lookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mKanaAdapter.getItem(position).getCharId().length() == 3) {
                    return 10;
                } else {
                    return 6;
                }
            }
        };

        manager.setSpanSizeLookup(lookup);
        mKanaRecyclerView.setLayoutManager(manager);

        mKanaAdapter = new KanaAdapter();
        mKanaAdapter.setOnItemClickListener(this);
        mKanaRecyclerView.setAdapter(mKanaAdapter);

        mPresenter = new KatakanaCharacterPresenter(getContext(), this);
        mPresenter.obtainRegularCharacters();
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter successFilter = new IntentFilter();
        successFilter.addAction(KatakanaWebIntentService.ACTION_SUCCESS);
        getContext().registerReceiver(mSuccessReceiver, successFilter);
    }

    @Override
    public void onPause() {
        getContext().unregisterReceiver(mSuccessReceiver);
        super.onPause();
    }

    @Override
    public void refreshCharacterList(List<KanaCharacter> kanaCharacterList) {
        mKanaAdapter.setKanaCharacterList(kanaCharacterList);
    }

    @Override
    public void onItemClick(View view) {
        KanaCharacter kanaCharacter = (KanaCharacter) view.getTag();

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setAction(MainActivity.ACTION_KANA_CHARACTER);
        intent.putExtra(MainActivity.EXTRA_CHARACTER, kanaCharacter);

        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_refresh_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                mKanaSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        Intent intent = new Intent(getContext(), KatakanaWebIntentService.class);
        getContext().startService(intent);
    }
}

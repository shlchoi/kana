package ca.uwaterloo.sh6choi.kana.fragments.kana;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ca.uwaterloo.sh6choi.kana.R;
import ca.uwaterloo.sh6choi.kana.activities.MainActivity;
import ca.uwaterloo.sh6choi.kana.adapters.KanaViewPagerAdapter;
import ca.uwaterloo.sh6choi.kana.fragments.DrawerFragment;

/**
 * Created by Samson on 2015-09-25.
 */
public class KanaLookupFragment extends Fragment implements DrawerFragment {
    private static final String TAG = KanaLookupFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = MainActivity.TAG + ".fragment.kana_lookup";

    private ViewPager mKanaViewPager;
    private PagerTabStrip mKanaPagerTitleStrip;
    private KanaViewPagerAdapter mKanaViewPagerAdapter;

    public static KanaLookupFragment getInstance(Bundle args) {
        KanaLookupFragment fragment = new KanaLookupFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        View contentView = inflater.inflate(R.layout.fragment_kana_lookup, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mKanaViewPager = (ViewPager) view.findViewById(R.id.kana_view_pager);
        mKanaPagerTitleStrip = (PagerTabStrip) view.findViewById(R.id.kana_pager_tab_strip);
        mKanaPagerTitleStrip.setTabIndicatorColorResource(R.color.colorAccent);
        mKanaPagerTitleStrip.setDrawFullUnderline(true);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HiraganaListFragment.getInstance(new Bundle()));
        fragments.add(KatakanaListFragment.getInstance(new Bundle()));

        mKanaViewPagerAdapter = new KanaViewPagerAdapter(getChildFragmentManager(), fragments);
        mKanaViewPager.setAdapter(mKanaViewPagerAdapter);
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public int getTitleStringResId() {
        return R.string.toolbar_title_character_lookup;
    }

    @Override
    public boolean shouldShowUp() {
        return true;
    }

    @Override
    public boolean shouldAddToBackstack() {
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

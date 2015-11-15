package ca.uwaterloo.sh6choi.kana.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Samson on 2015-10-26.
 */
public class KanaViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public KanaViewPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList) {
        super(fragmentManager);

        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Hiragana";
        } else if (position == 1) {
            return "Katakana";
        } else {
            return "";
        }
    }
}

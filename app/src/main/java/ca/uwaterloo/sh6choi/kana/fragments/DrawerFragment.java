package ca.uwaterloo.sh6choi.kana.fragments;

/**
 * Created by Samson on 2015-09-22.
 */
public interface DrawerFragment {

    String getFragmentTag();

    int getTitleStringResId();

    boolean shouldShowUp();

    boolean shouldAddToBackstack();

    boolean onBackPressed();

}

package ca.uwaterloo.sh6choi.kana.views;


import ca.uwaterloo.sh6choi.kana.R;

/**
 * Created by Samson on 2015-09-22.
 */
public enum KanaMenuItem implements IDrawerMenuItem {
    KANA(R.string.nav_menu_kana),
    QUIZ(R.string.nav_menu_quiz),
    DICTATION(R.string.nav_menu_dictation);

    private int mStringResId;

    KanaMenuItem(int stringResId) {
        mStringResId = stringResId;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.list_item_nav_menu;
    }

    @Override
    public int getStringResId() {
        return mStringResId;
    }

    @Override
    public int getDrawableResId() {
        return 0;
    }
}

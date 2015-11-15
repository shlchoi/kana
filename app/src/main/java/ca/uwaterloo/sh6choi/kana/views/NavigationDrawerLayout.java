package ca.uwaterloo.sh6choi.kana.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import java.util.ArrayList;

import ca.uwaterloo.sh6choi.kana.R;

/**
 * Created by Samson on 2015-09-22.
 */
public abstract class NavigationDrawerLayout extends SlidingDrawer {

    protected ListView mDrawerMenu;
    protected DrawerMenuAdapter mMenuAdapter;

    public NavigationDrawerLayout(Context context) {
        super(context);
    }

    public NavigationDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NavigationDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DrawerMenuAdapter getMenuAdapter() {
        return mMenuAdapter;
    }

    public void setMenuAdapter(DrawerMenuAdapter adapter) {
        mMenuAdapter = adapter;
        initializeMenu();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(isInEditMode()) {
            return;
        }
        initializeMenu();
    }

    protected void initializeMenu() {

        mDrawerMenu = (ListView) findViewById(R.id.navigation_drawer);

        if (mMenuAdapter == null) {
            mMenuAdapter = new DrawerMenuAdapter(getContext(), new ArrayList<IDrawerMenuItem>());
        }
        mDrawerMenu.setAdapter(mMenuAdapter);

        setupClickListener();
    }

    protected abstract void setupClickListener();
    protected abstract void startNavigation(IDrawerMenuItem navigateToItem);
}


package ca.uwaterloo.sh6choi.kana.views;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ca.uwaterloo.sh6choi.kana.R;

/**
 * Created by Samson on 2015-09-22.
 */
public class SlidingDrawer extends SlidingPaneLayout implements ISlidingPane, SlidingPaneLayout.PanelSlideListener {

    List<DrawerLayout.DrawerListener> mListeners;
    boolean mDrawerEnabled = true;

    public SlidingDrawer(Context context) {
        super(context);
        init();
    }

    public SlidingDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlidingDrawer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setPanelSlideListener(this);
        setCoveredFadeColor(getResources().getColor(android.R.color.transparent));
        setSliderFadeColor(getResources().getColor(android.R.color.transparent));
        setShadowResourceLeft(R.drawable.navigation_drawer_shadow);
        mListeners = new ArrayList<>();
    }

    @Override
    public boolean isVisible() {
        return isOpen();
    }

    @Override
    public boolean isOpen() {
        if (!isSlideable())
            return false;

        return super.isOpen();
    }

    @Override
    public void addDrawerListener(DrawerLayout.DrawerListener listener) {
        if (!mListeners.contains(listener)) {
            mListeners.add(listener);
        }
    }

    @Override
    public void removeDrawerListener(DrawerLayout.DrawerListener listener) {
        if (mListeners.contains(listener)) {
            mListeners.remove(listener);
        }
    }

    @Override
    public void clearDrawerListeners() {
        mListeners.clear();
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        for (DrawerLayout.DrawerListener listener : mListeners) {
            listener.onDrawerSlide(panel, slideOffset);
        }
    }

    @Override
    public void onPanelOpened(View panel) {
        for (DrawerLayout.DrawerListener listener : mListeners) {
            listener.onDrawerOpened(panel);
        }
    }

    @Override
    public void onPanelClosed(View panel) {
        for (DrawerLayout.DrawerListener listener : mListeners) {
            listener.onDrawerClosed(panel);
        }
    }

    public boolean isDrawerEnabled() {
        return mDrawerEnabled;
    }

    public void setDrawerEnabled(boolean drawerEnabled) {
        mDrawerEnabled = drawerEnabled;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!mDrawerEnabled) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!mDrawerEnabled) {
            return false;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        if (!mDrawerEnabled) {
            return false;
        }
        return super.onInterceptHoverEvent(event);
    }
}

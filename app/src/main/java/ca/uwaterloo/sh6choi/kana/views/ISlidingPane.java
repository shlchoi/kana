package ca.uwaterloo.sh6choi.kana.views;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;

/**
 * Created by Samson on 2015-09-22.
 */
public interface ISlidingPane {

    boolean openPane();

    boolean closePane();

    boolean isOpen();

    boolean isVisible();

    void addDrawerListener(DrawerLayout.DrawerListener listener);

    void removeDrawerListener(DrawerLayout.DrawerListener listener);

    void clearDrawerListeners();

    class DrawerLayoutWrapper extends DrawerLayout {

        final private ISlidingPane mSlidingPanel;

        public DrawerLayoutWrapper(Activity context, ISlidingPane proxy) {
            super(context);
            mSlidingPanel = proxy;
        }

        @Override
        public void openDrawer(int gravity) {
            mSlidingPanel.openPane();
        }

        @Override
        public void closeDrawer(int gravity) {

            mSlidingPanel.closePane();
        }

        @Override
        public boolean isDrawerOpen(int drawerGravity) {
            return mSlidingPanel.isOpen();
        }

        @Override
        public boolean isDrawerVisible(int drawerGravity) {
            return mSlidingPanel.isVisible();
        }
    }
}

package ca.uwaterloo.sh6choi.kana.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.uwaterloo.sh6choi.kana.R;

/**
 * Created by Samson on 2015-09-22.
 */
public class DrawerMenuAdapter extends BaseAdapter {

    private List<IDrawerMenuItem> mMenuItems;
    private Context mContext;
    public DrawerMenuAdapter(Context context, List<IDrawerMenuItem> menuLabels) {
        super();

        mContext = context;
        mMenuItems = new ArrayList<>();

        for (int i = 0; i < menuLabels.size(); i++) {
            add(menuLabels.get(i));
        }
    }

    @Override
    public int getCount() {
        return mMenuItems.size();
    }

    @Override
    public IDrawerMenuItem getItem(int position) {
        return mMenuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        IDrawerMenuItem drawerMenuItem = mMenuItems.get(position);

        int type = getItemViewType(position);

        if (convertView != null && convertView.findViewById(R.id.navigation_menu_label) != null) {
            view = convertView;
        } else {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(drawerMenuItem.getLayoutResId(), parent, false);
        }

        TextView labelView = (TextView) view.findViewById(R.id.navigation_menu_label);
        labelView.setText(mContext.getString(drawerMenuItem.getStringResId()));

        return view;
    }

    public void add(IDrawerMenuItem item) {
        mMenuItems.add(item);
    }

    public void redrawMenu(List<IDrawerMenuItem> menuItems) {
        mMenuItems = menuItems;
        notifyDataSetInvalidated();
    }
}

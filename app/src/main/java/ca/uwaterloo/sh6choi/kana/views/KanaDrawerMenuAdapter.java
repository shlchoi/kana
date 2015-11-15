package ca.uwaterloo.sh6choi.kana.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ca.uwaterloo.sh6choi.kana.R;

/**
 * Created by Samson on 2015-10-02.
 */
public class KanaDrawerMenuAdapter extends DrawerMenuAdapter {

    public KanaDrawerMenuAdapter(Context context, List<IDrawerMenuItem> menuLabels) {
        super(context, menuLabels);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        view.setBackgroundResource(R.drawable.selector_nav_menu_dark);

        return view;
    }
}

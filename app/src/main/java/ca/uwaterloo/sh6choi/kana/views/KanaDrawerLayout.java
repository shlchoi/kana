package ca.uwaterloo.sh6choi.kana.views;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ca.uwaterloo.sh6choi.kana.R;
import ca.uwaterloo.sh6choi.kana.activities.MainActivity;

/**
 * Created by Samson on 2015-09-22.
 */
public class KanaDrawerLayout extends NavigationDrawerLayout {

    public KanaDrawerLayout(Context context) {
        super(context);
    }

    public KanaDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KanaDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void initializeMenu() {

        mDrawerMenu = (ListView) findViewById(R.id.navigation_drawer);

        if (mMenuAdapter == null) {
            mMenuAdapter = new KanaDrawerMenuAdapter(getContext(), new ArrayList<IDrawerMenuItem>());
        }
        mDrawerMenu.setAdapter(mMenuAdapter);

        setupClickListener();
    }

    @Override
    protected void setupClickListener() {
        mDrawerMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IDrawerMenuItem itemByPosition = mMenuAdapter.getItem(position);
                startNavigation(itemByPosition);
            }
        });
    }

    @Override
    protected void startNavigation(IDrawerMenuItem navigateToItem) {
        if (navigateToItem == null || !(navigateToItem instanceof KanaMenuItem)) {
            return;
        }

        Intent intent;
        switch ((KanaMenuItem) navigateToItem) {
            case KANA:
            default:
                intent = new Intent(getContext(), MainActivity.class);
                intent.setAction(MainActivity.ACTION_KANA);
                break;
            case QUIZ:
                intent = new Intent(getContext(), MainActivity.class);
                intent.setAction(MainActivity.ACTION_QUIZ);
                break;
            case DICTATION:
                intent = new Intent(getContext(), MainActivity.class);
                intent.setAction(MainActivity.ACTION_DICTATION);
                break;
        }
        getContext().startActivity(intent);
    }
}

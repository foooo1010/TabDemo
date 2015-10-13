package com.example.flandreyi.tabdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by FlandreYi on 2015/10/13.
 */
public class SimpleFragment extends Fragment {
    private int I;
    public SimpleFragment() {
    }

    public SimpleFragment(int i) {
        I = i;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.gravity=Gravity.CENTER;
        textView.setLayoutParams(lp);
        textView.setText(""+I);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}

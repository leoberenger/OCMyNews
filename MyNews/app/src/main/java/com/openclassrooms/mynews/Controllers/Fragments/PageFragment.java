package com.openclassrooms.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.openclassrooms.mynews.R;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {

    public static final String KEY_POSITION = "position";
    public static final String KEY_COLOR = "color";

    @BindView(R.id.fragment_page_rootView) LinearLayout mRootView;
    @BindView(R.id.fragment_page_title) TextView mTextView;

    public PageFragment() {
        // Required empty public constructor
    }

    public static PageFragment newInstance(int position, int color){
        PageFragment mFragment = new PageFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        args.putInt(KEY_COLOR, color);
        mFragment.setArguments(args);
        return(mFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_page, container, false);
        int position = getArguments().getInt(KEY_POSITION, -1);
        int color = getArguments().getInt(KEY_COLOR, -1);

        mRootView.setBackgroundColor(color);
        mTextView.setText("Page numéro" + position);
        Log.e(getClass().getSimpleName(), "onCreateView called fragment n°" + position);

        return result;
    }

}

package com.openclassrooms.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.openclassrooms.mynews.Controllers.Fragments.ArticleViews.DisplaySearchFragment;
import com.openclassrooms.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private DisplaySearchFragment mDisplaySearchFragment;

    @BindView(R.id.activity_search_query_input)
    EditText queryInput;
    @BindView(R.id.activity_search_button)
    Button button;

    private String mQuery = "france";
    private String mNewsDesk = "news_desk:(%22Travel%22)";
    private int mBeginDate = 20170910;
    private int mEndDate = 20171001;

    public SearchFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //mQuery = queryInput.getText().toString();
                //deskCheckboxes(view);
                configureAndShowDisplaySearchFragment();
            }
        });

        return view;
    }


    private void configureAndShowDisplaySearchFragment() {

        if (mDisplaySearchFragment == null) {
            mDisplaySearchFragment = new DisplaySearchFragment();

            Bundle args = new Bundle();
            args.putString("query", mQuery);
            args.putString("newsDesk", mNewsDesk);
            args.putInt("beginDate", mBeginDate);
            args.putInt("endDate", mEndDate);
            mDisplaySearchFragment.setArguments(args);

            getFragmentManager().beginTransaction()
                    .remove(this)
                    .add(R.id.activity_search_frame_layout, mDisplaySearchFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void deskCheckboxes(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        mNewsDesk = "news_desk:(";

        boolean min1checked = false;

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkbox_arts:
                if (checked) {
                    mNewsDesk += "%22Arts%22 ";
                    min1checked = true;
                }
                break;
            case R.id.checkbox_business:
                if (checked){
                    mNewsDesk += "%22Business%22 ";
                    min1checked = true;
                }
                break;
            case R.id.checkbox_entrepreneur:
                if (checked){
                    mNewsDesk += "%22Entrepreneurs%22 ";
                    min1checked = true;
                }
                break;
            case R.id.checkbox_politics:
                if (checked){
                    mNewsDesk += "%22Politics%22 ";
                    min1checked = true;
                }
                break;
            case R.id.checkbox_sports:
                if (checked){
                    mNewsDesk += "%22Sports%22 ";
                    min1checked = true;
                }
                break;
            case R.id.checkbox_travel:
                if (checked){
                    mNewsDesk += "%22Travel%22 ";
                    min1checked = true;
                }
                break;
        }
        mNewsDesk += ")";

        if(!min1checked){
            //SHOW MESSAGE TO SELECT AT LEAST 1
        }
    }



}

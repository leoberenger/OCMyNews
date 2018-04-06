package com.openclassrooms.mynews.Controllers.base;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

import com.openclassrooms.mynews.R;

public abstract class BaseSearchActivity extends AppCompatActivity{

    protected boolean [] desksAreChecked = {false, false, false, false, false, false};
    protected String [] newsDesk = {"%22Arts%22", "%22Business%22", "%22Entrepreneur%22", "%22Politics%22", "%22Sports%22", "%22Travel%22"};
    protected int newsDesksLength = desksAreChecked.length;

    protected String mQuery = "";
    protected String mNewsDesk;
    protected int mBeginDate = 0;
    protected int mEndDate = 0;

    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkbox_arts: desksAreChecked[0] = checked; break;
            case R.id.checkbox_business: desksAreChecked[1] = checked; break;
            case R.id.checkbox_entrepreneur: desksAreChecked[2] = checked; break;
            case R.id.checkbox_politics: desksAreChecked[3] = checked; break;
            case R.id.checkbox_sports: desksAreChecked[4] = checked; break;
            case R.id.checkbox_travel: desksAreChecked[5] = checked; break;
        }
    }
}

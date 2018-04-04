package com.openclassrooms.mynews.Controllers.Activities;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

import com.openclassrooms.mynews.R;

public abstract class BaseSearchActivity extends AppCompatActivity{

    protected boolean [] deskIsSet = {false, false, false, false, false, false};
    protected int newsDesksLength = deskIsSet.length;
    protected String mQuery = "";
    protected String mNewsDesk;
    protected String [] newsDesk = {"%22Arts%22", "%22Business%22", "%22Entrepreneur%22", "%22Politics%22", "%22Sports%22", "%22Travel%22"};


    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkbox_arts: deskIsSet[0] = checked; break;
            case R.id.checkbox_business: deskIsSet[1] = checked; break;
            case R.id.checkbox_entrepreneur: deskIsSet[2] = checked; break;
            case R.id.checkbox_politics: deskIsSet[3] = checked; break;
            case R.id.checkbox_sports: deskIsSet[4] = checked; break;
            case R.id.checkbox_travel: deskIsSet[5] = checked; break;
        }
    }
}

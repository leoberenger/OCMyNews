package com.openclassrooms.mynews.controllers.activities;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.openclassrooms.mynews.R;

public abstract class BaseSearchActivity extends AppCompatActivity{

    final boolean [] newsDesksSelected = {false, false, false, false, false, false};

    String mQuery = "";
    String mNewsDesk;
    int mBeginDate = 0;
    int mEndDate = 0;

    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkbox_arts: newsDesksSelected[0] = checked; break;
            case R.id.checkbox_business: newsDesksSelected[1] = checked; break;
            case R.id.checkbox_entrepreneur: newsDesksSelected[2] = checked; break;
            case R.id.checkbox_politics: newsDesksSelected[3] = checked; break;
            case R.id.checkbox_sports: newsDesksSelected[4] = checked; break;
            case R.id.checkbox_travel: newsDesksSelected[5] = checked; break;
        }
    }
}

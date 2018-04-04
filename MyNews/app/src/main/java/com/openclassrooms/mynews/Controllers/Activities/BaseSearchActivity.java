package com.openclassrooms.mynews.Controllers.Activities;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

import com.openclassrooms.mynews.R;

public abstract class BaseSearchActivity extends AppCompatActivity{

    protected boolean [] deskIsSet = {false, false, false, false, false, false};
    protected int newsDesksLength = deskIsSet.length;
    protected boolean min1DeskIsSelected = false;
    protected String mQuery = "";

    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();
        min1DeskIsSelected = false;

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkbox_arts:
                if (checked) {
                    deskIsSet[0] = true;
                    min1DeskIsSelected = true;
                }else{
                    deskIsSet[0] = false;
                }
                break;
            case R.id.checkbox_business:
                if (checked) {
                    deskIsSet[1] = true;
                    min1DeskIsSelected = true;
                }else{
                    deskIsSet[1] = false;
                }
                break;
            case R.id.checkbox_entrepreneur:
                if (checked){
                    deskIsSet[2] = true;
                    min1DeskIsSelected = true;
                }else{
                    deskIsSet[2] = false;
                }
                break;
            case R.id.checkbox_politics:
                if (checked){
                    deskIsSet[3] = true;
                    min1DeskIsSelected = true;
                }else{
                    deskIsSet[3] = false;
                }
                break;
            case R.id.checkbox_sports:
                if (checked){
                    deskIsSet[4] = true;
                    min1DeskIsSelected = true;
                }else{
                    deskIsSet[4] = false;
                }
                break;
            case R.id.checkbox_travel:
                if (checked){
                    deskIsSet[5] = true;
                    min1DeskIsSelected = true;
                }else{
                    deskIsSet[5] = false;
                }
                break;
        }
    }
}

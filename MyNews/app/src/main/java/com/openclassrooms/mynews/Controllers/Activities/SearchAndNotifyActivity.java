package com.openclassrooms.mynews.Controllers.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.openclassrooms.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAndNotifyActivity extends BaseSearchActivity {

    @BindView(R.id.activity_notification_switch) Switch notificationSwitch;
    @BindView(R.id.activity_notification_query) EditText queryInput;
    @BindView(R.id.checkbox_arts) CheckBox checkboxArts;
    @BindView(R.id.checkbox_business) CheckBox checkboxBusiness;
    @BindView(R.id.checkbox_entrepreneur) CheckBox checkboxEntrepreneur;
    @BindView(R.id.checkbox_politics) CheckBox checkboxPolitics;
    @BindView(R.id.checkbox_sports) CheckBox checkboxSports;
    @BindView(R.id.checkbox_travel) CheckBox checkboxTravel;

    CheckBox [] checkBoxes = new CheckBox [newsDesksLength];

    private SharedPreferences prefs;
    private boolean switchEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        prefs = getSharedPreferences("notification", MODE_PRIVATE);

        checkBoxes[0] = checkboxArts;
        checkBoxes[1] = checkboxBusiness;
        checkBoxes[2] = checkboxEntrepreneur;
        checkBoxes[3] = checkboxPolitics;
        checkBoxes[4] = checkboxSports;
        checkBoxes[5] = checkboxTravel;

        mQuery = prefs.getString("query", "");

        for(int i=0; i<newsDesksLength; i++){
            deskIsSet[i] = prefs.getBoolean("desk"+i, false);
        }

        switchEnabled = prefs.getBoolean("switch", false);


        Log.e("onCreate()", "Query = "+mQuery+", desk0 = "+ deskIsSet[0]+", desk1 = "+ deskIsSet[1]
                +", desk2 = "+ deskIsSet[2]+", desk3 = "+ deskIsSet[3]+", desk4 = "+ deskIsSet[4]+", desk5 = "+ deskIsSet[5]);

        this.getAndShowSavedNotification();
        this.configureSwitch();
    }

    private void getAndShowSavedNotification(){
        queryInput.setText(mQuery);

        for(int i = 0; i <newsDesksLength; i++){
            if(deskIsSet[i])
                checkBoxes[i].setChecked(true);
            else
                checkBoxes[i].setChecked(false);
        }

        if (switchEnabled) notificationSwitch.setChecked(true);

    }

    private void configureSwitch(){

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean switchIsChecked) {

                mQuery = queryInput.getText().toString();
                boolean min1DeskIsSelected = false;

                for(int i = 0; i<newsDesksLength; i++){
                    if(deskIsSet[i])
                        min1DeskIsSelected = true;
                }

                if(switchIsChecked){
                    if(mQuery.equals("")) {
                        Toast.makeText(getApplicationContext(), "Query required", Toast.LENGTH_LONG).show();
                        notificationSwitch.setChecked(false);
                    }else if(!min1DeskIsSelected) {
                        Toast.makeText(getApplicationContext(), "Pick at least one topic", Toast.LENGTH_LONG).show();
                        notificationSwitch.setChecked(false);
                    }else{
                        prefs.edit().putBoolean("switch", true).apply();
                        prefs.edit().putString("query", mQuery).apply();
                        for(int i = 0; i<newsDesksLength; i++){
                            prefs.edit().putBoolean("desk"+i, deskIsSet[i]).apply();
                        }

                        Toast.makeText(getApplicationContext(), "Notification saved", Toast.LENGTH_LONG).show();

                        Log.e("Set", " mQuery= " + mQuery
                                        +", desk0 = "+ deskIsSet[0]+", desk1 = "+ deskIsSet[1]
                                        +", desk2 = "+ deskIsSet[2]+", desk3 = "+ deskIsSet[3]
                                        +", desk4 = "+ deskIsSet[4]+", desk5 = "+ deskIsSet[5]
                        );

                        String mSavedQuery = prefs.getString("query", "");
                        boolean [] savedDesk = new boolean[deskIsSet.length];

                        for(int i=0; i<newsDesksLength; i++){
                            savedDesk[i] = prefs.getBoolean("desk"+i, false);
                        }


                        Log.e("Saved", "Notification Saved for query " + mSavedQuery
                                        +", desk0 = "+ savedDesk[0]+", desk1 = "+ savedDesk[1]
                                        +", desk2 = "+ savedDesk[2]+", desk3 = "+ savedDesk[3]
                                        +", desk4 = "+ savedDesk[4]+", desk5 = "+ savedDesk[5]
                        );

                        //JobManager.create(getApplicationContext()).addJobCreator(new JobCreator());
                        //SyncJob.scheduleJob();
                    }
                }else{
                    queryInput.setText("");
                    for(int i = 0; i <newsDesksLength; i++)
                        checkBoxes[i].setChecked(false);

                    prefs.edit().putBoolean("switch", false).apply();
                    prefs.edit().putString("query", "").apply();
                    for(int i = 0; i<newsDesksLength; i++){
                        prefs.edit().putBoolean("desk"+i, false).apply();
                    }
                    //stopAlarm();
                }
            }
        });
    }
}


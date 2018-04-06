package com.openclassrooms.mynews.Controllers.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.evernote.android.job.JobManager;
import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Utils.MyJobCreator;
import com.openclassrooms.mynews.Utils.SearchAndNotifyJob;
import com.openclassrooms.mynews.Controllers.base.BaseSearchActivity;

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

        checkBoxes[0] = checkboxArts;
        checkBoxes[1] = checkboxBusiness;
        checkBoxes[2] = checkboxEntrepreneur;
        checkBoxes[3] = checkboxPolitics;
        checkBoxes[4] = checkboxSports;
        checkBoxes[5] = checkboxTravel;

        //Retrieve saved search from Preferences
        prefs = getSharedPreferences("notification", MODE_PRIVATE);

        mQuery = prefs.getString("query", "");
        switchEnabled = prefs.getBoolean("switch", false);
        for(int i=0; i<newsDesksLength; i++)
            desksAreChecked[i] = prefs.getBoolean("desk"+i, false);

        this.getAndShowSavedNotification();
        this.configureSwitch();
    }

    private void getAndShowSavedNotification(){
        queryInput.setText(mQuery);

        for(int i = 0; i <newsDesksLength; i++){
            if(desksAreChecked[i])
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
                    if(desksAreChecked[i])
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
                        //1 - Save Search elements to Preferences
                        prefs.edit().putBoolean("switch", true).apply();
                        prefs.edit().putString("query", mQuery).apply();
                        for(int i = 0; i<newsDesksLength; i++){
                            prefs.edit().putBoolean("desk"+i, desksAreChecked[i]).apply();
                        }

                        StringBuilder str = new StringBuilder("news_desk:(");
                        for (int i = 0; i < newsDesksLength; i++) {
                            if (desksAreChecked[i])
                                str.append(newsDesk[i]);
                        }
                        str.append(")");
                        mNewsDesk = str.toString();

                        prefs.edit().putString("newsDesks", mNewsDesk).apply();

                        Log.e("NotifActivity", "mQuery="+mQuery+", mNewsDesk = " + mNewsDesk);

                        //2 - Create Job Manager
                        JobManager.create(getApplicationContext())
                                .addJobCreator(new MyJobCreator());
                        SearchAndNotifyJob.schedulePeriodic(mQuery, mNewsDesk);

                        //3 - Show Toast message
                        Toast.makeText(getApplicationContext(), "Notification saved", Toast.LENGTH_LONG).show();
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

                    JobManager.instance().cancel(1234);
                }
            }
        });
    }
}


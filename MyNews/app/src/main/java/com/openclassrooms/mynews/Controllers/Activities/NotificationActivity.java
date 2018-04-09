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
import com.openclassrooms.mynews.Models.Search;
import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Utils.MyJobCreator;
import com.openclassrooms.mynews.Utils.NotificationJob;
import com.openclassrooms.mynews.Controllers.base.BaseSearchActivity;
import com.openclassrooms.mynews.Utils.SearchMgr;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends BaseSearchActivity {

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
    private Search search;
    private SearchMgr searchMgr;

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
        searchMgr = SearchMgr.getInstance();

        this.getAndShowSavedNotification();
        this.configureSwitch();
    }

    private void getAndShowSavedNotification(){

        search = searchMgr.getSearchFromPrefs(prefs);

        for(int i=0; i<newsDesksLength; i++){
            newsDesksSelected[i] = prefs.getBoolean("desk"+i, false);
            checkBoxes[i].setChecked(newsDesksSelected[i]);
        }

        queryInput.setText(search.getQuery());

        switchEnabled = prefs.getBoolean("switchEnabled", false);
        if (switchEnabled) notificationSwitch.setChecked(true);
    }

    private void configureSwitch(){

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean switchIsChecked) {

                mQuery = queryInput.getText().toString();

                boolean emptyQuery = searchMgr.emptyQuery(mQuery);
                boolean noDeskSelected = searchMgr.noDeskSelected(newsDesksSelected);

                if(switchIsChecked){
                    if(emptyQuery) {
                        showToast("Search required");
                        notificationSwitch.setChecked(false);
                    }else if(noDeskSelected) {
                        showToast("Pick at least one topic");
                        notificationSwitch.setChecked(false);
                    }else{

                        //0 - Save to prefs
                        searchMgr.setSearchToPrefs(prefs, mQuery, newsDesksSelected, true);

                        //1 - Create Search Object
                        mNewsDesk = searchMgr.newsDesks(newsDesksSelected);
                        //GET CURRENT DATE
                        mBeginDate = 20180407;
                        mEndDate = 20180407;

                        search = new Search("query", mQuery, mNewsDesk, mBeginDate, mEndDate);

                        //2 - Create Job Manager
                        JobManager.create(getApplicationContext())
                                .addJobCreator(new MyJobCreator());
                        NotificationJob.schedulePeriodic(search);

                        //3 - Show Toast message
                        showToast("Notification saved");
                    }
                }else{
                    queryInput.setText("");
                    for(int i = 0; i <newsDesksLength; i++){
                        checkBoxes[i].setChecked(false);
                        newsDesksSelected[i] = false;
                    }

                    searchMgr.setSearchToPrefs(prefs, "", newsDesksSelected, false);

                    JobManager.instance().cancel(1234);
                }
            }
        });
    }

    private void showToast(String toastTxt){
        Toast.makeText(getApplicationContext(), toastTxt, Toast.LENGTH_LONG).show();
    }
}


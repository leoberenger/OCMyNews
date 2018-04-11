package com.openclassrooms.mynews.Controllers.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.evernote.android.job.JobManager;
import com.openclassrooms.mynews.Models.Search;
import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Utils.Notifications.MyJobCreator;
import com.openclassrooms.mynews.Utils.Notifications.NotificationJob;
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
    @BindView(R.id.activity_notification_toolbar)
    Toolbar mToolbar;

    private final CheckBox [] checkBoxes = new CheckBox [newsDesksLength];

    private SharedPreferences prefs;
    private boolean switchEnabled = false;
    private Search search;
    private SearchMgr searchMgr = SearchMgr.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        this.configureToolbar();

        prefs = getSharedPreferences("notification", MODE_PRIVATE);

        checkBoxes[0] = checkboxArts;
        checkBoxes[1] = checkboxBusiness;
        checkBoxes[2] = checkboxEntrepreneur;
        checkBoxes[3] = checkboxPolitics;
        checkBoxes[4] = checkboxSports;
        checkBoxes[5] = checkboxTravel;

        this.getAndShowSavedNotification();
        this.configureSwitch();
    }

    private void getAndShowSavedNotification(){

        mQuery = prefs.getString("query", "");

        for(int i=0; i<newsDesksLength; i++){
            newsDesksSelected[i] = prefs.getBoolean("desk"+i, false);
            checkBoxes[i].setChecked(newsDesksSelected[i]);
        }

        Log.e("NotifActiv onCreate()", "query = " + mQuery
                + " desk0 = " + newsDesksSelected[0]
                + " desk1 = " + newsDesksSelected[1]
                + " desk2 = " + newsDesksSelected[2]
                + " desk3 = " + newsDesksSelected[3]
                + " desk4 = " + newsDesksSelected[4]
                + " desk5 = " + newsDesksSelected[5]);

        queryInput.setText(mQuery);

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
                        Log.e("NotifAct switchOn Prefs", "query = " + mQuery
                                + " desk0 = " + newsDesksSelected[0]
                                + " desk1 = " + newsDesksSelected[1]
                                + " desk2 = " + newsDesksSelected[2]
                                + " desk3 = " + newsDesksSelected[3]
                                + " desk4 = " + newsDesksSelected[4]
                                + " desk5 = " + newsDesksSelected[5]);

                        //1 - Create Search Object
                        mNewsDesk = searchMgr.newsDesks(newsDesksSelected);

                        search = new Search("query", mQuery, mNewsDesk, 0, 0);
                        Log.e("NotifAct switchOn Job", "query = " + search.getQuery()
                                + " desks = " + search.getNewsDesk()
                                + " beginDate = " + search.getBeginDate()
                                + " endDate = " + search.getEndDate());

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

                    Log.e("NotifAct switchOf Prefs", "query = " + mQuery
                            + " desk0 = " + newsDesksSelected[0]
                            + " desk1 = " + newsDesksSelected[1]
                            + " desk2 = " + newsDesksSelected[2]
                            + " desk3 = " + newsDesksSelected[3]
                            + " desk4 = " + newsDesksSelected[4]
                            + " desk5 = " + newsDesksSelected[5]);

                    if(JobManager.instance() != null)
                        JobManager.instance().cancel(1234);
                }
            }
        });
    }

    private void showToast(String toastTxt){
        Toast.makeText(getApplicationContext(), toastTxt, Toast.LENGTH_LONG).show();
    }

    private void configureToolbar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}


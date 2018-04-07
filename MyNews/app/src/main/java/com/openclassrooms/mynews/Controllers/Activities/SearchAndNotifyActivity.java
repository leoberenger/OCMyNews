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
import com.openclassrooms.mynews.Utils.SearchAndNotifyJob;
import com.openclassrooms.mynews.Controllers.base.BaseSearchActivity;
import com.openclassrooms.mynews.Utils.SearchMgr;

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
    private Search search;

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

        this.getAndShowSavedNotification();
        this.configureSwitch();
    }

    private void getAndShowSavedNotification(){
        SearchMgr searchMgr = SearchMgr.getInstance();
        search = searchMgr.getSearchFromPrefs(prefs);

        mQuery = search.getQuery();
        switchEnabled = prefs.getBoolean("switch", false);

        for(int i=0; i<newsDesksLength; i++)
            desksAreChecked[i] = prefs.getBoolean("desk"+i, false);

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

        final SearchMgr searchMgr = SearchMgr.getInstance();

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean switchIsChecked) {

                mQuery = queryInput.getText().toString();
                boolean oneTopicSelected = searchMgr.checkMin1DeskSelected(desksAreChecked);

                if(switchIsChecked){
                    if( mQuery.equals("")) {
                        showToast("SearchManager required");
                        notificationSwitch.setChecked(false);
                    }else if( !oneTopicSelected) {
                        showToast("Pick at least one topic");
                        notificationSwitch.setChecked(false);
                    }else{

                        //0 - Create Search Object

                        for(int i = 0; i<newsDesksLength; i++){
                            prefs.edit().putBoolean("desk"+i, desksAreChecked[i]).apply();
                        }
                        mNewsDesk = searchMgr.getNewsDeskName(desksAreChecked, newsDesk);

                        //GET CURRENT DATE

                        mBeginDate = 20180407;
                        mEndDate = 20180407;

                        search = new Search("query", mQuery, mNewsDesk, mBeginDate, mEndDate);

                        //1 - Save SearchManager elements to Preferences
                        prefs.edit().putBoolean("switch", true).apply();
                        searchMgr.setSearchToPrefs(prefs, search);

                        Log.e("NotifActivity", "mQuery="+mQuery+", mNewsDesk = " + mNewsDesk);

                        //2 - Create Job Manager
                        JobManager.create(getApplicationContext())
                                .addJobCreator(new MyJobCreator());
                        SearchAndNotifyJob.schedulePeriodic(search);

                        //3 - Show Toast message
                        showToast("Notification saved");
                    }
                }else{
                    queryInput.setText("");
                    for(int i = 0; i <newsDesksLength; i++)
                        checkBoxes[i].setChecked(false);

                    prefs.edit().putBoolean("switch", false).apply();

                    //ERASE PREFS QUERY = "" AND NEWSDESKS = "" AND BEGIN/END DATE = 0
/*                    search.setQuery("");
                    for(int i = 0; i<newsDesksLength; i++){
                        prefs.edit().putBoolean("desk"+i, false).apply();
                    }
 */
                    JobManager.instance().cancel(1234);
                }
            }
        });
    }

    private void showToast(String toastTxt){
        Toast.makeText(getApplicationContext(), toastTxt, Toast.LENGTH_LONG).show();
    }
}


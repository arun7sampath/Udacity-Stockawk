package com.udacity.stockhawk.sync;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.PersistableBundle;

import com.udacity.stockhawk.ui.MainActivity;

import timber.log.Timber;

public class QuoteJobService extends JobService {


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Timber.d("Intent handled");
        PersistableBundle persistableBundle = jobParameters.getExtras();
        Intent nowIntent = new Intent(getApplicationContext(), QuoteIntentService.class);
        if(persistableBundle != null){
            if(persistableBundle.getString(MainActivity.CHECK_STOCK_KEY) != null) {
                nowIntent.putExtra(MainActivity.CHECK_STOCK_KEY, persistableBundle.getString(MainActivity.CHECK_STOCK_KEY));
            }
        }
        getApplicationContext().startService(nowIntent);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }


}

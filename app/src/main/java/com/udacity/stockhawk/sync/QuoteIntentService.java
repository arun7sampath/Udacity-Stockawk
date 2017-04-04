package com.udacity.stockhawk.sync;

import android.app.IntentService;
import android.content.Intent;

import com.udacity.stockhawk.ui.MainActivity;

import timber.log.Timber;


public class QuoteIntentService extends IntentService {

    public QuoteIntentService() {
        super(QuoteIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Timber.d("Intent handled");
        if(intent.hasExtra(MainActivity.CHECK_STOCK_KEY)){
            QuoteSyncJob.isStockValid(getApplicationContext(), intent.getStringExtra(MainActivity.CHECK_STOCK_KEY));
        }
        else {
            QuoteSyncJob.getQuotes(getApplicationContext());
        }
    }
}

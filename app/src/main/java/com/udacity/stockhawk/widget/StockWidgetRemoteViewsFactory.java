package com.udacity.stockhawk.widget;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

/**
 * Created by Arun on 2/25/17.
 */

public class StockWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Cursor data = null;
    private Context mContext;

    public static final int POSITION_ID = 0;
    public static final int POSITION_SYMBOL = 1;
    public static final int POSITION_PRICE = 2;
    public static final int POSITION_ABSOLUTE_CHANGE = 3;

    public StockWidgetRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        if (data != null) {
            data.close();
            data = null;
        }

        data = mContext.getContentResolver().query(Contract.Quote.URI,
                new String[]{Contract.Quote._ID, Contract.Quote.COLUMN_SYMBOL,
                Contract.Quote.COLUMN_PRICE, Contract.Quote.COLUMN_ABSOLUTE_CHANGE},
                null, null, Contract.Quote.COLUMN_SYMBOL);

    }

    @Override
    public long getItemId(int i) {
        if(data.moveToPosition(i)){
            return data.getLong(POSITION_ID);
        }
        return i;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.getCount();
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public RemoteViews getLoadingView() {
        return new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if (i == AdapterView.INVALID_POSITION ||
                data == null || !data.moveToPosition(i)) {
            return null;
        }
        RemoteViews views = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_list_item);
        views.setTextViewText(R.id.widget_symbol, data.getString(POSITION_SYMBOL));
        views.setTextViewText(R.id.widget_price, data.getString(POSITION_PRICE));
        float absoluteChange = data.getFloat(POSITION_ABSOLUTE_CHANGE);
        if(absoluteChange > 0) {
            views.setTextViewText(R.id.widget_change, "+" + Float.toString(absoluteChange));
            views.setTextColor(R.id.widget_change, Color.GREEN);
        }
        else if(absoluteChange < 0) {
            views.setTextViewText(R.id.widget_change, "-" + Float.toString(absoluteChange));
            views.setTextColor(R.id.widget_change, Color.RED);
        }
        else
            views.setTextViewText(R.id.widget_change, Float.toString(absoluteChange));

        return views;
    }

    @Override
    public void onDestroy() {
        if(data != null){
            data.close();
            data = null;
        }
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

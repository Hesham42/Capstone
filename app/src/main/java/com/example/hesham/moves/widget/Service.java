package com.example.hesham.moves.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by root on 2/24/18.
 */

public class Service extends RemoteViewsService {

      /*
* call the Adapter of the listview
*  Adapter is ListProvider
* */

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        return (new WidgetListProvider(this.getApplicationContext(), intent));
    }

}

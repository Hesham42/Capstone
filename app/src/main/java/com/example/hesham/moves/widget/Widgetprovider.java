package com.example.hesham.moves.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.hesham.moves.R;

/**
 * Implementation of App Widget functionality.
 */
public class Widgetprovider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widgetprovider);
//        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
       final int Number= appWidgetIds.length;
       for (int i=0;i<Number;++i){
           RemoteViews remoteViews= new RemoteViews(context.getPackageName(),
                   R.layout.widgetnote);
           Intent intent = new Intent(context, Service.class);
           intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
           intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
           remoteViews.setRemoteAdapter(appWidgetIds[i], R.id.widget_ListView, intent);
           Intent intent2 = new Intent(context, NoteActiviy.class);
           intent2.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
           PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, 0);

           appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
       }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


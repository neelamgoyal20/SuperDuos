package barqsoft.footballscores.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Random;

import barqsoft.footballscores.R;
import barqsoft.footballscores.service.FootballWidgetService;
import barqsoft.footballscores.service.myFetchService;

/**
 * Created by E01090 on 3/14/2016.
 */
public class FootballWidget extends AppWidgetProvider{


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        Intent intent = new Intent(context, FootballWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[0]);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        rv.setRemoteAdapter(appWidgetIds[0], R.id.stack_view, intent);
        appWidgetManager.updateAppWidget(appWidgetIds[0], rv);

    }
}

package com.byagowi.persiancalendar.daemon;


import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

/**
 * 1x1 widget provider, implementation is on {@code CalendarWidget}
 * 
 * @author ebraminio
 */
public class Widget1x1Listener extends AppWidgetProvider {
	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, Daemon.class));
	}
}

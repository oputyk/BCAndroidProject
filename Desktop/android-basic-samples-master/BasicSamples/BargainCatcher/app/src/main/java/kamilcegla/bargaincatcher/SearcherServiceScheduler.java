package kamilcegla.bargaincatcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SearcherServiceScheduler extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, NewArticleService.class);
        context.startService(serviceIntent);
    }
}

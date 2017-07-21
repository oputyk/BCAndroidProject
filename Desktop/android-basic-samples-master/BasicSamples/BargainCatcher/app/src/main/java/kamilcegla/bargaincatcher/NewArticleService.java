package kamilcegla.bargaincatcher;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import org.json.JSONException;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class NewArticleService extends Service {

    ArticleContainer articleContainer = new ArticleContainerClass();

    @Override
    public void onCreate() {
        super.onCreate();
        /*try {
            articleContainer.setUrl("http://www.otomoto.pl/osobowe/audi/");
            if (articleContainer.isNewNotPromotedArticle()) {
                */notifyAboutNewNotPromotedArticle();
                stopSelf();/*
            }
        } catch (Exception e) {
           e.printStackTrace();
        }*/
    }

    private void notifyAboutNewNotPromotedArticle() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setLights(Color.MAGENTA, 1000, 1000)
                        .setVibrate(new long[] { 0, 1000 })
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Title")
                        .setContentText("Longer text...");

        Intent notifIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, mBuilder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

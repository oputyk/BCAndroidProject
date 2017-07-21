package kamilcegla.bargaincatcher;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBroadCastReceiver();
    }

    private void initBroadCastReceiver() {
        alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, SearcherServiceScheduler.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), 60 * 1000, alarmIntent);
    }

    public void cancelBroadcast(View view) {
        alarmMgr.cancel(alarmIntent);
    }

    /*public TextView textView;
    public ArticleContainer articleContainer;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView);
        initArticleContainer();
        new Thread(new ArticleUpdater()).start();
    }

    private void initArticleContainer() {
        articleContainer = new ArticleContainerClass();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    articleContainer.setUrl("http://www.otomoto.pl/osobowe/audi/");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private class ArticleUpdater extends TimerTask {
        @Override
        public void run() {
            try {
                articleContainer.update();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        printNewArticles();
                        timer.schedule(new ArticleUpdater(), 60000L);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void printNewArticles() {
        List<Article> articleList = articleContainer.getNewNotPromotedArticles();
        StringBuilder stringBuilder = new StringBuilder();
        for(Article article : articleList) {
            stringBuilder.append(article.toString() + "\n");
        }
        stringBuilder.append(textView.getText().toString());
        textView.setText(stringBuilder.toString());
    }*/
}

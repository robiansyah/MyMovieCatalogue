package com.example.mymoviecatalogue.reminder;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.mymoviecatalogue.BuildConfig;
import com.example.mymoviecatalogue.MainActivity;
import com.example.mymoviecatalogue.R;
import com.example.mymoviecatalogue.ui.movie.MovieDetailActivity;
import com.example.mymoviecatalogue.ui.movie.MovieItems;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class ReleaseReceiver extends BroadcastReceiver {

    private static final String API_KEY = BuildConfig.API_KEY;
    private final int ID_REPEATING = 101;
    private ArrayList<MovieItems> listMovie = new ArrayList<>();

    public ReleaseReceiver(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        getReleaseMovie(context);
    }

    private void getReleaseMovie(final Context context) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateToday = sdf.format(date);

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&primary_release_date.gte=" + dateToday + "&primary_release_date.lte=" + dateToday;
        final String img_url = "https://image.tmdb.org/t/p/w185";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < 5; i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MovieItems movieItems = new MovieItems();
                        movieItems.setIdApi(movie.getInt("id"));
                        movieItems.setTitle(movie.getString("title"));
                        movieItems.setPosterPath(img_url + movie.getString("poster_path"));
                        movieItems.setOverview(movie.getString("overview"));
                        movieItems.setVoteAverage(movie.getString("vote_average"));
                        movieItems.setReleaseDate(movie.getString("release_date"));
                        movieItems.setPopularity(movie.getString("popularity"));
                        listMovie.add(movieItems);
                    }
                    showAlarmNotification(context);
                } catch (Exception e) {
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", Objects.requireNonNull(error.getMessage()));
            }
        });
    }

    private void showAlarmNotification(Context context) {
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "Release channel";
        String title = context.getString(R.string.app_name);
        String message, subText;
        Intent intent;
        PendingIntent pendingIntent;

        NotificationManager notificationManager
                = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.baseline_notification_important_white_48dp);

        int numMovie;
        if (listMovie.size() > 0) {
            numMovie = listMovie.size();
        } else {
            numMovie = 0;
        }

        if (numMovie == 0) {
            intent = new Intent(context, MainActivity.class);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            message = context.getString(R.string.no_release);
            builder.setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setLargeIcon(bmp)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(alarmSound)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            if (notificationManager != null) {
                notificationManager.notify(0, builder.build());
            }
        } else {
            intent = new Intent(context, MovieDetailActivity.class);
            for (int i = 0; i < listMovie.size(); i++) {
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIES, listMovie.get(i));
                pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                message = listMovie.get(i).getTitle();
                subText = message + " " + context.getString(R.string.release_today);
                builder.setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setLargeIcon(bmp)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSubText(subText)
                        .setContentIntent(pendingIntent)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setSound(alarmSound)
                        .setAutoCancel(true);
                if (notificationManager != null) {
                    notificationManager.notify(i, builder.build());
                }
            }
        }
    }

    public void setRepeatingAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReceiver.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Toast.makeText(context, R.string.release_reminder_set, Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReceiver.class);
        int requestCode = ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, R.string.release_reminder_unset, Toast.LENGTH_SHORT).show();
    }
}

package shridhar_manages.login;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmService extends IntentService {
    private NotificationManager alarmNotificationManager;
    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sendNotification("EVENT");
    }

    private void sendNotification(String msg) {
        Log.d("ALarmSercie","Preparing to send notifiction"+msg);
        alarmNotificationManager=(NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);



        PendingIntent contentIntent=PendingIntent.getActivity(this,0,new Intent(this,CreateEvent.class),0);
       // Intent dis = new Intent(this,AlarmService.class);
       //PendingIntent dismiss = PendingIntent.getActivity(this,0,dis,PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder alarmNotificationBuilder= new NotificationCompat.Builder(this).setContentTitle("ALARM").setSmallIcon(R.drawable.icon).setStyle(new NotificationCompat.BigTextStyle().bigText(msg)).setContentText(msg).setAutoCancel(true);
       NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.mipmap.alarmoff,"Dismiss",contentIntent).build();
        alarmNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationBuilder.addAction(action);
        alarmNotificationManager.notify(1,alarmNotificationBuilder.build());
        Log.d("ALarmService","Notification sent");

    }
}

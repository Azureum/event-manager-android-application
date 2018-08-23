package shridhar_manages.login;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        CreateEvent inst=CreateEvent.instance();
        //inst.setAlarmText("ALARM. WAKEUP");
        Uri alarmuri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if(alarmuri==null){
            alarmuri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone=RingtoneManager.getRingtone(context,alarmuri);

        ringtone.play();



        ComponentName comp=new ComponentName(context.getPackageName(),AlarmService.class.getName());
        startWakefulService(context,(intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}

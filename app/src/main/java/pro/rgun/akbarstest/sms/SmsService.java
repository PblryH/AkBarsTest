package pro.rgun.akbarstest.sms;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.util.UUID;

import pro.rgun.akbarstest.Application;
import pro.rgun.akbarstest.R;
import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.use_case.NotesCurrentRepository;
import pro.rgun.akbarstest.ui.screen.notes_list.NotesListActivity;

/**
 * Created by rgun on 03.10.16.
 */

public class SmsService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String smsFrom = intent.getExtras().getString("sms_from");
        String smsBody = intent.getExtras().getString("sms_body");
        showNotification(smsFrom,smsBody);

        NotesCurrentRepository mNotesCurrentRepository = ((Application) this.getApplicationContext()).getNotesCurrentRepository();

        Note mNote = new Note();
        mNote.setId(UUID.randomUUID().toString());
        mNote.setDateTimeTS(System.currentTimeMillis());
        mNote.setTitle(smsFrom);
        mNote.setText(smsBody);
        mNotesCurrentRepository.saveNote(mNote, null);

        return START_STICKY;
    }

    private void showNotification(String smsFrom, String smsBody) {
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, NotesListActivity.class), 0);
        Context context = getApplicationContext();
        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle(smsFrom)
                .setContentText(smsBody)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.getNotification();
        notificationManager.notify(R.mipmap.ic_launcher, notification);
    }
}
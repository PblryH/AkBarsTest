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
import pro.rgun.akbarstest.domain.repository.NotesRepository;
import pro.rgun.akbarstest.domain.use_case.NotesCurrentRepository;
import pro.rgun.akbarstest.repository.sqlite.SQLiteNotesRepository;
import pro.rgun.akbarstest.ui.screen.notes_list.NotesListActivity;
import timber.log.Timber;

/**
 * Created by rgun on 03.10.16.
 */

public class SmsService extends Service {

    public static final String SMS_FROM = "sms_from";
    public static final String SMS_BODY = "sms_body";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String smsFrom = intent.getExtras().getString(SMS_FROM);
        String smsBody = intent.getExtras().getString(SMS_BODY);
        showNotification(smsFrom,smsBody);
        saveSmsToStorage(smsFrom,smsBody);
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

    private void saveSmsToStorage(String smsFrom, String smsBody){
        Note note = createNote(smsFrom, smsBody);
        boolean isCurrentScreenNotesList = ((Application) this.getApplication()).isCurrentScreenNotesList();
        Timber.d("isCurrentScreenNotesList = %s", isCurrentScreenNotesList);
        if(isCurrentScreenNotesList) {
            NotesCurrentRepository notesCurrentRepository = ((Application) this.getApplicationContext()).getNotesCurrentRepository();
            notesCurrentRepository.saveNote(note, null);
        }
        else {
            NotesRepository notesRepository = new SQLiteNotesRepository(this);
            notesRepository.saveNote(note, null);
        }
    }

    private Note createNote(String smsFrom, String smsBody){
        Note note = new Note();
        note.setId(UUID.randomUUID().toString());
        note.setDateTimeTS(System.currentTimeMillis());
        note.setTitle(smsFrom);
        note.setText(smsBody);
        return note;
    }
}
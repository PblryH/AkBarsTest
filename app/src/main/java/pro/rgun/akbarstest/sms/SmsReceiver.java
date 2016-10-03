package pro.rgun.akbarstest.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import timber.log.Timber;

/**
 * Created by rgun on 03.10.16.
 */

public class SmsReceiver extends BroadcastReceiver {

    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null && ACTION.compareToIgnoreCase(intent.getAction()) == 0) {
            Object[] pduArray = (Object[]) intent.getExtras().get("pdus");
            SmsMessage[] messages = new SmsMessage[pduArray.length];
            for (int i = 0; i < pduArray.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pduArray[i]);
            }

            String smsFrom = messages[0].getDisplayOriginatingAddress();
            StringBuilder bodyText = new StringBuilder();
            for (int i = 0; i < messages.length; i++) {
                bodyText.append(messages[i].getMessageBody());
            }
            String smsBody = bodyText.toString();

            Timber.d("smsFrom = %s", smsFrom);
            Timber.d("smsBody = %s", smsBody);


            Intent mIntent = new Intent(context, SmsService.class);
            mIntent.putExtra("sms_from", smsFrom);
            mIntent.putExtra("sms_body", smsBody);
            context.startService(mIntent);

            abortBroadcast();
        }
    }
}

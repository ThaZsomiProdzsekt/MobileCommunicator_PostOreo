package com.example.vpm.communicatorapp_postoreo;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telecom.TelecomManager;
import android.telephony.PhoneStateListener;
import android.widget.Toast;
import android.telephony.TelephonyManager;


public class PhoneStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {

            TelecomManager tm = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);

            if (tm == null) {
                // whether you want to handle this is up to you really
                throw new NullPointerException("tm == null");
            }

            tm.acceptRingingCall();

            System.out.println("Receiver start");
            TelephonyManager tmgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            MyPhoneStateListener PhoneListener = new MyPhoneStateListener(context);

            tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

            Toast.makeText(context," Receiver start + ",Toast.LENGTH_SHORT).show();
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            System.out.println("inc numer: " + incomingNumber);

            if(state.equals(TelephonyManager.EXTRA_INCOMING_NUMBER)){
                //Toast.makeText(context,"Ringing State is -",Toast.LENGTH_SHORT).show();
                Toast.makeText(context,"Ringing State Number is - " + incomingNumber, Toast.LENGTH_SHORT).show();
                System.out.println("inc numer: " + incomingNumber);
            }
            if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))){
                Toast.makeText(context,"Received State",Toast.LENGTH_SHORT).show();
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                Toast.makeText(context,"Idle State",Toast.LENGTH_SHORT).show();
            }
        }
        catch (SecurityException ex) {
            ex.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private class MyPhoneStateListener extends PhoneStateListener {
        // Ez a szar nem kell, valszeg mobilbeállítással is meg lehet oldani.
        Context pcontext;

        MyPhoneStateListener(Context cont){
            super();
            pcontext = cont;
        }

        public void onCallStateChanged(int state, String incomingNumber) {

            System.out.println(" incoming no:"+incomingNumber);

            // state = 1 means when phone is ringing
            if (state == 1) {

                String msg = " New Phone Call Event. Incomming Number : "+incomingNumber;
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(pcontext, msg, duration);
                toast.show();

            }
        }
    }
}

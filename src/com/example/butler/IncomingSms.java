package com.example.butler;

import android.content.*;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class IncomingSms extends BroadcastReceiver {
    
    
    @Override
    public void onReceive(Context context, Intent intent) {
     
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        boolean butlerActive = sharedPref.getBoolean("butlerActive", false);
        if(butlerActive) {
		    try {
		         
		        if (bundle != null) {
		             
		            final Object[] pdusObj = (Object[]) bundle.get("pdus");
		                 
		                for (int i = 0; i < pdusObj.length; i++) {
		                     
		                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
		                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
		                     
		                    String senderNum = phoneNumber;
		                    String message = currentMessage.getDisplayMessageBody();
		 
		                    
		                   // Show Alert
		                int duration = Toast.LENGTH_SHORT;
		                //Toast.makeText(context, "senderNum: "+ senderNum + ", message: " + message, duration).show();
		                String messageReply = sharedPref.getString("messageTest", "Automated message: Can't reply right now.");
		                try {
		                   SmsManager smsManager = SmsManager.getDefault();
		               	   smsManager.sendTextMessage(senderNum, null, messageReply, null, null);
		               	   Toast.makeText(context, 
		                         "Replied to "+senderNum+"!", duration).show();
		                }catch(Exception e) {
		                	Toast.makeText(context,
		                	    "SMS reply failed.",
		                	    Toast.LENGTH_SHORT).show();
		                	e.printStackTrace();
		                }
		                 
		            } // end for loop
		          } // bundle is null
		 
		        } catch (Exception e) {
		        	Toast.makeText(context,
		        	           "SMS faild, please try again.",
		    	           Toast.LENGTH_SHORT).show();
		    	           e.printStackTrace();             
		    }
        }
    }    
}

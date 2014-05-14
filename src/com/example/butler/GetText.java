package com.example.butler;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.inputmethod.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;


public class GetText extends Activity {
	
	   EditText txtMessage;
	   boolean butlerActive;
	   SharedPreferences sharedPref;
	   Button testButton;
	   int notificationID = 257;
       NotificationManager mNotificationManager;
			   // (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
		
	public static void hideSoftKeyboard(Activity activity) {
	    InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}
	
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN) @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_text);
        txtMessage = (EditText) findViewById(R.id.userText);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        butlerActive = sharedPref.getBoolean("butlerActive", false);
        testButton = (Button) findViewById(R.id.button1);
        
        if(butlerActive) {
    		testButton.setText("DISABLE");
    		
    	}
        else {
    		testButton.setText("ENABLE");
        }

    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN) public void sendMessage(View view) {
		Editor edit = sharedPref.edit();
		
    	if(butlerActive) {
    		edit.putBoolean("butlerActive", false);
    		butlerActive = false;
    		testButton.setText("ENABLE");
        	Toast.makeText(getApplicationContext(), "Disabled Butler", Toast.LENGTH_SHORT).show();
        	 mNotificationManager =
			    (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
			 mNotificationManager.cancel(notificationID);

    	}
    	else {
    		edit.putBoolean("butlerActive", true);
    		edit.putString("messageTest", txtMessage.getText().toString());
    		butlerActive = true;
    		testButton.setText("DISABLE");
        	Toast.makeText(getApplicationContext(), "Enabled Butler", Toast.LENGTH_SHORT).show();
        	NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
    		mBuilder.setSmallIcon(R.drawable.ic_launcher);
    		mBuilder.setContentTitle("Butler");
    		mBuilder.setContentText("Serving");
    		mBuilder.setOngoing(true);
    		
    		 /* Creates an explicit intent for an Activity in your app */
    	      Intent resultIntent = new Intent(this, GetText.class);

    	      TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
    	      stackBuilder.addParentStack(GetText.class);

    	      /* Adds the Intent that starts the Activity to the top of the stack */
    	      stackBuilder.addNextIntent(resultIntent);
    	      PendingIntent resultPendingIntent =
    	         stackBuilder.getPendingIntent(
    	            0,
    	            PendingIntent.FLAG_UPDATE_CURRENT
    	         );

    	      mBuilder.setContentIntent(resultPendingIntent);

    	      mNotificationManager =
    	      (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);

    	      /* notificationID allows you to update the notification later on. */
    	      mNotificationManager.notify(notificationID, mBuilder.build());
    		
    	}
    	edit.apply();
    	
    	
    	/*String phoneNo = "6178173172";
        String message = txtMessage.getText().toString();

        try {
           SmsManager smsManager = SmsManager.getDefault();
      	   smsManager.sendTextMessage(phoneNo, null, message, null, null);
           Toast.makeText(getApplicationContext(), "SMS sent.",
           Toast.LENGTH_LONG).show();
        } catch (Exception e) {
           Toast.makeText(getApplicationContext(),
           "SMS faild, please try again.",
           Toast.LENGTH_LONG).show();
           e.printStackTrace();
        }	*/
    }
    
}
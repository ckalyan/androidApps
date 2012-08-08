package greyCells.com.whatsup;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
	private final static String TAG = "WhatsUp/MainActivity";
	private static final String HOURS_VALUE = "greycells.com.Whatsup.HOURS";
	private static final String MINUTES_VALUE ="greycells.com.Whatsup.MINUTES"; 
	public int mHours;
	public int mMinutes;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //default ping period = 1 hour
        mHours = 1;
        mMinutes = 0;
        
        /*Intent startServiceIntent = new Intent(this,WhatsUpService.class);
        startServiceIntent.putExtra(HOURS_VALUE,hours);
		startServiceIntent.putExtra(MINUTES_VALUE,minutes);		
        startService(startServiceIntent);*/       
        Log.e(TAG,"sent new update value as "+mHours+" hrs, "+mMinutes+" mins");
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(), "Started monitoring service",Toast.LENGTH_SHORT).show();
        setRecurringAlarm();
    }

    //the button has been set to invoke this method in the manifest
    public void SetButtonOnClick(View v){
    	//Take input from hours and minutes if it happens, and set hours and minutes accordingly.
		String stringHours = ((EditText)findViewById(R.id.hoursText)).getText().toString();
		
		if (stringHours.length() > 0)
			mHours = Integer.parseInt(stringHours);
		
		String stringMinutes = ((EditText)findViewById(R.id.minutesText)).getText().toString();
		if (stringMinutes.length() > 0)
			mMinutes = Integer.parseInt(stringMinutes);
		
		/*Intent updatePeriodIntent = new Intent(this.getApplicationContext(),WhatsUpService.class);
		updatePeriodIntent.putExtra(HOURS_VALUE,hours);
		updatePeriodIntent.putExtra(MINUTES_VALUE,minutes);
		startService(updatePeriodIntent);
		Log.e(TAG,"sent new update value as "+hours+" hrs, "+minutes+" mins");*/
		Toast.makeText(getApplicationContext(), "Updated monitoring service",Toast.LENGTH_SHORT).show();
    }
    
    private void setRecurringAlarm() {
    	Calendar time = Calendar.getInstance();
    	
    	//reduce mMinutes and mHours to hh:mm format
    	if (mMinutes > 60){
    		mHours= mMinutes%60;
    		mMinutes = mMinutes - (mHours*60);
    	}
    	if (mHours > 24) {
    		mHours = 24; //maximum ping period is 1 day :)
    	}
    	//set time to current time + increment.
    	time.set(Calendar.HOUR_OF_DAY, (Calendar.HOUR_OF_DAY + mHours > 23) ? 0 :Calendar.HOUR_OF_DAY + mHours);
    	time.set(Calendar.MINUTE, mMinutes);
    	
    	//set up the alarm
    	Intent updater = new Intent(getApplicationContext(),AlarmReceiver.class);
    	PendingIntent IntentToSetUpAlert = PendingIntent.getBroadcast(getApplicationContext(), 0, updater, PendingIntent.FLAG_CANCEL_CURRENT);
    	AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    	alarm.setRepeating(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), AlarmManager.INTERVAL_DAY, IntentToSetUpAlert);
    }
}

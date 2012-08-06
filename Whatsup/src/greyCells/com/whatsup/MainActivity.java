package greyCells.com.whatsup;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
	private static final String HOURS_VALUE = "greycells.com.Whatsup.HOURS";
	private static final String MINUTES_VALUE ="greycells.com.Whatsup.MINUTES"; 
	public int hours;
	public int minutes;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //default ping period = 1 hour
        hours = 1;
        minutes = 0;
        
        Intent startServiceIntent = new Intent(this,WhatsUpService.class);
        startServiceIntent.putExtra(HOURS_VALUE,hours);
		startServiceIntent.putExtra(MINUTES_VALUE,minutes);		
        startService(startServiceIntent);       
        
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(), "Started monitoring service",Toast.LENGTH_SHORT).show();
    }

    //the button has been set to invoke this method in the manifest
    public void SetButtonOnClick(View v){
    	//Take input from hours and minutes if it happens, and set hours and minutes accordingly.
		String stringHours = ((EditText)findViewById(R.id.hoursText)).getText().toString();
		
		if (stringHours.length() > 0)
			hours = Integer.parseInt(stringHours);
		
		String stringMinutes = ((EditText)findViewById(R.id.minutesText)).getText().toString();
		if (stringMinutes.length() > 0)
			minutes = Integer.parseInt(stringMinutes);
		
		Intent updatePeriodIntent = new Intent(this.getApplicationContext(),WhatsUpService.class);
		updatePeriodIntent.putExtra(HOURS_VALUE,hours);
		updatePeriodIntent.putExtra(MINUTES_VALUE,minutes);
		startService(updatePeriodIntent);
		
		Toast.makeText(getApplicationContext(), "Updated monitoring service",Toast.LENGTH_SHORT).show();
    }
}

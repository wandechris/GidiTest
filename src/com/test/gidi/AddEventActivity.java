package com.test.gidi;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.test.gidi.database.DatabaseHelper;
import com.test.gidi.reciever.PingService;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class AddEventActivity extends SherlockFragmentActivity {
	
	EditText name;
	EditText address;
	public static EditText date;
	TextView gps;
	public static EditText time;
	
	Button save;
	Button timePicker;
	Button datepicker;
	Button gpspicker;
	private ProgressDialog pDialog;
	Spinner type;
	Intent mServiceIntent;
	SimpleDateFormat format;
	String date_gotten;
	Date date1;
	Date date2;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_event);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		mServiceIntent = new Intent(getApplicationContext(), PingService.class);
		
		name = (EditText)findViewById(R.id.editName);
		address = (EditText)findViewById(R.id.address);
		date = (EditText)findViewById(R.id.editdate);
		time = (EditText)findViewById(R.id.editTime);
		
		timePicker = (Button)findViewById(R.id.pickTme);
		datepicker = (Button)findViewById(R.id.pickdate);
		gpspicker = (Button)findViewById(R.id.pickLoc);
		gps = (TextView)findViewById(R.id.typeView);
		save = (Button)findViewById(R.id.save);
		
		type = (Spinner)findViewById(R.id.type);
		 format = new SimpleDateFormat("yyyy-MM-dd HH:mm:00", Locale.ENGLISH);
		  date1 = new Date();
		   // event.setGps();
		
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Event event = new Event();
				 event.setName(name.getText().toString());
				    event.setAddress(address.getText().toString());
				    event.setDate(date.getText().toString());
				    event.setTime(time.getText().toString());
				    event.setType(type.getSelectedItem().toString());
				    event.setGps(gps.getText().toString());
				    date_gotten = date.getText().toString()+" "+time.getText().toString();
				    
				    try {
						date2 = (Date)format.parse(date_gotten);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				    long differenceInMillis = date2.getTime() - date1.getTime();
					mServiceIntent.putExtra("name", name.getText().toString());
					mServiceIntent.putExtra("extra", differenceInMillis);
					mServiceIntent.putExtra("id", date2.getTime());
			        mServiceIntent.setAction("set");
			        
			        startService(mServiceIntent);
			        
				    saveEventToDB save = new saveEventToDB();
				    save.execute(event);
				
			}
		});
		
		gpspicker.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(AddEventActivity.this, MapActivity.class);
				startActivityForResult(i, 1);
			}
		});
		
		
		
		
	}
	
	public void showTimePickerDialog(View v) {
	    SherlockDialogFragment newFragment = new  TimePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "timePicker");
	}
	public void showDatePickerDialog(View v) {
		SherlockDialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "datePicker");
	}
	
	class saveEventToDB extends AsyncTask<Event, String, Event> {

		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(AddEventActivity.this);
			pDialog.setMessage("Saving...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		
		@Override
		protected Event doInBackground(Event... args) {
			DatabaseHelper DbHelper = new DatabaseHelper(getApplicationContext());
			DbHelper.addEvent(args[0]);
			return args[0];
		}

		
		protected void onPostExecute(Event args) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			
			Intent intent = new Intent(AddEventActivity.this,MainActivity.class);
			   startActivity(intent);
			
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK && requestCode == 1) {
	    if (data.hasExtra("location")) {
	    	String h = data.getExtras().getString("location");
	    	gps.setText(h);
	    }
	  }
	} 
	public static class TimePickerFragment extends SherlockDialogFragment
    implements TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);
			
			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
			DateFormat.is24HourFormat(getActivity()));
		}
		
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// Do something with the time chosen by the user
			String s;
			Format formatter;
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
			calendar.set(Calendar.MINUTE, minute);

			formatter = new SimpleDateFormat("HH:mm:00", Locale.ENGLISH);
			s = formatter.format(calendar.getTime()); // 08:00:00
			time.setText(s);
		}
	}
	
	public static class DatePickerFragment extends SherlockDialogFragment
    implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		
		public void onDateSet(DatePicker view, int year, int month, int day) {
		// Do something with the date chosen by the user
			Calendar c = Calendar.getInstance();
			c.set(year, month, day);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			String formattedDate = sdf.format(c.getTime());  
			date.setText(formattedDate);
		}
	}
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        case android.R.id.home:
             finish();
             break;
       

        default:
            return super.onOptionsItemSelected(item);
        }
        return false;
    }
}

package com.test.gidi;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.test.gidi.database.DatabaseHelper;

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

public class EditEventActivity extends SherlockFragmentActivity {

	EditText name;
	EditText address;
	public static EditText date;
	TextView gps;
	public static EditText time;
	
	Button save;
	Button timePicker;
	Button datepicker;
	private ProgressDialog pDialog;
	Spinner type;
	Bundle b;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_event);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		name = (EditText)findViewById(R.id.editName);
		address = (EditText)findViewById(R.id.address);
		date = (EditText)findViewById(R.id.editdate);
		time = (EditText)findViewById(R.id.editTime);
		
		 b = getIntent().getExtras();
		name.setText( b.getString("name"));
		address.setText( b.getString("address"));
		date.setText( b.getString("date"));
		time.setText( b.getString("time"));
		
		timePicker = (Button)findViewById(R.id.pickTme);
		datepicker = (Button)findViewById(R.id.pickdate);
		save = (Button)findViewById(R.id.save);
		save.setText("Update");
		type = (Spinner)findViewById(R.id.type);
		
		
		
		
		
	 
		   
		   // event.setGps();
		
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Event event = new Event();
				event.setId(b.getString("id"));
				 event.setName(name.getText().toString());
				    event.setAddress(address.getText().toString());
				    event.setDate(date.getText().toString());
				    event.setTime(time.getText().toString());
				    event.setType(type.getSelectedItem().toString());
				 //Log.v("edit",name.getText().toString());
				//DatabaseHelper DbHelper = new DatabaseHelper(getApplicationContext());
				//DbHelper.addEvent(event);
				    saveEventToDB save = new saveEventToDB();
				    save.execute(event);
				
			}
		});
		
		
		
		
	}
	
	public void showTimePickerDialog(View v) {
	    SherlockDialogFragment newFragment = new TimePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "timePicker");
	}
	public void showDatePickerDialog(View v) {
		SherlockDialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "datePicker");
	}
	
	class saveEventToDB extends AsyncTask<Event, String, String> {

		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditEventActivity.this);
			pDialog.setMessage("Updating...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		
		@Override
		protected String doInBackground(Event... args) {
			DatabaseHelper DbHelper = new DatabaseHelper(getApplicationContext());
			DbHelper.updateEvent(args[0]);
			   
			return "done";
		}

		
		protected void onPostExecute(String args) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			Intent intent = new Intent(EditEventActivity.this,MainActivity.class);
			   startActivity(intent);
			
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

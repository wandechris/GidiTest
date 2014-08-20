package com.test.gidi;


import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewActivity extends SherlockActivity {
	TextView name;
	TextView type;
	TextView address;
	Button dir;
	ImageView banner;
	Bundle b;
	GPSTracker gps;
	String coor;
	private ShareActionProvider mShareActionProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_event);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		
		
		
		
		
		name = (TextView)findViewById(R.id.nameView);
		address = (TextView)findViewById(R.id.addressView);
		
		dir = (Button)findViewById(R.id.btnDir);
		
		b = getIntent().getExtras();
		name.setText(b.getString("name"));
		coor = b.getString("gps");
		
		address.setText(b.getString("address"));
		
		gps = new GPSTracker(ViewActivity.this);
		dir.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String Str = new String(coor);
				Str.replace('(', ' ');
				Str.replace(')', ' ');
				String[] parts = Str.split(":");
				String[] part = parts[1].split(",");
				String lat = part[0];
				String lng = part[1];
				String slat = Double.toString(gps.getLatitude());
				String slon = Double.toString(gps.getLongitude());
				//LatLng userLoc = new LatLng(gps.getLatitude(), gps.getLongitude());
				//LatLng placeLoc = new LatLng(latitude,longitude);
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
					    Uri.parse("http://maps.google.com/maps?f=d&saddr="+slat+","+ slon+"&daddr="+lat+","+lng));
					startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate menu resource file.
	    getSupportMenuInflater().inflate(R.menu.main, menu);

	    // Locate MenuItem with ShareActionProvider
	    MenuItem item = menu.findItem(R.id.menu_item_share);
	    // Fetch and store ShareActionProvider
	    mShareActionProvider = (ShareActionProvider) item.getActionProvider();
	    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, b.getString("name"));
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, b.getString("address"));
        mShareActionProvider.setShareIntent(sharingIntent);
	    

	   

	    // Return true to display menu
	    return true;
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

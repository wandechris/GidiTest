package com.test.gidi;



import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;

public class MapActivity extends SherlockFragmentActivity {
	
	GoogleMap map;
	LatLng location;
	GPSTracker gps;
	AlertDialogManager alert = new AlertDialogManager();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		alert.showAlertDialog(MapActivity.this, "Hint", "Navigate to the location of the place and touch the location to set it...", false);
		
		gps = new GPSTracker(this);
		
		LatLng userLoc = new LatLng(gps.getLatitude(), gps.getLongitude());
		
		setContentView(R.layout.map);
	 SupportMapFragment fr = ((SupportMapFragment) this.getSupportFragmentManager().findFragmentById(R.id.map));
		
		map = fr.getMap();
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		CameraUpdate center = CameraUpdateFactory.newLatLng(userLoc);
		CameraUpdate zoom=CameraUpdateFactory.zoomTo(16);
		map.moveCamera(center);
		map.animateCamera(zoom);
		map.setOnMapClickListener(new OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng loc) {
				location = loc;
				alert.showAlertDialogMap(MapActivity.this, "Location", Html.fromHtml("<b>is this the location?</b>").toString(), false);
				
			}
		});
		
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        case android.R.id.home:
        	location=new LatLng(0.00, 0.00);
             finish();
             break;
       

        default:
            return super.onOptionsItemSelected(item);
        }
        return false;
    }

	
	
	@Override
	public void finish() {
		Intent data = new Intent();
		  data.putExtra("location", location.toString());
		  setResult(RESULT_OK, data);
		super.finish();
	}


}

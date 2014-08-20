package com.test.gidi.adapters;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.test.gidi.Event;
import com.test.gidi.GPSTracker;
import com.test.gidi.R;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EventAdapter extends BaseAdapter {
	
	private Activity context;
    private List<Event> data;
    private static LayoutInflater inflater = null;
    
    public EventAdapter(Activity a, List<Event> eventListItems){
    	context = a;
        data = eventListItems;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
		
		
        if(convertView == null)
            vi = inflater.inflate(R.layout.event_list_item, null);

        TextView name=(TextView)vi.findViewById(R.id.item_name);
        TextView location=(TextView)vi.findViewById(R.id.item_loc);
        TextView duration=(TextView)vi.findViewById(R.id.item_days);
        String date_date = data.get(position).getDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:00", Locale.ENGLISH);
        try {
		Date datet2;
		
			datet2 = (Date)format.parse(date_date);
		
			 Date dt = new Date();
		        String now = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).format(dt);
		        String now2 = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).format(datet2);
		      
		       if (now.equals(now2)){
		        	vi.setBackgroundColor(0xff888888);
		       }
       
        } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //String Str = new String(data.get(position).getGps());
		//Str.replace('(', ' ');
		//Str.replace(')', ' ');
		//String[] parts = Str.split(":");
		//String[] part = parts[1].split(",");
		//String lat = part[0];
		//String lng = part[1];
		
        GPSTracker track = new GPSTracker(context);
        Location loc,loc2;
        loc = new Location("");
        loc2 = new Location("");
        loc.setLatitude(track.getLatitude());
        loc.setLongitude(track.getLongitude());
        
       // loc2.setLatitude(Double.parseDouble(lat));
       // loc2.setLongitude(Double.parseDouble(lng));
        
        double radius = 50.0;
        double distance = loc.distanceTo(loc2);
        if (distance < radius){
        	vi.setBackgroundColor(0xff888888);
        }
        
        name.setText(data.get(position).getName());
        location.setText(data.get(position).getAddress());
        duration.setText(data.get(position).getType());
        
        return vi;
    }
	

}

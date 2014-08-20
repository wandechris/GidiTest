package com.test.gidi.fragments;


import java.util.List;

import com.actionbarsherlock.app.SherlockListFragment;
import com.test.gidi.EditEventActivity;
import com.test.gidi.Event;
import com.test.gidi.R;
import com.test.gidi.ViewActivity;
import com.test.gidi.adapters.EventAdapter;
import com.test.gidi.database.DatabaseHelper;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EventListFragment extends SherlockListFragment {
	EventAdapter adapter;
	List<Event> events;
	DatabaseHelper DBHelper;

	
	

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 DBHelper = new DatabaseHelper(getActivity().getApplicationContext());
			events = DBHelper.getAllEvents();
			
			Log.v("Read", events.toString());
			setEmptyText("No Event: touch the blue button to add");
			adapter = new EventAdapter(getActivity(), events);
			setListAdapter(adapter);
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

	        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
	                final int pos, long id) {
	            
	        	
	        	String[] values = new String[] { "Edit", "Delete"};
				// TODO Auto-generated method stub
				final Dialog dlg=new Dialog(getActivity());
				dlg.setTitle(events.get(pos).getName());
				dlg.setContentView(R.layout.list_remove);
				ListView list = (ListView)dlg.findViewById(R.id.list_re);
				
				ArrayAdapter<String> adapter2 = new  ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
				list.setAdapter(adapter2);
				
				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> adapterv2, View view2,
							int pos2, long item) {
						if (pos2 == 0){
							Event event = events.get(pos);
							Intent intent = new Intent(getActivity(),EditEventActivity.class);
							
							intent.putExtra("name", event.getName());
							intent.putExtra("address", event.getAddress());
							intent.putExtra("date", event.getDate());
							intent.putExtra("time", event.getTime());
							intent.putExtra("type", event.getType());
							intent.putExtra("id", event.getId());
							intent.putExtra("gps", event.getGps());
							getActivity().startActivity(intent);
							dlg.dismiss();	
						}else{
							
							
							DBHelper.deleteEvent(events.get(pos));
							events.remove(pos);
							
							adapter.notifyDataSetChanged();
							dlg.dismiss();
						}
						
					}
				});
				dlg.show();
			

	            return true;
	        }
	    }); 
		super.onActivityCreated(savedInstanceState);
	}

	


	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		Event event = events.get(position);
		Intent intent = new Intent(getActivity(),ViewActivity.class);
		
		intent.putExtra("name", event.getName());
		intent.putExtra("address", event.getAddress());
		intent.putExtra("date", event.getDate());
		intent.putExtra("time", event.getTime());
		intent.putExtra("type", event.getType());
		intent.putExtra("id", event.getId());
		intent.putExtra("gps", event.getGps());
		getActivity().startActivity(intent);
		super.onListItemClick(l, v, position, id);
	}
	
	
	
	
	
}

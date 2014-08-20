package com.test.gidi;



import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.test.gidi.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends SherlockFragmentActivity {
	
	Button add;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		 add = (Button)findViewById(R.id.add_butto);

	        add.setOnClickListener(new OnClickListener() {
	                public void onClick(View v) {
	                    //do onActivity for result
	                	Intent i = new Intent(MainActivity.this, AddEventActivity.class);
	    				startActivity(i);
	                }

	});
	
}
	
	
}

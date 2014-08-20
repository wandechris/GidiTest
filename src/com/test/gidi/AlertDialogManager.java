package com.test.gidi;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class AlertDialogManager {
	/**
	 * Function to display simple Alert Dialog
	 * @param context - application context
	 * @param title - alert dialog title
	 * @param message - alert message
	 * @param status - success/failure (used to set icon)
	 * 				 - pass null if you don't want icon
	 * */
	
	public static int radius;
	public void showAlertDialog(final Activity context, String title, String message,
			Boolean status) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		/*if(status != null)
			// Setting alert dialog icon
			alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);*/
		

		// Setting OK Button
		alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//context.finish();
			}
		});
		
		alertDialog.create();

		// Showing Alert Message
		alertDialog.show();
	}
	

	public void showAlertDialogMap(final Activity context, String title, String message,
			Boolean status) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		/*if(status != null)
			// Setting alert dialog icon
			alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);*/
		

		// Setting OK Button
		alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				context.finish();
			}
		});
		
		alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		
		alertDialog.create();

		// Showing Alert Message
		alertDialog.show();
	}
	
	public void showAlertDialogC(final Activity context, String title, String message,
			Boolean status) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		/*if(status != null)
			// Setting alert dialog icon
			alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);*/
		

		// Setting OK Button
		alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				context.finish();
			}
		});
		
		alertDialog.create();

		// Showing Alert Message
		alertDialog.show();
	}

}


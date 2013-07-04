package com.example.locationact;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.audiofx.BassBoost.Settings;

public class enableGps extends Activity
{
	/*@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
}*/
	

	public void showSettingsAlert(final Context mContext){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
 
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");
 
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
 
        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);
 
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
 
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
    }
}

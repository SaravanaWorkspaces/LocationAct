package com.example.locationact;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

public class locationName_class 
{
	Context mcontext;
	public locationName_class(Context con) 
	{
		mcontext=con;
	}

	public String findingName(double lat, double lon) 
	{
		String returnedStmt;
		Geocoder gcd = new Geocoder(mcontext, Locale.getDefault());
		 try {
			 
			 List<Address> addresses = gcd.getFromLocation(lat,lon, 3);
			 
			  if(addresses != null) 
			  {
				  Address returnedAddress = addresses.get(0);
			   
				  StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
				  
			   for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++)
			   {
				   strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
			   }
			   	returnedStmt=strReturnedAddress.toString();
			
			   	}
			  else
			  {
				  returnedStmt="No Address returned!";
			   }
			 }
		 catch (IOException e) 
			 {
			  	e.printStackTrace();
			  	returnedStmt=e.toString();
			 }
		return returnedStmt;

	
		
	}
	
	
	
}

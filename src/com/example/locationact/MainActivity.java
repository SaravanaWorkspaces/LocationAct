package com.example.locationact;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.telephony.SignalStrength;
import android.telephony.SmsManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity 
{
	
Button but,layoutClose,t,dep,del;



	ContentValues conValuesforcolName=new ContentValues();

	//String filePath="/sdcard/";
	String filePath="/mnt/sdcard/";
	String filename,selecedFile,locName="No location found",time;
	
	int startingTimeformeg=60000*1,megforthistime=60000*1; 
	
	int colno=0,arylistSize,rowN=0,xlFilecount=0;
	
	ArrayList<String> contactss,sqlContacts;
	
	ArrayList<String> fileNames=new ArrayList<String>();
	
	LocationManager locationManager;
	
	LocationListener listener;
	
	Button start_ForlocationUpdateButton,ext_frmlocationupdate,load_contactsButton,gpsonoff;
	
	TextView tv;
	
	locationName_class obj;
	
	database_Class objofColName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		t=(Button)findViewById(R.id.button2);
		dep=(Button)findViewById(R.id.button3);
		del=(Button)findViewById(R.id.button4);

	
		tv=(TextView)findViewById(R.id.textView1);
		
		load_contactsButton=(Button)findViewById(R.id.load_contatcs);
	
		start_ForlocationUpdateButton=(Button)findViewById(R.id.startFor_locationupdate);
		
		ext_frmlocationupdate=(Button)findViewById(R.id.extfrm_locationupdate);
		
		gpsonoff=(Button)findViewById(R.id.Gpson_offButton);
		
		
		
		locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
		
		obj=new locationName_class(MainActivity.this);
		
		checkForgps();
		try
		{
			
			Log.i("GT","GTT");
			
			
			gpsonoff.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View view) 
				{
					if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
					{
						gpsonoff.setText("ON");
	                    Toast.makeText(getApplicationContext(), "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
					}
					else
					{
						//To callings GPS settings Intent
						//gpsonoff.setText("OFF");
						enableGps di=new enableGps();
						di.showSettingsAlert(MainActivity.this);
					}
					
				}
			});
			
			//ticket checking
			t.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					Intent i=new Intent(MainActivity.this,ticket.class);
					startActivity(i);
				}
			});
			
			
			//depature
			dep.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					Intent depintent=new Intent(MainActivity.this,depature.class);
					startActivity(depintent);
				}
			});
			
			
	//delayed
			del.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0)
				{
					/*database_Class db=new database_Class(MainActivity.this,"pa",null,1);
					ArrayList<String> ary=db.readownerandoffice();*/
					
					Intent iii=new Intent(MainActivity.this,delay.class);
					Bundle bundle=new Bundle();
					/*bundle.putStringArrayList("owner",ary);*/
					bundle.putString("location",locName);
					iii.putExtras(bundle);
					startActivity(iii);
				}
			});
			
		
			
		//load contacts into SQlite
			load_contactsButton.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View view) 
				{
					fileNames.clear();
					list_ofexcelFiles();
					
					
					final Dialog dialog = new Dialog(MainActivity.this);
					dialog.setContentView(R.layout.loadingcontacts_design);
					dialog.setTitle("Select File...");

					
					ListView listV=(ListView)dialog.findViewById(R.id.listView1);
					layoutClose=(Button)dialog.findViewById(R.id.button1);
					
				
					ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,fileNames);
					
					listV.setAdapter(adapter);
					listV.setOnItemClickListener(new OnItemClickListener()
					{
						@Override
						public void onItemClick(AdapterView<?> arg0, View view,
								int arg2, long arg3) 
						{
							Log.i("SELECTED",""+fileNames.get(arg2));
							selecedFile=fileNames.get(arg2);
							view.setBackgroundColor(Color.RED);
						}
					});
					dialog.show();
					//close layout
					layoutClose.setOnClickListener(new OnClickListener() 
					{
						
						@Override
						public void onClick(View arg0) 
						{
							dialog.dismiss();
							if(xlFilecount>0)
							{
							reading_excelData();
							Log.i("Excel available","Excel Available");
							contactss=objofColName.read();
							//Log.i("CONTACTS"+contactss.size(),"contacts"+contactss.size());
							}
							
							sqlreader();
							
							//send sms half an hour once
													
							
							final Timer mytimer = new Timer(true);

				            final TimerTask mytask = new TimerTask() {
				                public void run() 
				                {
				                	sendMessager();
				                }
				            };

				            mytimer.schedule(mytask, startingTimeformeg,megforthistime*3);
						}
					});
					
				}
			});
			
	
		//Start for location update	
		start_ForlocationUpdateButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
				{
					gpsonoff.setText("ON");
					//immediate_currentlocation();
				}
				else
				{
					//To callings GPS settings Intent
					enableGps di=new enableGps();
					di.showSettingsAlert(MainActivity.this);
				}
				//To get currrent location immediately
				//immediate_currentlocation();
								
				listener =new LocationListener() {
					
					@Override
					public void onStatusChanged(String provider, int status, Bundle arg2) 
					{
						switch (status) {
						case LocationProvider.AVAILABLE:
							Toast.makeText(MainActivity.this,"Available",Toast.LENGTH_LONG).show();
							break;
						case LocationProvider.OUT_OF_SERVICE:
							Toast.makeText(MainActivity.this,"out of service",Toast.LENGTH_LONG).show();
							break;
						case LocationProvider.TEMPORARILY_UNAVAILABLE:
							Toast.makeText(MainActivity.this,"Temporarly unavailable",Toast.LENGTH_LONG).show();
							break;
						default:
							break;
						}
						
					}
					
					@Override
					public void onProviderEnabled(String arg0) 
					{
						
						Log.e("GPS", "provider Enabled " + arg0);
					}
					
					@Override
					public void onProviderDisabled(String arg0) 
					{
						
						Log.e("GPS", "provider disabled " + arg0);
					}
					
					@Override
					public void onLocationChanged(Location location)
					{
						double lat=location.getLatitude();;
						double lon=location.getLongitude();
						String locationNamee=obj.findingName(lat,lon);
						locName=locationNamee;
						tv.setText(locationNamee);
					}
				};
				locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 1000, 1, listener);
				}
		});
		
		
		//	Closing Application
		ext_frmlocationupdate.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				onDestroy();
				System.exit(0);
				
			}
		});
		
		
		}
	catch (Exception e) 
	{
			Log.e("Error",e.toString());
	}
}

	@Override
	protected void onStart() 
	{
		super.onStart();
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
		checkForgps();
	}

	@Override
	protected void onPause() 
	{
		super.onPause();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		//locationManager.removeUpdates(listener);
	}
	
	
	//Checking weather GPS is available or not if not enabling GPS.
	
	public void checkForgps()
	{
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
           // Toast.makeText(getApplicationContext(), "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
			gpsonoff.setBackgroundColor(Color.GREEN);
			gpsonoff.setText("ON");
		}
		else
		{
			gpsonoff.setBackgroundColor(Color.RED);
			gpsonoff.setText("Click on this Button to Enable GPS ");
		}
		
			}
	
	
	public void list_ofexcelFiles()
	{
		Log.i("", "1");
		File folder = new File(filePath);
		File[] listOfFiles = folder.listFiles();
			
			
			Log.i("", "2");
			for (int i = 0; i < listOfFiles.length; i++) 
		    {
		      if (listOfFiles[i].isFile()) 
		      {
		    	  Log.i("", "3");
		    	  filename=listOfFiles[i].getName();
		    	  String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
		    	  if(extension.equals("xls"))
		    	  {
		    		  Log.i("", "4");
		    		  fileNames.add(filename);
		    		  xlFilecount++;
		    		  System.out.println("File " + filename);
		    		  Log.i("", "5");
		    	  }
		    	 
		    	  
		      } 
		      else if (listOfFiles[i].isDirectory()) 
		      {
		    	  //if we need folder we can use this block
		      }
		      
		  }
			//If no Item found
			 if(xlFilecount==0)
	    	  {
				 fileNames.add("NO EXCEL FILE AVAILABLE");
	    	  }
			 
			Log.i("", "6");
	}
	
	ArrayList<String > ary=new ArrayList<String>();
	//Reading excel data from excel sheet
	public void reading_excelData()
	{
		objofColName=new database_Class(MainActivity.this,"pa",null,1);
		int in=1;
		ary.clear();
		try
		{
			FileInputStream file=new FileInputStream(new File(filePath+selecedFile));
			
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			
			HSSFSheet sheet=workbook.getSheetAt(0);
			
			Iterator<Row> rowIterator = sheet.iterator();
			ContentValues con=new ContentValues();
			//ROW itration
			 while(rowIterator.hasNext()) 
			 {
				 	Row row = rowIterator.next();
			     
			       Iterator<Cell> cellIter = row.cellIterator();
				 	int item=0;
				 	
				 	int j=0;
				 	//coulmn Itration
				 	while(cellIter.hasNext())
				 	{
				 		HSSFCell myCell=(HSSFCell)cellIter.next();	
				 	if(in==1)
				 	{
				 		String val=myCell.toString();
				 		ary.add(val);
				 		Log.i("column "+in,""+myCell.toString());
				 		colno++;
				 	}
				 	else
				 	{
				 		
				 		j=1;
				 		con.put(ary.get(item),myCell.toString());
				 		Log.i(ary.get(item),myCell.toString());
				 		Log.i("ITEM"+item, ""+item);
				 		item++;
				 	}
				 	}
				 	if(j==1)
				 	{
				 		Log.i("got in","got in");
				 		objofColName.insert(con);
				 		Log.i("Succ", "Succ");
				 	}
				 	
				 	rowN++;
				 	if(in==1)
				 	{
				 		objofColName.tableCreate(ary);
				 	}
				 	Log.i("ROW NO"+in,"ROW NO"+in);
				 	arylistSize=ary.size();
				 	in++;
			   }
			 
			 	Log.i("CALL","CALL");
			 			 
		}
		catch(FileNotFoundException ex)
		{
			Log.e("Error TAG", ex.toString());
		}
		catch (IOException ex) 
		{
			Log.e("Error TAG", ex.toString());
		}
		
	}
	
	public void sendMessager()
	{
		
		int len=sqlContacts.size();
		Log.i(""+len,""+len);
		
		for(int i=0;i<len;i++)
		{
			
			long dtMili = System.currentTimeMillis();
			Date d = new Date(dtMili);
			
			time= java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
			
			Log.i("sar",""+sqlContacts.get(i));
			
			SmsManager sms1=SmsManager.getDefault();
			
			Log.i("TIME OF UPDATE","updates"+time);
			//locName="LOATION NAME";
			sms1.sendTextMessage(""+sqlContacts.get(i), null, "We are @"+locName+"@ The Time of"+time, null, null);
			
		}
		
		
		
	}
	public void sqlreader()
	{
		database_Class db=new database_Class(MainActivity.this, "pa",null,1);
		sqlContacts=db.read();
		}
	
}

package com.example.locationact;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class delay extends Activity{
	
	
	CheckBox traffic,accident,breakdown;
	Button all,owner;
	
	String timer,time,locationName;
	
	String delayed_Reason;
	
	ArrayList<String> ary,aryowner;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delayedinformation);
		
		traffic=(CheckBox)findViewById(R.id.checkBox1);
		accident=(CheckBox)findViewById(R.id.checkBox2);
		breakdown=(CheckBox)findViewById(R.id.checkBox3);
		
		long dtMili = System.currentTimeMillis();
		Date d = new Date(dtMili);
		time= java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		
		owner=(Button)findViewById(R.id.button1);
		all=(Button)findViewById(R.id.button2);
		
	Bundle bundle=getIntent().getExtras();
		
		locationName=bundle.getString("location");
		
		
		
//sent msg to all
		
		all.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0)
			{
				if(traffic.isChecked())
				{
					delayed_Reason="Bad Traffic";
				}
				else if(accident.isChecked())
				{
					delayed_Reason="Small accident";
				}
				else if(breakdown.isChecked())
				{
					delayed_Reason="Braek down";
				}
				else
				{
					Toast.makeText(delay.this, "Click your option", 10000).show();
					
				}
				sqlreader();
				sendMessager();
			}

			
		});
		
		
		
		owner.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0)
			{
				if(traffic.isChecked())
				{
					delayed_Reason="Bad Traffic";
				}
				else if(accident.isChecked())
				{
					delayed_Reason="Small acccident";
				}
				else if(breakdown.isChecked())
				{
					delayed_Reason="Braek down";
				}
				else
				{
					Toast.makeText(delay.this, "Click your option", 10000).show();
					
				}
			
				sqlreader();
				sendowner();
				
			}

		
		});
		
		
			
}
	
	public void sqlreader()
	{
		database_Class db=new database_Class(delay.this, "pa",null,1);
		ary=db.read();
		aryowner=db.readownerandoffice();
	}
	
	
	public void sendMessager()
	{
		
		int len=ary.size();
		Log.i(""+len,""+len);
		
		for(int i=0;i<len;i++)
		{
			
			long dtMili = System.currentTimeMillis();
			Date d = new Date(dtMili);
			time= java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
			Log.i("sar",""+ary.get(i));
			SmsManager sms1=SmsManager.getDefault();
			Log.i("TIME OF UPDATE","updates"+time);
			sms1.sendTextMessage(""+ary.get(i), null, "We are @"+locationName+"@ The Time of"+time+" Bus Delay due to: "+delayed_Reason, null, null);
			
		}
	}
	
	public void sendowner()
	{
		int len=aryowner.size();
		Log.i(""+len,""+len);
		
		for(int i=0;i<len;i++)
		{
			
			long dtMili = System.currentTimeMillis();
			Date d = new Date(dtMili);
			time= java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
			Log.i("OWNER OFF",""+aryowner.get(i));
			SmsManager sms1=SmsManager.getDefault();
			Log.i("TIME OF UPDATE","updates"+time);
			sms1.sendTextMessage(""+aryowner.get(i), null, "We are @"+locationName+"@ The Time of"+time+" Bus Delay due to: "+delayed_Reason, null, null);
			
		}
		
		
	}
}


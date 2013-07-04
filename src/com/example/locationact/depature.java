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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class depature extends Activity
{

	String[] hour={"1","2","3","4","5","6","7","8","9","10","11","12"};
	String[] min={"5","10","15","20","25","30","35","40","45","50","55","60"};
	
	ArrayList<String> ary;
	
	Button but;
	EditText editText;
	RadioGroup rdg;
	CheckBox ch1,ch2,ch3;
	Spinner sp1,sp2;
	
	String dep_hour,dep_min,dep_ses,venue,message;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.depatureinformation);
		
		but=(Button)findViewById(R.id.button4);
		sp1=(Spinner)findViewById(R.id.spinner1);
		sp2=(Spinner)findViewById(R.id.spinner2);
		
		editText=(EditText)findViewById(R.id.editText1);
		rdg=(RadioGroup)findViewById(R.id.radioGroup1);
		
		ch1=(CheckBox)findViewById(R.id.checkBox1);
		ch2=(CheckBox)findViewById(R.id.checkBox2);
		ch3=(CheckBox)findViewById(R.id.checkBox3);
		
		ArrayAdapter<String> houradapter=new ArrayAdapter<String>(depature.this,android.R.layout.simple_list_item_1,hour);
		sp1.setAdapter(houradapter);
		ArrayAdapter<String> minadapter=new ArrayAdapter<String>(depature.this,android.R.layout.simple_list_item_1,min);
		sp2.setAdapter(minadapter);
		
		
		ch3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			
				if(ch3.isChecked()==true)
				{
					editTextcontrol();
				}
				else
				{
					editText.setVisibility(editText.INVISIBLE);
				}
			}
		});
		sp1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				dep_hour=hour[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) 
			{
				
				
			}
		});
		
		sp2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				dep_min=min[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) 
			{
				
				
			}
		});
		
		
		
			
		
		but.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int id=rdg.getCheckedRadioButtonId();
				
				if(id==R.id.radio0)
				{
					dep_ses="AM";
				}
				if(id==R.id.radio1)
				{
					dep_ses="PM";
				}
				if(ch1.isChecked()==true)
				{
					venue="OUR OFFICE";
				}
				if(ch2.isChecked()==true)
				{
					venue="Bus Stand";
				}
				if(ch3.isChecked()==true)
				{
					venue=editText.getText().toString();
				}
				
				message="Bus Depature Location :"+venue+" @ The Time of"+dep_hour+":"+dep_min+":"+dep_ses+" Please Be here Before one hour of Depature Time";
			
				Log.i("TIME",""+message);
				sqlreader();
				sendMessager();
				
			}
		});
		
}
	
	public void editTextcontrol()
	{
		editText.setVisibility(editText.VISIBLE);
	}
	public void sqlreader()
	{
		database_Class db=new database_Class(depature.this, "pa",null,1);
		ary=db.read();
	}
	
	public void sendMessager()
	{
		
		int len=ary.size();
		Log.i(""+len,""+len);
		
		for(int i=0;i<len;i++)
		{
			Log.i("sar",""+ary.get(i));
			SmsManager sms1=SmsManager.getDefault();
			sms1.sendTextMessage(""+ary.get(i), null, ""+message, null, null);
			
		}
		
		
		
	}
	
}

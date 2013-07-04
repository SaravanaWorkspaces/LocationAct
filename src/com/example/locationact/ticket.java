package com.example.locationact;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class ticket extends Activity{
	
	CheckBox c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18;
	Button done;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticketcheck);
    
        done=(Button)findViewById(R.id.button1);
        
        c1=(CheckBox)findViewById(R.id.checkBox1);
        c2=(CheckBox)findViewById(R.id.checkBox2);
        c3=(CheckBox)findViewById(R.id.checkBox3);
        c4=(CheckBox)findViewById(R.id.checkBox4);
        c5=(CheckBox)findViewById(R.id.checkBox5);
        c6=(CheckBox)findViewById(R.id.checkBox6);
        c7=(CheckBox)findViewById(R.id.checkBox7);
        c8=(CheckBox)findViewById(R.id.checkBox8);
        c9=(CheckBox)findViewById(R.id.checkBox9);
        c10=(CheckBox)findViewById(R.id.checkBox10);
        c11=(CheckBox)findViewById(R.id.checkBox11);
        c12=(CheckBox)findViewById(R.id.checkBox12);
        c13=(CheckBox)findViewById(R.id.checkBox13);
        c14=(CheckBox)findViewById(R.id.checkBox14);
        c15=(CheckBox)findViewById(R.id.checkBox15);
        c16=(CheckBox)findViewById(R.id.checkBox16);
        c17=(CheckBox)findViewById(R.id.checkBox17);
        c18=(CheckBox)findViewById(R.id.checkBox18);
        
        
        
        
        done.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(c1.isChecked())
				{
					Toast.makeText(ticket.this, "1 occupied", 10000).show();
				}
				
				if(c2.isChecked())
				{
									
					Toast.makeText(ticket.this, "2 occupied", 10000).show();
										
			
				}
				
				if(c3.isChecked())
				{
									
					Toast.makeText(ticket.this, "3 occupied", 10000).show();
										
			
				}
				
				if(c4.isChecked())
				{
									
					Toast.makeText(ticket.this, "4 occupied", 10000).show();
				}
				
				if(c5.isChecked())
				{
									
					Toast.makeText(ticket.this, "5 occupied", 10000).show();
				}
				
				if(c6.isChecked())
				{
									
					Toast.makeText(ticket.this, "6 occupied", 10000).show();
				}
				
				if(c7.isChecked())
				{
									
					Toast.makeText(ticket.this, "7 occupied", 10000).show();
				}
				
				if(c8.isChecked())
				{
					Toast.makeText(ticket.this, "8 occupied", 10000).show();
				}
				
				if(c9.isChecked())
				{
									
					Toast.makeText(ticket.this, "9 occupied", 10000).show();
				}
				
				if(c2.isChecked())
				{
									
					Toast.makeText(ticket.this, "2 occupied", 10000).show();
				}
				
				if(c10.isChecked())
				{
									
					Toast.makeText(ticket.this, "10 occupied", 10000).show();
				}
				
				if(c11.isChecked())
				{
									
					Toast.makeText(ticket.this, "11 occupied", 10000).show();
				}
				if(c12.isChecked())
				{
									
					Toast.makeText(ticket.this, "12 occupied", 10000).show();
				}
				
				if(c2.isChecked())
				{
									
					Toast.makeText(ticket.this, "2 occupied", 10000).show();
				}
				
				if(c13.isChecked())
				{
					Toast.makeText(ticket.this, "13 occupied", 10000).show();
				}
				
				if(c14.isChecked())
				{
									
					Toast.makeText(ticket.this, "14 occupied", 10000).show();
				}
				
				if(c15.isChecked())
				{
									
					Toast.makeText(ticket.this, "15 occupied", 10000).show();
				}
				
				if(c16.isChecked())
				{
									
					Toast.makeText(ticket.this, "16 occupied", 10000).show();
				}
				
				if(c17.isChecked())
				{
									
					Toast.makeText(ticket.this, "17 occupied", 10000).show();
				}
				
				if(c18.isChecked())
				{
									
					Toast.makeText(ticket.this, "18 occupied", 10000).show();
				}
				
			}
		});
        
        
        
        
        
        
        
        

}
}


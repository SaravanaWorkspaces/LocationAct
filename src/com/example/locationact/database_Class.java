package com.example.locationact;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)

@SuppressLint("NewApi")

public class database_Class extends SQLiteOpenHelper
{
	String[] cols=null;
	
	Context mcontext;
	
	SQLiteDatabase dbr;
	
	int phone_numberat=0,ownernoat=0;
	
	static String table_Name="passenger_det";
	
	String createdTable_query;
	
	public database_Class(Context context, String tabName, CursorFactory factory,
			int version) 
	{
		
		super(context, tabName, null, 1);
		Log.i("Constuctor","Entered");
		mcontext=context;
		//onCreate(dbr);
		Log.i("Constructor","Finished");
	}

	@Override
	public void onCreate(SQLiteDatabase db1) 
	{
		Log.i("Oncreate","Entred");
		Log.i("In onCreate tab query",""+createdTable_query);
		try
		{
			//Over writes Previous Table
			db1.execSQL("DROP TABLE IF EXISTS "+table_Name);
			//Creates Table
			db1.execSQL(createdTable_query);
			
			Log.i("Table Created","TAB CREATED");
		}
		catch(Exception ex)
		{
			onUpgrade(db1, 1, 1);
			Log.i("Exception IN", ""+ex);
		}
		
		Log.i("Oncreate","Finally Table Created" );
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		Log.i("onUpgrade"," Entered");
		db.execSQL("DROP TABLE IF EXISTS "+table_Name);
		Log.i("onUpgrade"," TAB DROPED");
		onCreate(db);
	}
	
	public void tableCreate(ArrayList<String> ary)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		 Log.i("U M TAB Created","Entered");
		
		int size=ary.size();
		
		String query;
		
		StringBuilder strBuiled=new StringBuilder();
		
		for(int i=0;i<size;i++)
		{
			query=ary.get(i)+" text";
			if(ary.get(i).toString().equalsIgnoreCase("phone"))
			{
				phone_numberat=i;
				Log.i("I matched"+i,""+i);
			}
			if(ary.get(i).toString().equalsIgnoreCase("o"))	
			{
				ownernoat=i;
				Log.i("Onwer At","");
			}
			if(i==0)
			{
				strBuiled.append(query);
			}
			else
			{
				strBuiled.append(","+query);
			}
			
			Log.i("Query Building",strBuiled.toString());
		}
		
		createdTable_query="create table "+table_Name+"("+strBuiled.toString()+")";
		
		Log.i("Table query in creation",""+createdTable_query);
		SQLiteDatabase dbr=this.getWritableDatabase();
		onCreate(dbr);
	}
	public void insert(ContentValues con)
	{
		Log.i("Inserted", "Entered");
		SQLiteDatabase db=this.getWritableDatabase();
		
		db.insert(table_Name, null, con);
		db.close();
		Log.i("Inserted", "Inserted");
		
	}
public ArrayList<String> read()
{
	
	Log.i("READ", "READ");
ArrayList< String> ary=new ArrayList<String>();
SQLiteDatabase sd=this.getReadableDatabase();

String s="select *from "+table_Name;

Cursor c=sd.rawQuery(s, null);

Log.i("Element",""+c.getCount());

c.moveToFirst();

for(int i=0;i<c.getCount();i++)
{

	String l=c.getString(phone_numberat);
	ary.add(l);
	Log.i("MAIN","MAINA"+l);
	
	c.moveToNext();
}
return ary;

}
 public ArrayList<String> readownerandoffice()
 {
	ArrayList<String> ary=new ArrayList<String>();
	SQLiteDatabase sd=this.getReadableDatabase();
	String query="select owner from "+table_Name;
	
	Cursor c=sd.rawQuery(query,null);
	Log.i("Elementss",""+c.getCount());

	c.moveToFirst();

	for(int i=0;i<c.getCount();i++)
	{

		String l=c.getString(ownernoat);
		ary.add(l);
		c.moveToNext();
	}
	 return ary;
	 }

}

package com.alex.googlemaps;

public class Locations_viewer 
{

	int id;
	String Latitude;
	String Longitude;
	String Time;
	
	//empty constructor
	public Locations_viewer()
	{
		
	}
	
	public Locations_viewer(int _id,String lat,String longi,String time)
	{
		this.id=_id;
		this.Latitude=lat;
		this.Longitude=longi;
		this.Time=time;
	}
	
	public Locations_viewer(String lat,String longi,String time)
	{
		this.Latitude=lat;
		this.Longitude=longi;
		this.Time=time;
	}
	
	public int getID(){
		return this.id;
	}

	// setting id
	public void setID(int id){
		this.id = id;
	}

	// getting name
	public String getLat(){
		return this.Latitude;
	}

	// setting name
	public void setLAt(String lat)
	{
		this.Latitude = lat;
	}

	// getting phone number
	public String getLong(){
		return this.Longitude;
	}

	// setting phone number
	public void setLongi(String Longi){
		this.Longitude = Longi;
	}
	// getting phone number
		public String getTime(){
			return this.Time;
		}

		// setting phone number
		public void setTime(String time){
			this.Time = time;
		}
}

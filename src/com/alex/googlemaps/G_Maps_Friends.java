package com.alex.googlemaps;

import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.annotation.TargetApi;
import android.app.Activity;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class G_Maps_Friends extends Activity {

	private GoogleMap mMap;
	Double latitude, longitude;
	String Time;
	LatLng CIU;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		String lat = getIntent().getStringExtra("lat");
		String lon = getIntent().getStringExtra("lon");
		Time = getIntent().getStringExtra("time");
		latitude = Double.parseDouble(lat);
		longitude = Double.parseDouble(lon);
		
		
		
		MapFragment mpFragment= ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
        mMap=mpFragment.getMap();
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        CIU = new LatLng(latitude, longitude);
		MapFragment mpFragment2= ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
        mMap=mpFragment2.getMap();
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        
        mMap.addMarker(new MarkerOptions()
        .position(CIU).title("Location").snippet("Latitude="+lat+"  Longitude="+lon));
		
		

       
       mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CIU, 10));
       mMap.animateCamera(CameraUpdateFactory.zoomTo(10),2000,null);
	}



}

package com.alex.googlemaps;

import java.util.List;

import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Menu;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class G_Maps extends Activity {

	private GoogleMap mMap;
	Double latitude, longitude;
	LatLng CIU;
	List<Locations_viewer> location_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		MapFragment mpFragment = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.map));
		mMap = mpFragment.getMap();
		mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

		final DatabaseHandlerActivity db = new DatabaseHandlerActivity(
				getApplicationContext());
		location_list = db.getAllLocations();
		db.close();

		if (location_list.isEmpty()) {

			CIU = new LatLng(10.523067500000000000, 76.222210599999920000);
			MapFragment mpFragment2 = ((MapFragment) getFragmentManager()
					.findFragmentById(R.id.map));
			mMap = mpFragment2.getMap();
			mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			mMap.addMarker(new MarkerOptions()
					.position(CIU)
					.title("Location")
					.snippet(
							"Latitude=10.52306750, Longitude=76.22221059999992"));

		} else {

			for (Locations_viewer locations_viewer : location_list) {
				latitude = Double.parseDouble(locations_viewer.getLat());
				longitude = Double.parseDouble(locations_viewer.getLong());
				CIU = new LatLng(latitude, longitude);
				mMap.addMarker(new MarkerOptions()
						.position(CIU)
						.title("Location")
						.snippet(
								"Latitude=" + locations_viewer.getLat()
										+ ",  Longitude="
										+ locations_viewer.getLong()));
			}
		}

		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CIU, 10));
		mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

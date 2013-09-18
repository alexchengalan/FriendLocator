package com.alex.googlemaps;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class Friends_page extends Activity {

	ProgressDialog myPd_ring;
	ListView friends_list;
	List<ParseObject> loc_friend;
	ArrayList<String> friendsName = new ArrayList<String>();
	String lat, lon;
	String Time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends_page);
		friends_list = (ListView) findViewById(R.id.friends_list);

		myPd_ring = new ProgressDialog(Friends_page.this);
		myPd_ring.setMessage("Please wait...");
		myPd_ring.setCancelable(true);
		myPd_ring.show();

		String our_Id = ParseUser.getCurrentUser().getUsername();
		ParseQuery friends = new ParseQuery("Friends");
		friends.whereEqualTo("UserId", our_Id);
		friends.findInBackground(new FindCallback() {

			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				friendsName.clear();
				// TODO Auto-generated method stub
				for (ParseObject obj : arg0) {
					friendsName.add(obj.getString("FriendId"));
				}

				if (friendsName.isEmpty()) {
					TextView text = (TextView) findViewById(R.id.nofriendtext);
					friends_list.setVisibility(View.INVISIBLE);
					text.setVisibility(View.VISIBLE);
					text.setText("No friends!");
					text.setTextSize(17f);
					myPd_ring.cancel();
				} else {
					friends_list.setAdapter(new Custom_ListView(
							Friends_page.this, friendsName));
					myPd_ring.cancel();
				}

			}
		});

		friends_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub

				final String friend = friends_list.getItemAtPosition(position)
						.toString();

				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						ParseQuery location = ParseUser.getQuery();
						location.whereEqualTo("username", friend);
						try {
							loc_friend = location.find();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						for (ParseObject obj : loc_friend) {
							lat = obj.getString("Latitude");
							lon = obj.getString("Longitude");
							Time = obj.getString("Time");

						}

						Intent i = new Intent(Friends_page.this,
								G_Maps_Friends.class);
						i.putExtra("lat", lat);
						i.putExtra("lon", lon);
						i.putExtra("time", Time);
						startActivity(i);
					}
				}).start();

			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
		new AlertDialog.Builder(Friends_page.this)
				.setTitle("Are you sure,")
				.setMessage("Want to exit?")
				.setNegativeButton("No", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				})
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog_main,
									int which) {
								// TODO Auto-generated method stub
								dialog_main.dismiss();
								finish();
							}
						}).show();
	}
}

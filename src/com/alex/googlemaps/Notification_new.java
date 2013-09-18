package com.alex.googlemaps;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import de.keyboardsurfer.android.widget.crouton.Crouton;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class Notification_new extends Activity {

	ProgressDialog myPd_ring;
	ListView list_notification;
	ArrayList<String> notificatios = new ArrayList<String>();
	ArrayList<ParseObject> obj_notification = new ArrayList<ParseObject>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification);
		list_notification = (ListView) findViewById(R.id.list_notification);
		myPd_ring = new ProgressDialog(Notification_new.this);
		myPd_ring.setMessage("Please wait...");
		myPd_ring.setCancelable(true);
		myPd_ring.show();
		ParseQuery myquery = new ParseQuery("Request");
		myquery.whereEqualTo("UserId", ParseUser.getCurrentUser().getUsername());
		myquery.findInBackground(new FindCallback() {

			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				// TODO Auto-generated method stub
				for (ParseObject obj : arg0) {
					notificatios.add(obj.getString("RequestingId"));
					obj_notification.add(obj);
				}

				if (notificatios.isEmpty()) {
					TextView text = (TextView) findViewById(R.id.no_noti_text);
					list_notification.setVisibility(View.INVISIBLE);
					text.setVisibility(View.VISIBLE);
					text.setText("No notifications!");
					text.setTextSize(17f);
					myPd_ring.dismiss();
				} else {
					list_notification.setAdapter(new Custom_ListView(
							Notification_new.this, notificatios));

					myPd_ring.dismiss();
				}

			}
		});

		list_notification.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int position, long arg3) {
				// TODO Auto-generated method stub

				AlertDialog.Builder builder = new AlertDialog.Builder(
						Notification_new.this);
				builder.setMessage(
						"Would you like to add " + notificatios.get(position)
								+ " as your friend?")
						.setCancelable(false)

						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										// String our_Name =
										// ParseUser.getCurrentUser().getUsername();
										// ParseObject Request = new
										// ParseObject("Request");

										// Request.put("UserId",Adapter.getItem(position));
										// Request.put("RequestingId",
										// our_Name);
										// Request.saveInBackground();
										// Toast.makeText(getApplicationContext(),
										// "Request sent",
										// Toast.LENGTH_SHORT).show();

										String our_Name = ParseUser
												.getCurrentUser().getUsername();
										ParseObject Request = new ParseObject(
												"Friends");
										Request.put("UserId", our_Name);
										Request.put("FriendId",
												notificatios.get(position));
										Request.saveEventually();
										ParseObject Request_relation = new ParseObject(
												"Friends");
										Request_relation.put("UserId",
												notificatios.get(position));
										Request_relation.put("FriendId",
												our_Name);
										Request_relation.saveInBackground();

										Crouton.makeText(
												Notification_new.this,
												"You are now friends with "
														+ notificatios
																.get(position),
												de.keyboardsurfer.android.widget.crouton.Style.CONFIRM)
												.show();

									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}

								});
				AlertDialog alert = builder.create();
				alert.show();

				obj_notification.get(position).deleteEventually();

			}
		});

	}

}

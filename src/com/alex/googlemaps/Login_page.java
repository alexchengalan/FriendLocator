package com.alex.googlemaps;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import de.keyboardsurfer.android.widget.crouton.Crouton;

import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

@SuppressLint("DefaultLocale")
public class Login_page extends Activity {

	EditText userField, passField;
	String username, password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			// do stuff with the user
			Intent i = new Intent(getApplicationContext(), MainPage.class);
			startActivity(i);
		} else {
			setContentView(R.layout.login_page);
			userField = (EditText) findViewById(R.id.username);
			passField = (EditText) findViewById(R.id.password_field);
		}

	}

	public void login(View v) {

		username = userField.getText().toString().toLowerCase().trim();
		password = passField.getText().toString().toLowerCase().trim();

		final ProgressDialog mDialog = new ProgressDialog(this);
		mDialog.setMessage("Login Progress..");

		mDialog.show();

		ParseUser.logInInBackground(username, password, new LogInCallback() {

			@Override
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					// Hooray! The user is logged in.

					mDialog.cancel();

					Crouton.makeText(Login_page.this, "Successful login!",
							de.keyboardsurfer.android.widget.crouton.Style.CONFIRM)
							.show();

					// Toast toast = Toast.makeText(
					// getApplicationContext(),
					// "Successfull Login! ",
					// Toast.LENGTH_LONG);
					// toast.show();

					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Intent signupScreen = new Intent(
									getApplicationContext(), MainPage.class);
							startActivity(signupScreen);
						}
					}, 1000);

				} else {

					mDialog.cancel();
					// Signup failed. Look at the ParseException
					// to see what happened.

					Crouton.makeText(Login_page.this, "Invalid credentials or no internet connectivity!",
							de.keyboardsurfer.android.widget.crouton.Style.ALERT)
							.show();

				}

			}

		});

	}

	public void signup(View v) {
		Intent i = new Intent(getApplicationContext(), SignUp.class);
		startActivity(i);
	}
}

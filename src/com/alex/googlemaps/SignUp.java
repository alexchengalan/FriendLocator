package com.alex.googlemaps;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import de.keyboardsurfer.android.widget.crouton.Crouton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

@SuppressLint("DefaultLocale")
public class SignUp extends Activity {
	EditText usernameField, passwordField, emailField;
	String username, password, email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_page);
		usernameField = (EditText) findViewById(R.id.username);
		passwordField = (EditText) findViewById(R.id.password_field);
		emailField = (EditText) findViewById(R.id.email);
	}

	@SuppressLint("SimpleDateFormat")
	public void sign_up(View v) {

		// TelephonyManager telemamanger = (TelephonyManager)
		// getSystemService(Context.TELEPHONY_SERVICE);
		// String simSerialNumber = telemamanger.getSimSerialNumber();
		// String uniqueNumber = telemamanger.getDeviceId();

		if (usernameField.getText().length() > 0) {
			if (passwordField.getText().length() > 0) {
				if (emailField.getText().length() > 0) {

					username = usernameField.getText().toString().toLowerCase().trim();
					password = passwordField.getText().toString().toLowerCase().trim();
					email = emailField.getText().toString().trim();

					ParseUser user = new ParseUser();
					user.setUsername(username);
					user.setPassword(password);
					user.setEmail(email);
					user.put("Latitude", "10.523067500000000000");
					user.put("Longitude", "76.222210599999920000");

					Calendar c = Calendar.getInstance();
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String formattedDate = df.format(c.getTime());

					user.put("Time", formattedDate);

					// user.put("SimSerialNumber", simSerialNumber);
					// user.put("UniqueNumber", uniqueNumber);

					final ProgressDialog mDialog = new ProgressDialog(
							SignUp.this);
					mDialog.setMessage("Signing Up..");

					mDialog.show();

					user.signUpInBackground(new SignUpCallback() {
						public void done(ParseException e) {

							mDialog.cancel();

							if (e == null) {

								Crouton.makeText(
										SignUp.this,
										"Successful signup!",
										de.keyboardsurfer.android.widget.crouton.Style.CONFIRM)
										.show();
								new Handler().postDelayed(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										Intent signupScreen = new Intent(
												getApplicationContext(),
												Login_page.class);
										startActivity(signupScreen);
										// Hooray! Let them use the app now.
									}
								}, 1000);

							} else {

								Crouton.makeText(
										SignUp.this,
										"Sign up didn't succeed, please try again later..",
										de.keyboardsurfer.android.widget.crouton.Style.ALERT)
										.show();

								// Sign up didn't succeed. Look at the
								// ParseException
								// to figure out what went wrong

							}
						}
					});
				} else {

					Crouton.makeText(
							SignUp.this,
							"Please enter an email! ",
							de.keyboardsurfer.android.widget.crouton.Style.ALERT)
							.show();
				}

			} else {

				Crouton.makeText(SignUp.this, "Please enter a password! ",
						de.keyboardsurfer.android.widget.crouton.Style.ALERT)
						.show();
			}

		} else {

			Crouton.makeText(SignUp.this, "Please enter a valid username! ",
					de.keyboardsurfer.android.widget.crouton.Style.ALERT)
					.show();
		}

	}
}

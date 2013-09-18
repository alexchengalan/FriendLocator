package com.alex.googlemaps;

import com.parse.Parse;
import android.app.Application;

public class ParseApplication extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Parse.initialize(this, "cOF2YCY3vHAJeG0Y2fkVDxW4TXXcqjpkZfXCKnU5",
				"We0vjt4xAh2MSGy3Xxofq0q3CpnqXpF8QNSDpCtr");
	}

}

package com.alex.googlemaps;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Custom_ListView extends ArrayAdapter<String> {

	private final ArrayList<String> names;
	LayoutInflater inflater;

	public Custom_ListView(Context context, ArrayList<String> objects) {
		super(context, R.layout.custom_list, objects);
		this.names = objects;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.custom_list, parent, false);
			holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.text.setTextColor(Color.BLACK);
		holder.text.setText(names.get(position));
		return convertView;
	}

	private static class ViewHolder {
		TextView text;
	}
}

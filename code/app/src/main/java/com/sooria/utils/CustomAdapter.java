package com.sooria.utils;

import java.util.ArrayList;

import com.sooria.aadhavan.MainActivity;
import com.sooria.aadhavan.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {

	Context context;
	int layoutResourceId;
	ArrayList<String> data = null;
	Typeface tf;

	public CustomAdapter(Context context, int layoutResourceId,
			ArrayList<String> data, String FONT) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
		tf = Typeface.createFromAsset(context.getAssets(), FONT);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.simplerow, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.rowTextView);
		if (MainActivity.Version >= 21)
			textView.setText(data.get(position).toString());
		else {
			textView.setTypeface(tf);
			textView.setText(UnicodeUtil.unicode2tsc(data.get(position)
					.toString()));
		}
		return rowView;
	}
}

package com.paulusworld.drawernavigationtabs.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import com.paulusworld.drawernavigationtabs.R;
import com.paulusworld.drawernavigationtabs.bean.Event;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomMyAppointmentsListAdapter extends BaseAdapter {

	List<Event> events;
	private LayoutInflater layoutInflater;
	private FragmentActivity callbackActivity;

	public CustomMyAppointmentsListAdapter(FragmentActivity callbackActivity,
			List<Event> events) {
		this.callbackActivity = callbackActivity;
		this.layoutInflater = (LayoutInflater) callbackActivity
				.getApplicationContext().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
		this.events = events;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return events.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			Event event = events.get(position);
			View rootView = layoutInflater.inflate(R.layout.event_item, parent,false);

			TextView eventName = (TextView) rootView
					.findViewById(R.id.event_name);
			TextView eventDescription = (TextView) rootView
					.findViewById(R.id.event_description);
			TextView eventPlace = (TextView) rootView
					.findViewById(R.id.event_place);
			TextView eventTime = (TextView) rootView
					.findViewById(R.id.event_time);

			eventName.setText(event.getEventName());
			eventDescription.setText(event.getDescription());
			eventPlace.setText(event.getPlace());
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
			eventTime.setText(dateFormat.format(event.getFromDate()));
			return rootView;
		} 
		else {
			return convertView;
		}
	}

}

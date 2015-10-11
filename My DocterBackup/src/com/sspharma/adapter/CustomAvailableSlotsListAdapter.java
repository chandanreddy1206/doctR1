package com.paulusworld.drawernavigationtabs.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.paulusworld.drawernavigationtabs.R;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAvailableSlotsListAdapter extends BaseAdapter {
	private List<Date> availableSlotsDates;
	private FragmentActivity callbackActivity;
	private LayoutInflater layoutInflater;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy  hh:mm a");
	public CustomAvailableSlotsListAdapter(FragmentActivity callbackActivity, List<Date> availableSlotsDates) {
		this.availableSlotsDates = availableSlotsDates;
		this.callbackActivity = callbackActivity;
		this.layoutInflater = (LayoutInflater) callbackActivity.getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		return availableSlotsDates.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public class Holder
	{
		TextView textView;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
		{
			Holder holder = new Holder();
			View view = layoutInflater.inflate(R.layout.doctor_appointment_available_slot_item,null);
			holder.textView = (TextView) view.findViewById(R.id.doctor_appointment_available_slot_item_textview);
			
			holder.textView.setText(dateFormat.format(availableSlotsDates.get(position)));
			return view;
		}
		else
		{
			return convertView;
		}
	}

}

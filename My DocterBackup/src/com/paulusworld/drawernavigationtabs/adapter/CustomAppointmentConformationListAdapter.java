package com.paulusworld.drawernavigationtabs.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.paulusworld.drawernavigationtabs.R;

import android.R.integer;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class CustomAppointmentConformationListAdapter extends BaseAdapter {
	private List<Date> appointmentRequests;
	private FragmentActivity callbackActivity;
	private LayoutInflater layoutInflater;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy  hh:mm a");

	public CustomAppointmentConformationListAdapter(FragmentActivity callbackActivity, List<Date> appointmentRequests) {
		this.appointmentRequests = appointmentRequests;
		this.callbackActivity = callbackActivity;
		this.layoutInflater = (LayoutInflater) callbackActivity.getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		return appointmentRequests.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public class Holder {
		TextView appointmentDetails;
		TextView appointmentsConformed;
		Button approveAppointment;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			Holder holder = new Holder();
			View view = layoutInflater.inflate(R.layout.doctor_approve_appointments_item, null);
			holder.appointmentDetails = (TextView) view.findViewById(R.id.doctor_approve_appointment_details);
			holder.appointmentsConformed = (TextView) view.findViewById(R.id.doctor_approve_appointment_confirmed);
			holder.approveAppointment = (Button) view.findViewById(R.id.doctor_approve_appointment_approve_button);
			
			Date date  = appointmentRequests.get(position);
			holder.appointmentDetails.setText(dateFormat.format(date));
			holder.approveAppointment.setOnClickListener(new ApproveButtonClickListener(date));
			return view;
		} else {
			return convertView;
		}
	}
	private class ApproveButtonClickListener implements OnClickListener
	{
		Date date;
		public ApproveButtonClickListener(Date date) {
			this.date = date;
		}
		@Override
		public void onClick(View v) {
		
			//TODO update server ......!!!
			
			Button approveButton = (Button) v;
			approveButton.setText("Conformed");
			approveButton.setEnabled(false);
		}
	}
}

package com.paulusworld.drawernavigationtabs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.paulusworld.drawernavigationtabs.adapter.CustomAppointmentConformationListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class DoctorAppointmentConformationFragment extends Fragment {
	public static final String TAG = DoctorAppointmentConformationFragment.class.getSimpleName();
	private FragmentActivity callbackActivity;
	private ListView listview;
	private List<Date> appointments;

	public static DoctorAppointmentConformationFragment newInstance() {
		return new DoctorAppointmentConformationFragment();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		callbackActivity = (FragmentActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.doctor_approve_appointments, null);
		listview = (ListView) view.findViewById(R.id.doctor_approve_appointments_listview);
		appointments = new ArrayList<Date>();
		Calendar calendar = Calendar.getInstance();
		for(int i=0;i<10;i++)
		{
			appointments.add(calendar.getTime());
			calendar.add(Calendar.MINUTE,30);
		}
		
		listview.setAdapter(new CustomAppointmentConformationListAdapter(callbackActivity, appointments));
		return view;
	}
}

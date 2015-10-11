package com.sspharma;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sspharma.adapter.CustomAvailableSlotsListAdapter;
import com.sspharma.bean.Doctor;
import com.sspharma.widget.CustomTimePickerDialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.TimePicker;

public class BookAppointmentCalenderFragment extends Fragment implements OnClickListener,OnTimeSetListener, OnDateSetListener, OnItemClickListener
{

	public static final String TAG = BookAppointmentCalenderFragment.class.getSimpleName();
	private FragmentActivity callbackActivity;
	private Button dateButton;
	private Button timeButton;
	private GridView availableSlotsGridView;
	//private ListView availableSlotsListView;
	private Calendar selectedDateTime=Calendar.getInstance();
	private CustomAvailableSlotsListAdapter availableSlotsListAdapter;
	private List<Date> availableSlotsDates = new ArrayList<Date>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_book_appointment, null);

		
		dateButton = (Button) view.findViewById(R.id.fragment_book_appointment_date_button);
		timeButton = (Button) view.findViewById(R.id.fragment_book_appointment_time_button);
//		availableSlotsListView = (ListView) view.findViewById(R.id.fragment_book_appointment_available_slots);
		availableSlotsGridView = (GridView) view.findViewById(R.id.fragment_book_appointment_available_slots);
		availableSlotsListAdapter = new CustomAvailableSlotsListAdapter(callbackActivity,availableSlotsDates);
		availableSlotsGridView.setAdapter(availableSlotsListAdapter);
		
		//availableSlotsListView.setAdapter(availableSlotsListAdapter);
		Calendar calendar = Calendar.getInstance();
		for(int i=0;i<10;i++)
		{
			availableSlotsDates.add(calendar.getTime());
			calendar.add(Calendar.MINUTE,30);
		}
		availableSlotsListAdapter.notifyDataSetChanged();
		dateButton.setOnClickListener(this);
		timeButton.setOnClickListener(this);
		//availableSlotsListView.setOnItemClickListener(this);
		availableSlotsGridView.setOnItemClickListener(this);
		return view;
	}
	public static BookAppointmentCalenderFragment newInstance()
	{
		return new BookAppointmentCalenderFragment();
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		callbackActivity=(FragmentActivity)activity;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == timeButton.getId())
		{
			CustomTimePickerDialog customTimePickerDialog = new CustomTimePickerDialog(callbackActivity, this, 12, 30, false);
			customTimePickerDialog.show();
		}
		else if(v.getId() == dateButton.getId())
		{
			Calendar calendar = Calendar.getInstance();
			DatePickerDialog datePickerDialog = new DatePickerDialog(callbackActivity, this, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
			DatePicker datePicker = datePickerDialog.getDatePicker();
			datePicker.setCalendarViewShown(false);
			datePicker.setSpinnersShown(true);
			
			calendar.add(Calendar.DATE, 1);
			datePicker.setMinDate(calendar.getTimeInMillis());
			calendar.add(Calendar.DATE, 5);
			datePicker.setMaxDate(calendar.getTimeInMillis());
			datePickerDialog.show();
		}
	}
	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//		System.out.println("adkfjlad "+hourOfDay+" "+minute);
		selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
		selectedDateTime.set(Calendar.MINUTE, minute);
		
		updateAvaialbleSlots();
	}
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//		System.out.println("adkfjlad "+year+" "+monthOfYear+" "+dayOfMonth);
		selectedDateTime.set(Calendar.DAY_OF_MONTH, 1);
		selectedDateTime.set(Calendar.MONTH, Calendar.JANUARY);
		selectedDateTime.set(Calendar.YEAR, year);
		selectedDateTime.set(Calendar.MONTH, monthOfYear);
		selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		
		updateAvaialbleSlots();
	}
	public void updateAvaialbleSlots()
	{
		availableSlotsDates.clear();
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		for(int i=0;i<10;i++)
		{
			availableSlotsDates.add(calendar.getTime());
			calendar.add(Calendar.MINUTE,30);
		}
		//TODO get Data from server
		//List<Date> newDates = new ArrayList<Date>();
		
		//availableSlotsDates.addAll(newDates);
		availableSlotsListAdapter.notifyDataSetChanged();
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		System.out.println("clicked "+position);
		sendBookAppointmentRequest(null, availableSlotsDates.get(position));
	}
	public void sendBookAppointmentRequest(Doctor doctor,Date slot)
	{
		//TODO send req to server
	}
}

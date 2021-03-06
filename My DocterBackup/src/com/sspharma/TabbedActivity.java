package com.sspharma;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.sspharma.adapter.CustomMyAppointmentsListAdapter;
import com.sspharma.bean.Event;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class TabbedActivity extends Fragment {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	public static final String TAG = TabbedActivity.class.getSimpleName();

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	public static TabbedActivity newInstance() {
		return new TabbedActivity();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_item_one, container, false);
		mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

		mViewPager = (ViewPager) v.findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		if (getArguments() != null) {

			int targetPage = getArguments().getInt("target");
			if (targetPage != 0)
				mViewPager.setCurrentItem(targetPage - 1,true);
		}
		return v;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
		
			if(position == 0)
			{
				return new MyAppointmentsFragment();
			}
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_tabbed_dummy, container, false);
			TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
			return rootView;
		}
	}

	public static class MyAppointmentsFragment extends Fragment
	{
		private FragmentActivity callbackActivity;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_myappointments, container, false);
			
			
			ListView listView = (ListView) rootView.findViewById(R.id.myapointments_listview);
			List<Event> events = new ArrayList<Event>();
			Event event;
			for(int i=0;i<10;i++)
			{
				event = new Event();
				event.setEventId("id"+i);
				event.setEventName("Name : "+i);
				event.setDescription("Description : "+i);
				event.setPlace("Place : "+i);
				event.setFromDate(new Date());
				event.setToDate(new Date());
				events.add(event);
			}
			listView.setAdapter(new CustomMyAppointmentsListAdapter(callbackActivity,events));
			return rootView;
		}
		@Override
		public void onAttach(Activity activity) {
			// TODO Auto-generated method stub
			super.onAttach(activity);
			callbackActivity=(FragmentActivity)activity;
		}
	}
}

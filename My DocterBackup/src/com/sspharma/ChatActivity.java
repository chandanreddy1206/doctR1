package com.paulusworld.drawernavigationtabs;

import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChatActivity extends Fragment {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	public static final String TAG = ChatActivity.class.getSimpleName();

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	public static ChatActivity newInstance() {
		return new ChatActivity();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_chat, container, false);
		mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

		mViewPager = (ViewPager) v.findViewById(R.id.chat_pager);
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
			Fragment fragment = null;
			switch (position) {
			case 0:
				fragment = new VoiceChatFragment();
				break;
			case 1:
				fragment = new VideoChatFragment();
				break;
			default:
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 2 total pages(Voice & Video).
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.chat_voice).toUpperCase(l);
			case 1:
				return getString(R.string.chat_video).toUpperCase(l);
			}
			return null;
		}
	}

	
	/**
	 * A fragment representing Video Chat. But displays
	 * "Premium package. Not available for Indian users".
	 */
	public static class VideoChatFragment extends Fragment {

		public VideoChatFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_tabbed_chat_video, container, false);
			TextView dummyTextView = (TextView) rootView.findViewById(R.id.chat_video_section_label);
			dummyTextView.setText("Premium package. Not available for Indian users");
			return rootView;
		}
	}

}

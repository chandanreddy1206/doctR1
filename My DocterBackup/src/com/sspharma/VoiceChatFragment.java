package com.paulusworld.drawernavigationtabs;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;
import com.paulusworld.aynctask.EndpointAsyncInterface;
import com.paulusworld.aynctask.EndpointsgetAsync;
import com.paulusworld.drawernavigationtabs.adapter.CustomDoctorListAdapter;
import com.paulusworld.drawernavigationtabs.bean.Doctor;
import com.paulusworld.drawernavigationtabs.constants.Constants;
import com.paulusworld.drawernavigationtabs.util.JsonUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * A fragment representing Voice Chat. Facilitates text and voice chat.
 */
public class VoiceChatFragment extends Fragment implements EndpointAsyncInterface<List<Doctor>> {

	private ArrayAdapter<String> listAdapter;

	/*
	 * private TextView location; private TextView gender; private TextView age;
	 */
	private RadioGroup doctorCategory;
	private RadioButton doctorGP;
	private RadioButton doctorExpert;
	private ListView doctorListView;
	List<Doctor> doctors;
	ProgressDialog getdoctorsDialogue;
	private FragmentActivity callbackActivity;

	public VoiceChatFragment() {
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		callbackActivity = (FragmentActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tabbed_chat_voice, container, false);
		/*
		 * ListView listView = (ListView)
		 * rootView.findViewById(R.id.chat_voice_list_view); List<String>
		 * strings = new ArrayList<String>(); for (int i = 0; i < 100; i++) {
		 * strings.add(i + ""); } listAdapter = new
		 * ArrayAdapter<String>(getActivity(), R.layout.drawer_list_item,
		 * strings); listView.setAdapter(listAdapter);
		 * listView.setOnScrollListener(new AbsListView.OnScrollListener() {
		 * private int mLastFirstVisibleItem;
		 * 
		 * @Override public void onScrollStateChanged(AbsListView view, int
		 * scrollState) { // TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void onScroll(AbsListView view, int
		 * firstVisibleItem, int visibleItemCount, int totalItemCount) {
		 * TypedValue tv = new TypedValue(); if (mLastFirstVisibleItem <
		 * firstVisibleItem) { getActivity().getActionBar().hide();
		 * 
		 * } if (mLastFirstVisibleItem > firstVisibleItem) {
		 * 
		 * if (getActivity().getTheme().resolveAttribute(android.R.attr.
		 * actionBarSize, tv, true)) {
		 * System.out.println(TypedValue.complexToDimensionPixelSize(tv.data,
		 * getResources().getDisplayMetrics())); }
		 * getActivity().getActionBar().show(); } mLastFirstVisibleItem =
		 * firstVisibleItem; } });
		 */

		/*
		 * location = (TextView)
		 * rootView.findViewById(R.id.chat_voice_location); gender = (TextView)
		 * rootView.findViewById(R.id.chat_voice_gender); age = (TextView)
		 * rootView.findViewById(R.id.chat_voice_age);
		 */
		doctorCategory = (RadioGroup) rootView.findViewById(R.id.chat_voice_doctor_category);
		doctorGP = (RadioButton) rootView.findViewById(R.id.chat_voice_gp);
		doctorExpert = (RadioButton) rootView.findViewById(R.id.chat_voice_expert);
		doctorCategory.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				try {
					if (checkedId == R.id.chat_voice_gp) {

						Type type = new TypeToken<List<Doctor>>() {
						}.getType();
						new EndpointsgetAsync<List<Doctor>>(VoiceChatFragment.this, type).execute(Constants.listDoctors + URLEncoder.encode(Constants.queryForGP, "UTF-8"));
//						new getDoctorsListAsync()
//								.execute(Constants.listDoctors + URLEncoder.encode(Constants.queryForGP, "UTF-8"));

					} else if (checkedId == R.id.chat_voice_expert) {
						Type type = new TypeToken<List<Doctor>>() {
						}.getType();
						new EndpointsgetAsync<List<Doctor>>(VoiceChatFragment.this, type).execute(Constants.listExpertDoctors);

//						new getDoctorsListAsync().execute(Constants.listExpertDoctors);
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		doctorListView = (ListView) rootView.findViewById(R.id.chat_voice_doctor_list_view);
		try {

			getdoctorsDialogue = new ProgressDialog(callbackActivity);
			getdoctorsDialogue.setCancelable(false);
			getdoctorsDialogue.show();
			Type type = new TypeToken<List<Doctor>>() {
			}.getType();
			new EndpointsgetAsync<List<Doctor>>(this, type)
					.execute(Constants.listDoctors + URLEncoder.encode(Constants.queryForGP, "UTF-8"));
			// new
			// getDoctorsListAsync().execute(Constants.listDoctors+URLEncoder.encode(Constants.queryForGP,
			// "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// doctors= new ArrayList<Doctor>();
		// for(int i =0;i<10;i++)
		// {
		// Doctor doctor = new Doctor();
		// doctor.setName("D: "+i);
		// doctor.setRating(i%5);
		// doctors.add(doctor);
		// }
		// doctorListView.setAdapter(new
		// CustomDoctorListAdapter(callbackActivity, doctors));
		return rootView;
	}

	class getDoctorsListAsync extends AsyncTask<String, String, JSONArray> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			getdoctorsDialogue = new ProgressDialog(callbackActivity);
			getdoctorsDialogue.setCancelable(false);
			getdoctorsDialogue.show();
		}

		@Override
		protected JSONArray doInBackground(String... params) {
			return JsonUtil.requestContent(params[0]);

		}

		@Override
		protected void onPostExecute(JSONArray items) {
			// TODO Auto-generated method stub
			if (items != null) {
				Log.e("onPostExecute result", items + "");
				doctors = new ArrayList<Doctor>();

				try {
					if (items.length() != 0) {
						for (int i = 0; i < items.length(); i++) {
							JSONObject doctorObject = items.getJSONObject(i);
							Log.e("onPostExecute doctorObject", doctorObject + "");

							Doctor doctor = new Doctor(doctorObject.getString("name"), doctorObject.getString("email"),
									doctorObject.getString("category"), doctorObject.getInt("rating"),
									"https://drive.google.com/uc?id=" + doctorObject.getString("driveFolderId"));
							Log.e("doctor", doctor + "");
							// Video video = new
							// Video(videoObject.getString("title"),
							// videoObject.getString("description"),
							// videoObject.getJSONObject("player")
							// .getString("default"),
							// videoObject.getJSONObject("thumbnail")
							// .getString("sqDefault"));

							doctors.add(doctor);
						}
					}

				} catch (JSONException e) {
					// manage exceptions
				}
				if (doctors != null && !(doctors.isEmpty())) {
					Log.e("doctor if not empty", doctors + "");
					doctorListView.setAdapter(new CustomDoctorListAdapter(callbackActivity, doctors));

				} else {
					getdoctorsDialogue.dismiss();
					Log.e("doctor else", doctors + "");
					return;
				}
			} else {
				doctors = new ArrayList<Doctor>();
				doctorListView.setAdapter(new CustomDoctorListAdapter(callbackActivity, doctors));
			}

			getdoctorsDialogue.dismiss();
		}

	}

	@Override
	public void updateData(List<Doctor> doctors) {
		// TODO Auto-generated method stub
		getdoctorsDialogue.dismiss();
		Log.e("updateData Return", doctors+"");
		if (doctors != null && !(doctors.isEmpty())) {
			Log.e("doctor if not empty", doctors + "");
			doctorListView.setAdapter(new CustomDoctorListAdapter(callbackActivity, doctors));

		} else {

			Log.e("doctor else", doctors + "");
			return;
		}
	}

}

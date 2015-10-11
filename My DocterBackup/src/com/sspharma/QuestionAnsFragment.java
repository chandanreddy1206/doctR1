package com.sspharma;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.sspharma.adapter.CustomChatVoiceMessageListAdapter;
import com.sspharma.aynctask.EndpointAsyncInterface;
import com.sspharma.aynctask.EndpointsgetAsync;
import com.sspharma.bean.Doctor;
import com.sspharma.bean.User;
import com.sspharma.bean.VoiceMessage;
import com.sspharma.constants.Constants;
import com.sspharma.util.AudioUtil;
import com.sspharma.util.CustomVolleyRequestQueue;
import com.sspharma.util.SharingPreferences;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class QuestionAnsFragment extends Fragment
		implements OnClickListener, EndpointAsyncInterface<List<VoiceMessage>> {
	public static final String TAG = QuestionAnsFragment.class.getSimpleName();

	private NetworkImageView doctorImageView;
	private TextView doctorName;
	private TextView doctorCategory;
	private RatingBar doctorRating;
	private ListView questionAnsListView;
	private ToggleButton record;
	private ToggleButton play;
	private Button send;
	// private Button bookAppointment;
	private MediaPlayer mPlayer = null;
	private MediaRecorder mRecorder = null;
	ProgressDialog dialogue;
	private FragmentActivity callbackActivity;
	Doctor doctor;
	SharingPreferences<User> sharingPreferences = new SharingPreferences<User>();
	User user;

	public static QuestionAnsFragment newInstance() {
		return new QuestionAnsFragment();
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		callbackActivity = (FragmentActivity) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		dialogue = new ProgressDialog(callbackActivity);
		user = sharingPreferences.get(callbackActivity, "user", User.class);
		Bundle args = getArguments();
		doctor = (Doctor) args.getSerializable("doctor");

		View view = inflater.inflate(R.layout.fragment_question_ans, null);

		doctorImageView = (NetworkImageView) view.findViewById(R.id.question_ans_doctor_network_image_view);
		doctorName = (TextView) view.findViewById(R.id.question_ans_doctor_name);
		doctorCategory = (TextView) view.findViewById(R.id.question_ans_doctor_category);
		// doctorCategory = (TextView)
		// view.findViewById(R.id.question_ans_doctor_category);
		doctorRating = (RatingBar) view.findViewById(R.id.question_ans_doctor_rating_bar);
		questionAnsListView = (ListView) view.findViewById(R.id.question_ans_listview);

		record = (ToggleButton) view.findViewById(R.id.question_ans_record);
		play = (ToggleButton) view.findViewById(R.id.question_ans_play);
		send = (Button) view.findViewById(R.id.question_ans_send);

		record.setOnClickListener(this);
		play.setOnClickListener(this);
		send.setOnClickListener(this);

		ImageLoader imageLoader = CustomVolleyRequestQueue.getInstance(callbackActivity.getApplicationContext())
				.getImageLoader();
		// Image URL - This can point to any image file supported by Android
		imageLoader.get(doctor.getProfilepicUrl(),
				ImageLoader.getImageListener(doctorImageView, R.drawable.doctor, R.drawable.doctor));
		doctorImageView.setImageUrl(doctor.getProfilepicUrl(), imageLoader);

		doctorName.setText(doctor.getName());
		doctorCategory.setText(doctor.getCategory());
		doctorRating.setRating(doctor.getRating());

		// List<VoiceMessage> voiceMessages = new ArrayList<VoiceMessage>();
		// VoiceMessage voiceMessage = new VoiceMessage();
		// voiceMessage.setLocalFileUlr("doctorquestion.3gp");
		// voiceMessages.add(voiceMessage);
		// VoiceMessage voiceMessage2 = new VoiceMessage();
		// voiceMessage.setLocalFileUlr("doctorquestion.3gp");
		// voiceMessages.add(voiceMessage2);
		Type type = new TypeToken<List<VoiceMessage>>() {

		}.getType();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("doctorEmail = " + doctor.getEmail());
		stringBuilder.append(" and ");
		stringBuilder.append("patientEmail = " + user.getEmail());
		dialogue.show();
		try {
			new EndpointsgetAsync<List<VoiceMessage>>(QuestionAnsFragment.this, type)
					.execute(Constants.listvoiceMessages + URLEncoder.encode(stringBuilder.toString(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		callbackActivity.getMenuInflater().inflate(R.menu.qusansfragmentmenutotakeappointment, menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.question_ans_book_appointment) {
			FragmentManager fragmentManager = callbackActivity.getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.content_frame, BookAppointmentCalenderFragment.newInstance(),
					BookAppointmentCalenderFragment.TAG);
			fragmentTransaction.addToBackStack("tag");
			fragmentTransaction.commit();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		String mFileName = Constants.APP_DIRECTORY + "/" + Constants.audioFileName;
		if (v.getId() == record.getId()) {
			if (record.isChecked()) {
				AudioUtil.startRecording(mFileName);
			} else {
				AudioUtil.stopRecording();
			}
		} else if (v.getId() == play.getId()) {
			if (play.isChecked()) {
				AudioUtil.startPlaying(mFileName);
			} else {
				AudioUtil.stopPlaying();
			}

		} else if (v.getId() == send.getId()) {
			File file = new File(mFileName);
			if(!file.exists())
			{
				Toast.makeText(callbackActivity, "Please record your question first", Toast.LENGTH_SHORT).show();
				return;
			}
			dialogue.show();
			new fileUpload().execute();

			// String mFileName = Environment.getExternalStorageDirectory()
			// .getAbsolutePath();
			// mFileName += "/" + Constants.audioFileName;
			// Map<String,Object> mapForInsertServlet=new
			// LinkedHashMap<String,Object>();
			// mapForInsertServlet.put("file", new File(mFileName));
			// mapForInsertServlet.put("patientEmail", user.getEmail());
			// mapForInsertServlet.put("doctorEmail", doctor.getEmail());
			// mapForInsertServlet.put("title", mFileName);
			// Type type = new TypeToken<VoiceMessage>(){
			//
			// }.getType();
			// new EndpointsPostAsync<VoiceMessage>(QuestionAnsFragment.this,
			// mapForInsertServlet,type).execute(Constants.insertVoiceMessageServlet);
			// dialogue.show();

		}
		// else if (v.getId() == bookAppointment.getId()) {
		// FragmentManager fragmentManager =
		// callbackActivity.getSupportFragmentManager();
		// FragmentTransaction fragmentTransaction =
		// fragmentManager.beginTransaction();
		// fragmentTransaction.replace(R.id.content_frame,
		// BookAppointmentCalenderFragment.newInstance(),
		// BookAppointmentCalenderFragment.TAG);
		// fragmentTransaction.addToBackStack("tag");
		// fragmentTransaction.commit();
		// }
	}

	class fileUpload extends AsyncTask<String, Integer, RequestParams> {

		@Override
		protected RequestParams doInBackground(String... params) {
			RequestParams parameters = new RequestParams();
			String mFileName = Constants.APP_DIRECTORY + "/" + Constants.audioFileName;
			try {
				File file = new File(mFileName);
				parameters.put("file",file);
				parameters.put("patientEmail", user.getEmail());
				parameters.put("doctorEmail", doctor.getEmail());
				parameters.put("title", "SomeTitle");
				parameters.put("voiceMsgType", Constants.uservoiceMessageType);
				Log.i(QuestionAnsFragment.class.getSimpleName(), "parameters : " + parameters);
				return parameters;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(RequestParams result) {
			// TODO Auto-generated method stub
			Log.i(QuestionAnsFragment.class.getSimpleName(), "onPostExecute");
			AsyncHttpClient client = new AsyncHttpClient();
			client.setTimeout(30*1000);
			RequestHandle  req = client.post("https://v1-dot-my-doctor12.appspot.com/insertVoiceMessage", result, new uploadServ());
		}
	}

	class uploadServ extends AsyncHttpResponseHandler {

		@Override
		public void onProgress(int bytesWritten, int totalSize) {
			Log.v("AsyncHttpResponseHandler", String.format("Progress %d from %d (%2.0f%%)",
					new Object[] { Integer.valueOf(bytesWritten), Integer.valueOf(totalSize), Double.valueOf(
							totalSize <= 0 ? -1D : (((double) bytesWritten * 1.0D) / (double) totalSize) * 100D) }));

			Double delta = Double
					.valueOf(totalSize <= 0 ? -1D : (((double) bytesWritten * 1.0D) / (double) totalSize) * 100D);

		}

		@Override
		public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
			// TODO Auto-generated method stub
			dialogue.dismiss();
			Log.i(QuestionAnsFragment.class.getSimpleName(), "onFailure");

		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			Log.i(QuestionAnsFragment.class.getSimpleName(), "onFinish");

		}

		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			Log.i(QuestionAnsFragment.class.getSimpleName(), "onStart");

		}
		@Override
		public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
			// TODO Auto-generated method stub
			dialogue.dismiss();
			Log.i(QuestionAnsFragment.class.getSimpleName(), "onSuccess");
			String response = new String(arg2);
			Log.i(TAG, "response : "+response);
			if(response != null)
			{
				VoiceMessage voiceMessage = new Gson().fromJson(response, VoiceMessage.class);
				File tempFile = new File(Constants.APP_DIRECTORY + "/" + Constants.audioFileName);
				File msgFile = new File(Constants.APP_DIRECTORY + "/" + voiceMessage.getLocalFileName());
				tempFile.renameTo(msgFile);
			}
		}
	}

	@Override
	public void updateData(List<VoiceMessage> voiceMessages) {
		// TODO Auto-generated method stub
		dialogue.dismiss();
		if (voiceMessages == null || voiceMessages.isEmpty()) {

		} else {
			questionAnsListView.setAdapter(new CustomChatVoiceMessageListAdapter(getActivity(), voiceMessages));
		}

	}

}

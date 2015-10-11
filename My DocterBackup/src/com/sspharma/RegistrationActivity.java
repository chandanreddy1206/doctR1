package com.paulusworld.drawernavigationtabs;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.reflect.TypeToken;
import com.paulusworld.aynctask.EndpointAsyncInterface;
import com.paulusworld.aynctask.EndpointsPostAsync;
import com.paulusworld.aynctask.EndpointsgetAsync;
import com.paulusworld.drawernavigationtabs.bean.User;
import com.paulusworld.drawernavigationtabs.constants.Constants;
import com.paulusworld.drawernavigationtabs.util.AlertDialogueUtil;
import com.paulusworld.drawernavigationtabs.util.CustomVolleyRequestQueue;
import com.paulusworld.drawernavigationtabs.util.SharingPreferences;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RegistrationActivity extends Activity implements OnClickListener,EndpointAsyncInterface<User> {

	private static final String TAG = RegistrationActivity.class.getSimpleName();

	private NetworkImageView profilePicNetworkImageView;
	private ImageLoader profilePicImageLoader;
	private EditText displayName;
	private EditText age;
	private RadioGroup gender;
	private RadioButton male;
	private RadioButton female;
	private Button register;
	private EditText email;
	User user;
	ProgressDialog progressbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		setTitle(R.string.registration_title);
		profilePicNetworkImageView = (NetworkImageView) findViewById(R.id.registration_profile_pic);
		displayName = (EditText) findViewById(R.id.registration_display_name);
		age = (EditText) findViewById(R.id.registration_age);
		gender = (RadioGroup) findViewById(R.id.registration_radio_group_gender);
		male = (RadioButton) findViewById(R.id.registration_radio_male);
		female = (RadioButton) findViewById(R.id.registration_radio_female);
		register = (Button) findViewById(R.id.registration_register_button);
		email = (EditText) findViewById(R.id.registration_email);
		register.setOnClickListener(this);
		
		Bundle bundle = getIntent().getExtras();
		 user = (User) bundle.getSerializable("user");
		displayUser(user);
		// setContentView(R.layout.activity_main);
		/*
		 * Intent intent = AccountPicker.newChooseAccountIntent(null, null, new
		 * String[]{"com.google"}, false, null, null, null, null);
		 * startActivityForResult(intent, SOME_REQUEST_CODE);
		 */

	}

	private void displayUser(User user) {
		displayName.setText(user.getName());
		profilePicImageLoader = CustomVolleyRequestQueue.getInstance(this.getApplicationContext()).getImageLoader();
		// Image URL - This can point to any image file supported by Android
		profilePicImageLoader.get(user.getProfilePic(), ImageLoader.getImageListener(profilePicNetworkImageView,
				android.R.drawable.ic_menu_add, android.R.drawable.ic_dialog_alert));
		profilePicNetworkImageView.setImageUrl(user.getProfilePic(), profilePicImageLoader);
		age.setText(user.getAge().toString());
		if (user.getGender().equals("male"))
			gender.check(male.getId());
		else if (user.getGender().equals("female"))
			gender.check(female.getId());
		email.setText(user.getEmail());
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == register.getId())
		{
			
			progressbar=new ProgressDialog(RegistrationActivity.this);
			progressbar.setCancelable(false);
		
			progressbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressbar.show();
			
			
			
			Map<String,Object> mapForUserJsonObject=new LinkedHashMap<String,Object>();
			mapForUserJsonObject.put("name",displayName.getText().toString());
			mapForUserJsonObject.put("isPremiumUser",user.getIsPremiumUser());
			mapForUserJsonObject.put("profilePic", user.getProfilePic());
			 int id= gender.getCheckedRadioButtonId();
	           if(id==R.id.registration_radio_male){
	        	   mapForUserJsonObject.put("gender","M");   
	           }
	           else{
	        	   mapForUserJsonObject.put("gender","F");  
	           }
			mapForUserJsonObject.put("email", user.getEmail());
			mapForUserJsonObject.put("age", age.getText().toString().trim());
			Type type=new TypeToken<User>(){
				
			}.getType();
		new EndpointsPostAsync<User>(RegistrationActivity.this, mapForUserJsonObject, type).execute(Constants.insertUser);
	
//			new PostAsync().execute(Constants.insertUser);
		}
	}

	@Override
	public void updateData(final User result) {
		User user = new User();
		user.setName(displayName.getText().toString());
		user.setEmail(email.getText().toString());
//		user.setGender();
		SharingPreferences<User> sharingPreferences = new SharingPreferences<User>();
		sharingPreferences.save(this, "user", user);
		progressbar.dismiss();
		
		 AlertDialog.Builder builder=	AlertDialogueUtil.showAlertDialogueWithPositiveButton(RegistrationActivity.this, "Successfully Registred", "Registration Status");
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if(result!=null){
				Intent i=new Intent(RegistrationActivity.this, MainActivity.class);
				startActivity(i);
				finish();
				}
				else{
					dialog.cancel();
				}
			}
		});
		builder.show();
		
		// TODO Auto-generated method stub
		
	}

	
	
	
}

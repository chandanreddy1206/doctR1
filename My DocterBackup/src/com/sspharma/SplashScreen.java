package com.sspharma;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.sspharma.aynctask.LoadingTask;
import com.sspharma.aynctask.LoadingTask.LoadingTaskFinishedListener;
import com.sspharma.bean.User;
import com.sspharma.util.SharingPreferences;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

public class SplashScreen extends Activity
		implements ConnectionCallbacks, OnConnectionFailedListener, ResultCallback<LoadPeopleResult> , LoadingTaskFinishedListener {
	/* Request code used to invoke sign in user interactions. */
	private static final int RC_SIGN_IN = 0;
	private static String TAG=SplashScreen.class.getSimpleName();
	/* Client used to interact with Google APIs. */
	private GoogleApiClient mGoogleApiClient;

	/*
	 * A flag indicating that a PendingIntent is in progress and prevents us
	 * from starting further intents.
	 */
	private boolean mIntentInProgress;

	private static final int SOME_REQUEST_CODE = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		
		mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
				.addScope(Plus.SCOPE_PLUS_PROFILE).build();
	       // Find the progress bar
	        ProgressBar progressBar = (ProgressBar) findViewById(R.id.activity_splash_progress_bar);
	        // Start your loading
	     new LoadingTask(progressBar, SplashScreen.this).execute();	
		
	}

	protected void onStart() {
		super.onStart();	
	}

	protected void onStop() {
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			Log.e(TAG, "onStop() called. disconnecting api client");
			mGoogleApiClient.disconnect();
		}
	}

	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		Log.e(TAG, "onActivityResult() with requestCode : "+requestCode+" Result Code : "+resultCode); 
		if (requestCode == RC_SIGN_IN) {
			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		} else if (requestCode == SOME_REQUEST_CODE && resultCode == RESULT_OK) {
			String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
			Log.e(TAG, "Account Name : "+accountName);
		}
	}

	public void onConnectionFailed(ConnectionResult result) {
		Log.e(TAG, "onConnectionFailed : hasResolution : "+result.hasResolution());
		if (!mIntentInProgress && result.hasResolution()) {
			try {
				mIntentInProgress = true;
				Log.e(TAG, "onConnectionFailed : getResolution : "+result.getResolution().getIntentSender());
				startIntentSenderForResult(result.getResolution().getIntentSender(), RC_SIGN_IN, null, 0, 0, 0);
			} catch (SendIntentException e) {
				// The intent was canceled before it was sent. Return to the
				// default
				// state and attempt to connect to get an updated
				// ConnectionResult.
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
		else if(!mIntentInProgress && !result.hasResolution())
		{
			new AlertDialog.Builder(this)
			.setCancelable(false)
			.setMessage("Please update your Google Play Store and Google Play Services")
			.setTitle("Alert")
			.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					onBackPressed();
				}
			
			}).create().show();
		}
	}

	public void onConnected(Bundle connectionHint) {
		// We've resolved any connection errors. mGoogleApiClient can be used to
		// access Google APIs on behalf of the user.
		Log.e(TAG, "Api client Connected");
		
		Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);
		Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
		
		if (currentPerson != null) {
			User user = new User();
			user.setEmail(Plus.AccountApi.getAccountName(mGoogleApiClient));
			user.setName(currentPerson.getDisplayName());
			user.setProfilePic(currentPerson.getImage().getUrl().split("\\?")[0]);
			user.setAge(currentPerson.getAgeRange().getMin());
			if (currentPerson.getGender() == 0)
				user.setGender("male");
			else if (currentPerson.getGender() == 1)
				user.setGender("female");
			user.setIsPremiumUser(false);
			forwardToLoginActivity(user);
		} else {
			Log.e(TAG, "current person null");
			System.out.println("current person null");
		}
	}

	public void onConnectionSuspended(int cause) {
		mGoogleApiClient.connect();
	}

	@Override
	public void onResult(LoadPeopleResult peopleData) {
		/*
		 * // TODO Auto-generated method stub System.out.println("ppl result "
		 * +peopleData); if (peopleData.getStatus().getStatusCode() ==
		 * CommonStatusCodes.SUCCESS) { PersonBuffer personBuffer =
		 * peopleData.getPersonBuffer(); try { int count =
		 * personBuffer.getCount(); for (int i = 0; i < count; i++) { Log.d(TAG,
		 * "Display name: " + personBuffer.get(i).getDisplayName()); } } finally
		 * { personBuffer.close(); } } else { Log.e(TAG,
		 * "Error requesting people data: " + peopleData.getStatus()); } Person
		 * currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient); if
		 * (currentPerson != null) { String personName =
		 * currentPerson.getDisplayName(); Image personPhoto =
		 * currentPerson.getImage(); String personGooglePlusProfile =
		 * currentPerson.getUrl(); System.out.println(currentPerson); }
		 */
	}
	private void forwardToLoginActivity(User user)
	{
		Log.e(TAG, "opening LoginActivity");
		Intent intent = new Intent(SplashScreen.this, RegistrationActivity.class);
		intent.putExtra("user", user);
		startActivity(intent);
		finish();
	}

	@Override
	public void onTaskFinished() {
		// TODO Auto-generated method stub
		SharingPreferences<User> sharingPreferences = new SharingPreferences<User>();
		User user = sharingPreferences.get(this, "user", User.class);
		Log.e(TAG, "User : "+user);
		if (user == null) {
			
			mGoogleApiClient.connect();
			Log.e(TAG, "called connect()");
		} else {
			Log.e(TAG, "opening MainActivity");
			Intent intent = new Intent(SplashScreen.this, MainActivity.class);
			startActivity(intent);
			finish();
		}	
	}
}

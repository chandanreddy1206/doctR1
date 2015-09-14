package com.paulusworld.drawernavigationtabs.util;

import java.io.IOException;

import com.paulusworld.drawernavigationtabs.QuestionAnsFragment;

import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

public class AudioUtil {
	public static final String TAG = QuestionAnsFragment.class.getSimpleName();
	private static MediaPlayer mPlayer = null;
	private static MediaRecorder mRecorder = null;

	public static void startRecording(String fileName) {
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		String mFileName = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		// mFileName += "/doctorquestion.3gp";
		mFileName += "/" + fileName;
		mRecorder.setOutputFile(mFileName);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		try {
			mRecorder.prepare();
			mRecorder.start();
		} catch (IOException e) {
			Log.e(TAG, "prepare() failed");
		}

	}

	public static void stopRecording() {
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
	}

	public static MediaPlayer startPlaying(String fileName) {
		if(mPlayer != null)
		{
			stopPlaying();
		}
		mPlayer = new MediaPlayer();
		String mFileName = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		mFileName += "/" + fileName;
		try {
			mPlayer.setDataSource(mFileName);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
			Log.e(TAG, "prepare() failed");
		}
		return mPlayer;
	}

	public static void stopPlaying() {
		if (mPlayer != null)
			mPlayer.release();
		mPlayer = null;
	}
	public static  MediaPlayer getCurrentMediaPlayer()
	{
		return mPlayer;
	}
}

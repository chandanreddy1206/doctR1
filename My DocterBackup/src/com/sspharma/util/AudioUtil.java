package com.sspharma.util;

import java.io.IOException;

import com.sspharma.QuestionAnsFragment;

import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

public class AudioUtil {
	public static final String TAG = AudioUtil.class.getSimpleName();
	private static MediaPlayer mPlayer = null;
	private static MediaRecorder mRecorder = null;

	public static void startRecording(String fileLocation) {
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
		mRecorder.setOutputFile(fileLocation);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
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

	public static MediaPlayer startPlaying(String fileLocation) {
		if(mPlayer != null)
		{
			stopPlaying();
		}
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(fileLocation);
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

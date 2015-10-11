package com.sspharma.adapter;

import java.io.File;
import java.util.List;

import com.sspharma.R;
import com.sspharma.aynctask.DownloadFile;
import com.sspharma.aynctask.DownloadFileInterface;
import com.sspharma.bean.VoiceMessage;
import com.sspharma.constants.Constants;
import com.sspharma.util.AudioUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class CustomChatVoiceMessageListAdapter extends BaseAdapter implements DownloadFileInterface {

	private LayoutInflater inflater;
	private FragmentActivity callbackActivity;
	private List<VoiceMessage> voiceMessages;
	private MediaPlayer mPlayer = null;
	private MediaRecorder mRecorder = null;
	private Handler mHandler = new Handler();

	public CustomChatVoiceMessageListAdapter(FragmentActivity callbackActivity,
			List<VoiceMessage> voiceMessages) {

		this.voiceMessages = voiceMessages;
		this.callbackActivity = callbackActivity;
		this.inflater = (LayoutInflater) callbackActivity
				.getApplicationContext().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return voiceMessages.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public class Holder {
		ToggleButton play;
		TextView time;
		SeekBar seekBar;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		if (convertView == null) {
			VoiceMessage voiceMessage = voiceMessages.get(position);
			Holder holder = new Holder();
			View view = inflater
					.inflate(R.layout.chat_voice_user_message, null);

			holder.play = (ToggleButton) view
					.findViewById(R.id.chat_voice_user_msg_play);
			holder.time = (TextView) view
					.findViewById(R.id.chat_voice_user_msg_time);
			holder.seekBar = (SeekBar) view
					.findViewById(R.id.chat_voice_user_seekbar);

			PlayVoiceMessageClickListener voiceMessageClickListener = new PlayVoiceMessageClickListener(
					holder, voiceMessage);
			holder.play.setOnClickListener(voiceMessageClickListener);
			holder.seekBar
					.setOnSeekBarChangeListener(new VoiceMessageSeekBarChangeListener());
			holder.seekBar.setEnabled(false);
			return view;
		} else {
			return convertView;
		}
	}

	private class PlayVoiceMessageClickListener implements View.OnClickListener {
		private Holder holder;
		private VoiceMessage voiceMessage;

		public PlayVoiceMessageClickListener(Holder holder,
				VoiceMessage voiceMessage) {
			this.voiceMessage = voiceMessage;
			this.holder = holder;
		}

		@Override
		public void onClick(View v) {
			if (v.getId() == holder.play.getId()) {
				if (holder.play.isChecked()) {
					String voiceMsgLocation = Constants.APP_DIRECTORY +"/"+ voiceMessage.getLocalFileName();
					File file = new File(voiceMsgLocation);
					if(file.exists())
					{
						holder.seekBar.setEnabled(true);
						final MediaPlayer mediaPlayer = AudioUtil.startPlaying(voiceMsgLocation);
						final int duration = mediaPlayer.getDuration() / 1000;
						holder.seekBar.setMax(duration);
						callbackActivity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								if (mediaPlayer != null) {
									try {
										int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
										if (mCurrentPosition < duration) {
											holder.seekBar.setProgress(mCurrentPosition);
											mHandler.postDelayed(this, 1000);
										} else {
											holder.seekBar.setEnabled(false);
											AudioUtil.stopPlaying();
											holder.seekBar.setProgress(0);
											holder.play.setChecked(false);
										}
									} catch (Exception e) {
										e.printStackTrace();
										try {
											holder.seekBar.setEnabled(false);
											holder.seekBar.setProgress(0);
											holder.play.setChecked(false);
											AudioUtil.stopPlaying();
										} catch (Exception e1) {
											e1.printStackTrace();
										}
									}
								}
							}
						});
					}
					else
					{
						Toast.makeText(callbackActivity, "File Not found", Toast.LENGTH_SHORT).show();
						ProgressDialog dialog = new ProgressDialog(callbackActivity);
						dialog.setMessage("Downloading File. Please wait ...");
						dialog.setCancelable(true);
						dialog.setMax(100);
						dialog.setSecondaryProgress(ProgressDialog.STYLE_HORIZONTAL);
						new DownloadFile(CustomChatVoiceMessageListAdapter.this,dialog).execute(Constants.downloadVoiceMessageServlet + "?fileId="+voiceMessage.getDriveFileId(),voiceMsgLocation );
					}
				} else {
					holder.seekBar.setEnabled(false);
					holder.seekBar.setProgress(0);
					AudioUtil.stopPlaying();
				}

			}
		}

	}

	private class VoiceMessageSeekBarChangeListener implements
			SeekBar.OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			MediaPlayer mMediaPlayer = AudioUtil.getCurrentMediaPlayer();
			if (mMediaPlayer != null && fromUser) {
				mMediaPlayer.seekTo(progress * 1000);
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {

		}

	}

	@Override
	public void downloadCompleted() {
		// TODO Auto-generated method stub
		
	}

}

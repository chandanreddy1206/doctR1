package com.sspharma;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class ChatVoiceMessage extends Fragment {
	public static final String TAG = ChatVoiceMessage.class.getSimpleName();
	private ImageButton play;
	private TextView time;
	private SeekBar seekBar;
	
	public static ChatVoiceMessage newInstance()
	{
		return new ChatVoiceMessage();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chat_voice_user_message,
				container, false);
		play= (ImageButton) view.findViewById(R.id.chat_voice_user_msg_play);
		time=(TextView) view.findViewById(R.id.chat_voice_user_msg_time);
		seekBar=(SeekBar) view.findViewById(R.id.chat_voice_user_seekbar);
		return view;
	}
}

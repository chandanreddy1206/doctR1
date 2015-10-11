package com.paulusworld.drawernavigationtabs;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.paulusworld.drawernavigationtabs.bean.User;
import com.paulusworld.drawernavigationtabs.util.CustomVolleyRequestQueue;
import com.paulusworld.drawernavigationtabs.util.SharingPreferences;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class UserProfileFragment extends Fragment implements OnClickListener
{
	private NetworkImageView profilePicNetworkImageView;
	private ImageLoader profilePicImageLoader;
	private EditText displayName;
	private EditText age;
	private RadioGroup gender;
	private RadioButton male;
	private RadioButton female;
	private Button register;
	private TextView email;
	
	public static UserProfileFragment newInstance()
	{
		return new UserProfileFragment();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_registration, null);
		
		profilePicNetworkImageView = (NetworkImageView) view.findViewById(R.id.registration_profile_pic);
		displayName = (EditText) view.findViewById(R.id.registration_display_name);
		age = (EditText) view.findViewById(R.id.registration_age);
		gender = (RadioGroup) view.findViewById(R.id.registration_radio_group_gender);
		male = (RadioButton) view.findViewById(R.id.registration_radio_male);
		female = (RadioButton) view.findViewById(R.id.registration_radio_female);
		register = (Button) view.findViewById(R.id.registration_register_button);
		email = (TextView) view.findViewById(R.id.registration_email);
		register.setOnClickListener(this);
		
		SharingPreferences<User> sharingPreferences = new SharingPreferences<User>();
		User user = sharingPreferences.get(getActivity().getApplicationContext(), "user", User.class);
		System.out.println(user);
		register.setText("Save");
		displayUser(user);
		return view;
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == register.getId())
		{
			User user = new User();
			user.setName(displayName.getText().toString());
			user.setEmail(email.getText().toString());
			SharingPreferences<User> sharingPreferences = new SharingPreferences<User>();
			sharingPreferences.save(getActivity().getApplicationContext(), "user", user);
		}
	}
	private void displayUser(User user) {
		displayName.setText(user.getName());
		profilePicImageLoader = CustomVolleyRequestQueue.getInstance(getActivity().getApplicationContext()).getImageLoader();
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
}

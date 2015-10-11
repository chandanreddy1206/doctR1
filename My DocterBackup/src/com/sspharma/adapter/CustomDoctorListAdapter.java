package com.paulusworld.drawernavigationtabs.adapter;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.paulusworld.drawernavigationtabs.QuestionAnsFragment;
import com.paulusworld.drawernavigationtabs.R;
import com.paulusworld.drawernavigationtabs.bean.Doctor;
import com.paulusworld.drawernavigationtabs.constants.Constants;
import com.paulusworld.drawernavigationtabs.util.CustomVolleyRequestQueue;

public class CustomDoctorListAdapter extends BaseAdapter
{
	private LayoutInflater layoutInflater;
	private List<Doctor> doctors;
	private FragmentActivity callbackActivity;
	public CustomDoctorListAdapter(FragmentActivity callbackActivity,List<Doctor> doctors ) 
	{
		this.callbackActivity=callbackActivity;
		this.layoutInflater=(LayoutInflater)callbackActivity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.doctors=doctors;
	}
	@Override
	public int getCount() {
		return doctors.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	public class Holder
    {
        NetworkImageView doctorImageView;
        TextView doctorName;
        RatingBar doctorRatingBar;
    }
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder = new Holder();
		if(convertView == null)
		{
			final Doctor doctor = doctors.get(position);
			View doctorView = layoutInflater.inflate(R.layout.doctor_list_element,null);
			holder.doctorImageView = (NetworkImageView) doctorView.findViewById(R.id.doctor_network_image_view);
			holder.doctorName = (TextView) doctorView.findViewById(R.id.doctor_name);
			holder.doctorRatingBar = (RatingBar) doctorView.findViewById(R.id.doctor_rating_bar);
			holder.doctorRatingBar.setEnabled(false);
			holder.doctorName.setText(doctor.getName());
			holder.doctorRatingBar.setRating(doctor.getRating());
			
			ImageLoader imageLoader = CustomVolleyRequestQueue.getInstance(callbackActivity.getApplicationContext()).getImageLoader();
			// Image URL - This can point to any image file supported by Android
//			imageLoader.get("http://cliparts101.com/files/828/444D99AD3598558DAE6CAC3676A3A97D/Doctor_01.png", ImageLoader.getImageListener(holder.doctorImageView,
//					R.drawable.doctor, R.drawable.doctor));
//			holder.doctorImageView.setImageUrl("http://cliparts101.com/files/828/444D99AD3598558DAE6CAC3676A3A97D/Doctor_01.png", imageLoader);

			imageLoader.get(doctor.getProfilepicUrl(), ImageLoader.getImageListener(holder.doctorImageView,
					R.drawable.doctor, R.drawable.doctor));
			holder.doctorImageView.setImageUrl(doctor.getProfilepicUrl(), imageLoader);
			
//			holder.doctorImageView.set   TODO
			
			doctorView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Toast.makeText(v.getContext(), "You Clicked "+doctor.getName(), Toast.LENGTH_SHORT).show();
					QuestionAnsFragment questionAnsFragment = new QuestionAnsFragment();
					Bundle args= new Bundle();
					args.putSerializable("doctor", doctor);
					questionAnsFragment.setArguments(args);
					
					/*QuestionAnsFragment questionAnsFragment= QuestionAnsFragment.newInstance();
					Bundle args= new Bundle();
					args.putString(Constants.Bundle_DoctorName, doctor.getName());
					args.putString(Constants.Bundle_DoctorCategory, doctor.getCategory());
					args.putString(Constants.Bundle_DoctorProfilePicUri, doctor.getProfilepicUrl());
					args.putInt(Constants.Bundle_DoctorRating, doctor.getRating());
					args.putInt(Constants.Bundle_target, 1);
					questionAnsFragment.setArguments(args);*/
					
					FragmentManager fragmentManager= callbackActivity.getSupportFragmentManager();
					FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
					fragmentTransaction.replace(R.id.content_frame, questionAnsFragment, QuestionAnsFragment.TAG);
					fragmentTransaction.addToBackStack("tag");
					fragmentTransaction.commit();
				}
			});
			return doctorView;
		}
		else
		{
			return convertView;
		}
	}

}

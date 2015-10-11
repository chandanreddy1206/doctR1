package com.sspharma.constants;

import java.net.URLEncoder;

import android.os.Environment;

public interface Constants {
	String listDoctors = "https://v1-dot-my-doctor12.appspot.com/_ah/api/api/v1/doctors?q=";
	String listExpertDoctors = "https://v1-dot-my-doctor12.appspot.com/_ah/api/api/v1/doctors/experts";
	String insertUser = "https://v1-dot-my-doctor12.appspot.com/_ah/api/api/v1/users?q";
	String insertVoiceMessageServlet = "https://v1-dot-my-doctor12.appspot.com/insertVoiceMessage";
	String downloadVoiceMessageServlet = "https://v1-dot-my-doctor12.appspot.com/downloadVoiceMessage";
	String listvoiceMessages = "https://v1-dot-my-doctor12.appspot.com/_ah/api/api/v1/voicemessages?q=";
	String Bundle_DoctorName = "DoctorName";
	String queryForGP = "category=gp";
	String Bundle_DoctorCategory = "DoctorCategory";
	String Bundle_DoctorProfilePicUri = "DoctorProfilePicUri";
	String Bundle_DoctorRating = "DoctorRating";
	String Bundle_target = "target";
	String audioFileName = "doctorquestion.aac";
	String uservoiceMessageType = "PATIENT_TO_DOCTOR";
	String doctorvoiceMessageType = "DOCTOR_TO_PATIENT";
	String APP_DIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath();
}

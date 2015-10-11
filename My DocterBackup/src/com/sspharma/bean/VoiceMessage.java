package com.sspharma.bean;

import java.io.Serializable;


public class VoiceMessage implements Serializable
{
	public enum VoiceMessageType{
		PATIENT_TO_DOCTOR,DOCTOR_TO_PATIENT;
	}

	private String driveFileId;
	private String title;
	private String doctorEmail;
	private String patientEmail;
	private String localFileName;
	private VoiceMessageType type; // P-D or D-P
	
	public String getDriveFileId() {
		return driveFileId;
	}
	public void setDriveFileId(String driveFileId) {
		this.driveFileId = driveFileId;
	}
	public String getLocalFileName() {
		return localFileName;
	}
	public void setLocalFileName(String localFileUlr) {
		this.localFileName = localFileUlr;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDoctorEmail() {
		return doctorEmail;
	}
	public void setDoctorEmail(String sender) {
		this.doctorEmail = sender;
	}
	public String getPatientEmail() {
		return patientEmail;
	}
	public void setPatientEmail(String receiver) {
		this.patientEmail = receiver;
	}
	public VoiceMessageType getType() {
		return type;
	}
	public void setType(VoiceMessageType type) {
		this.type = type;
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VoiceMessage [localFileUlr=").append(localFileName)
				.append(", driveFileId=").append(driveFileId).append(", title=").append(title).append(", doctorEmail=")
				.append(doctorEmail).append(", patientEmail=").append(patientEmail).append(", type=").append(type)
				.append("]");
		return builder.toString();
	}
	@Override
	public boolean equals(Object object) {
		if(object instanceof VoiceMessage)
		{
			VoiceMessage voiceMessage = (VoiceMessage)object;
			if(voiceMessage.getDriveFileId().equalsIgnoreCase(this.driveFileId))
			{
				return true;
			}
		}
		return false;
	}
	@Override
	public int hashCode() {
		return driveFileId.hashCode();
	}
}

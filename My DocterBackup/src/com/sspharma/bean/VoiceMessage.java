package com.paulusworld.drawernavigationtabs.bean;

import java.io.Serializable;

public class VoiceMessage implements Serializable
{
	private String messageId;
	private String localFileUlr;
	private String driveFileId;
	private String senderType;
	private String senderName;
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getLocalFileUlr() {
		return localFileUlr;
	}
	public void setLocalFileUlr(String localFileUlr) {
		this.localFileUlr = localFileUlr;
	}
	public String getDriveFileId() {
		return driveFileId;
	}
	public void setDriveFileId(String driveFileId) {
		this.driveFileId = driveFileId;
	}
	public String getSenderType() {
		return senderType;
	}
	public void setSenderType(String senderType) {
		this.senderType = senderType;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VoiceMessage [messageId=").append(messageId)
				.append(", localFileUlr=").append(localFileUlr)
				.append(", driveFileId=").append(driveFileId)
				.append(", senderType=").append(senderType)
				.append(", senderName=").append(senderName).append("]");
		return builder.toString();
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof VoiceMessage)
		{
			VoiceMessage voiceMessage = (VoiceMessage)object;
			if(voiceMessage.getMessageId().equalsIgnoreCase(this.messageId))
			{
				return true;
			}
		}
		return false;
	}
	@Override
	public int hashCode() {
		return messageId.hashCode();
	}
}

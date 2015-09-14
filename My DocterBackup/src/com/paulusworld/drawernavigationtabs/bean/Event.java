package com.paulusworld.drawernavigationtabs.bean;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable
{
	private String eventId;
	private String eventName;
	private String description;
	private Date fromDate;
	private Date toDate;
	private String place;
	
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Event [eventId=").append(eventId)
				.append(", eventName=").append(eventName).append(", fromDate=")
				.append(fromDate).append(", toDate=").append(toDate)
				.append(", place=").append(place).append("]");
		return builder.toString();
	}
	@Override
	public boolean equals(Object object) {
		// TODO Auto-generated method stub
		if (object instanceof Event) {
			Event event = (Event) object;
			if (eventId.equalsIgnoreCase(this.getEventId())) {
				return true;
			}
		}
		return false;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return eventId.hashCode();
	}	
}
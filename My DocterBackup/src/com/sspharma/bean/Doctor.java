package com.sspharma.bean;

import java.io.Serializable;

public class Doctor implements Serializable {

	private String name;
	private String email;
	private String category;
	private Integer rating;
private String profilePic; 
	public String getProfilepicUrl() {
	return profilePic;
}
public void setProfilepicUrl(String profilepicUrl) {
	this.profilePic = profilepicUrl;
}
	public Doctor(String name, String email, String category, int rating,String profilepicUrl) {
	// TODO Auto-generated constructor stub
		this.name=name;
		this.email=email;
		this.category=category;
		this.rating=rating;
		this.profilePic=profilepicUrl;
}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Doctor [name=").append(name).append(", email=").append(email).append(", category=")
				.append(category).append(", rating=").append(rating).append("]");
		return builder.toString();
	}

	@Override
	public boolean equals(Object object) {
		// TODO Auto-generated method stub
		if (object instanceof User) {
			User user = (User) object;
			if (user.getEmail().equalsIgnoreCase(this.getEmail())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return email.hashCode();
	}
}

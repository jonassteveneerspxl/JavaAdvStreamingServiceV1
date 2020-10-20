package be.pxl.ja.streamingservice.model;

import be.pxl.ja.streamingservice.util.PasswordUtil;

import java.util.ArrayList;
import java.util.List;

public class Account {
	private String email;
	private String password;
	private PaymentInfo paymentInfo;
	private StreamingPlan streamingPlan;
	private List<Profile> profiles = new ArrayList<>();

	public Account(String email, String password) {
		this.email = email;
		setPassword(password);
		profiles.add(new Profile("Profile1"));
	}

	public void setStreamingPlan(StreamingPlan streamingPlan) {
		this.streamingPlan = streamingPlan;
	}

	public void addProfile(Profile profile) {
		profiles.add(profile);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean verifyPassword(String password) {
		return PasswordUtil.isValid(password, this.password);
	}

	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public void setPassword(String password) {
		this.password = PasswordUtil.encodePassword(password);
	}

	public Profile getFirstProfile() {
		return profiles.get(0);
	}

	public int getNumberOfProfiles() {
		return profiles.size();
	}

	public List<Profile> getProfiles() {
		return profiles;
	}
}

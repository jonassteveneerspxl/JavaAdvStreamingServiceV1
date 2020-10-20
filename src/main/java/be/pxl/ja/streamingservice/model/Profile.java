package be.pxl.ja.streamingservice.model;

import be.pxl.ja.streamingservice.exception.InvalidDateException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class Profile<T> {
	private String name;
	private LocalDate dateOfBirth;
	private String avatar;
	private List<Content> recentlyWatched;
	private List<Content> currentlyWatching;
	private List<Content> myList;

	public Profile(String name, String avatar) {
		this.name = name;
		this.avatar = avatar;
	}

	public Profile(String name) {
		this(name, "profile1");
	}
	public void startWatching(Content content){
		currentlyWatching.remove(content);
		currentlyWatching.add(0, content);
	}
	public void finishWatching(Content content) {
		currentlyWatching.remove(content);
		recentlyWatched.add(0, content);
	}
	public void addToMyList(Content content){
		if (!myList.contains(content)){
			myList.add(0, content);
		}
	}
	public void  removeFromMyList(Content content){
		myList.remove(content);
	}

	public String getName() {
		return name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) throws InvalidDateException {
		if (dateOfBirth.isAfter(LocalDate.now())){
			throw new InvalidDateException();
		}

		this.dateOfBirth = dateOfBirth;
	}

	public int getAge(){
		if (dateOfBirth == null){
			return 0;
		}
		return Period.between(dateOfBirth, LocalDate.now()).getYears();
	}

	public List<Content> getRecentlyWatched() {
		return recentlyWatched;
	}

	public List<Content> getCurrentlyWatching() {
		return currentlyWatching;
	}

	public List<Content> getMyList() {
		return myList;
	}

	public boolean allowedToWatch(Content content){
		return content.getMaturityRating() == null || content.getMaturityRating().getMinimumAge() <= getAge();
	}

	public boolean isInMyList(Content content) {
		return myList.contains(content);
	}
}

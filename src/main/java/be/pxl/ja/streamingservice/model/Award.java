package be.pxl.ja.streamingservice.model;

public class Award {
	private String name;
	private int year;
	private Content content;

	public Award(String name, int year) {
		this.name = name;
		this.year = year;
	}

	public void setContent(Content content) {
		this.content = content;
	}
}

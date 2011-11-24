package edu.mimuw.goodle.client;

public class Course {
	private String shortcut;
	private String namePL;
	private String nameEN;

	public Course() {
	}

	public Course(String shortcut, String namePL, String nameEN) {
		this.shortcut = shortcut;
		this.namePL = namePL;
		this.nameEN = nameEN;
	}

	public String getShortcut() {
		return this.shortcut;
	}

	public String getNamePL() {
		return this.namePL;
	}

	public String getNameEN() {
		return this.nameEN;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}

	public void setNamePL(String namePL) {
		this.namePL = namePL;
	}

	public void setNameEN(String nameEN) {
		this.nameEN = nameEN;
	}
}
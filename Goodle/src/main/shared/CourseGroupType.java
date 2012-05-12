package main.shared;

public enum CourseGroupType 
{
	LECTURE,
	CLASS,
	LAB;
	
	@Override
	public String toString()
	{
		if (this.equals(CourseGroupType.LECTURE)) return "Wykład";
		if (this.equals(CourseGroupType.CLASS)) return "Ćwiczenia";
		if (this.equals(CourseGroupType.LAB)) return "Laboratorium";
		return null;
	}
}

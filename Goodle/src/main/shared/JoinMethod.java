package main.shared;

public enum JoinMethod 
{
	OPEN,
	ASK,
	KEY;
	
	
	/* TODO Ostatecznie metoda rejetracji powinna być sygnalizowana za pomocą obrazka */
	@Override
	public String toString()
	{
		if (this.equals(JoinMethod.OPEN)) return "Otwarty";
		if (this.equals(JoinMethod.ASK)) return "Zaproszenie";
		if (this.equals(JoinMethod.KEY)) return "Zabezpieczony kluczem";
		return null;
	}
}

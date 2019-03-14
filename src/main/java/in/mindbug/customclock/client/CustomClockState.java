package in.mindbug.customclock.client;

public class CustomClockState extends com.vaadin.shared.AbstractComponentState {

	private static final long serialVersionUID = 1L;

	// State can have both public variable and bean properties
	// time in seconds
	public long serverTime = 0;
	public int fetchInterval = 0;

	// style
	public String backgroundColor = "white";
	public String fontColor = "blue";
	public String fontWeight = "normal";
	public String fontSize = "18pt";

}
package in.mindbug.customclock.client;

import com.google.gwt.user.client.ui.HTML;

// Extend any GWT Widget
public class CustomClockWidget extends HTML {

	public CustomClockWidget() {

		// CSS class-name should not be v- prefixed
		setStyleName("custom-clock");

		// State is set to widget in CustomClockConnector
	}

}
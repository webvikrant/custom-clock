package in.mindbug.customclock;

import in.mindbug.customclock.client.CustomClockServerRpc;
import in.mindbug.customclock.client.CustomClockState;

import java.util.Date;

// This is the server-side UI component that provides public API 
// for CustomClock
public class CustomClock extends com.vaadin.ui.AbstractComponent {

	private static final long serialVersionUID = 1L;

	public CustomClock(int fetchInterval, String backGroundColor, String fontColor, String fontSize, String fontWeight) {

		// To receive events from the client, we register ServerRpc
		CustomClockServerRpc rpc = this::calculateServerTime;
		registerRpc(rpc);

		// set state
		if (backGroundColor != null && !backGroundColor.isEmpty()) {
			getState().backgroundColor = backGroundColor;
		}

		if (fontColor != null && !fontColor.isEmpty()) {
			getState().fontColor = fontColor;
		}

		if (fontSize != null && !fontSize.isEmpty()) {
			getState().fontSize = fontSize;
		}
		
		if (fontWeight != null && !fontWeight.isEmpty()) {
			getState().fontWeight = fontWeight;
		}
		
		calculateServerTime();
	}

	// We must override getState() to cast the state to CustomClockState
	@Override
	protected CustomClockState getState() {
		return (CustomClockState) super.getState();
	}

	private void calculateServerTime() {
		// Update shared state. This state update is automatically
		// sent to the client.
		getState().serverTime = new Date().getTime();
	}
}

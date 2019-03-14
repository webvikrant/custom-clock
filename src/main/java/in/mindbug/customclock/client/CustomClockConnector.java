package in.mindbug.customclock.client;

import in.mindbug.customclock.CustomClock;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

// Connector binds client-side widget class to server-side component class
// Connector lives in the client and the @Connect annotation specifies the
// corresponding server-side component
@Connect(CustomClock.class)
public class CustomClockConnector extends AbstractComponentConnector {

	private static final long serialVersionUID = 1L;
	// ServerRpc is used to send events to server. Communication implementation
	// is automatically created here
	CustomClockServerRpc rpc = RpcProxy.create(CustomClockServerRpc.class, this);

	private Date date;
	private long time = 0;
	private final DateTimeFormat format = DateTimeFormat.getFormat("EEE, dd/MM/yyyy, hh:mm:ss a");
	private com.google.gwt.user.client.Timer timer = null;

	private int fetchInterval = 0;

	private String backgroundColor = "white";
	private String fontColor = "blue";
	private String fontWeight = "bold";
	private String fontSize = "20pt";

	public CustomClockConnector() {

		// To receive RPC events from server, we register ClientRpc implementation
//		registerRpc(CustomTimerClientRpc.class, this);

		date = new Date();
		date.setTime(0);

		timer = new Timer() {
			@Override
			public void run() {
				time = time + 1000;

				// check if time to fetch fresh server time
				if (fetchInterval > 0) {
					if ((time / 1000) % fetchInterval == 0) {
						rpc.fetchServerTime();
					}
				}
				showTime();
			}
		};

	}

	private void showTime() {
		// TODO Auto-generated method stub
		date.setTime(time);
		getWidget().setHTML(generateHtml());
	}

	private String generateHtml() {
		String str = "<div align=\"center\" style=\"background-color:" + backgroundColor + ";\">"
				+ "<span style=\"color:" + fontColor + "; font-weight:" + fontWeight + "; font-size:" + fontSize
				+ ";\">&nbsp;" + format.format(date) + "&nbsp;</span></div>";
		return str;
	}

	// We must implement getWidget() to cast to correct type
	// (this will automatically create the correct widget type)
	@Override
	public CustomClockWidget getWidget() {
		return (CustomClockWidget) super.getWidget();
	}

	// We must implement getState() to cast to correct type
	@Override
	public CustomClockState getState() {
		return (CustomClockState) super.getState();
	}

	// Whenever the state changes in the server-side, this method is called
	@Override
	public void onStateChanged(StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);

		// State is directly readable in the client after it is set in server
		fetchInterval = getState().fetchInterval;

		backgroundColor = getState().backgroundColor;
		fontColor = getState().fontColor;
		fontWeight = getState().fontWeight;
		fontSize = getState().fontSize;

		time = getState().serverTime;

		showTime();

		if (timer != null && !timer.isRunning()) {
			timer.scheduleRepeating(1000);
		}
	}
}

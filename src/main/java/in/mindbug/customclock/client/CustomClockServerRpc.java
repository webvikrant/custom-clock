package in.mindbug.customclock.client;

import com.vaadin.shared.communication.ServerRpc;

// ServerRpc is used to pass events from client to server
public interface CustomClockServerRpc extends ServerRpc {

    // Example API: Widget click is clicked
    public void fetchServerTime();

}

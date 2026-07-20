package dk.nicsilver.pcio.select.model;

public interface NewPortListenerCallback
{
    void onPortReceived(String port, String receivedIp);
}

package dk.nicsilver.pcio.select.model;

import java.util.List;

public interface BroadcastManagerCallback
{
    void onListUpdatedCallback(List<IIpAndName> ips);
    void onPortReceived(String port, String receivedIp);
}

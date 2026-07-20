package dk.nicsilver.pcio.select;

import dk.nicsilver.pcio.select.model.IIpAndName;

import java.util.List;

public interface SelectContract
{
    interface View
    {
        void setupIpList(List<IIpAndName> ips);
        
        void successFullConnect(String port, String receivedIp);
    }
    
    interface Presenter
    {
        void startBroadcast();
        void connect(String ip);
    }
}

package dk.nicsilver.pcioServer;

import java.net.InetAddress;
import java.net.UnknownHostException;

// Class used to get devices name, there's no universal "device" name,
// So the best way to anything like it, is to get the OS's own DNS localhost name
public class HostNameHelper
{
    private static HostNameHelper instance = null;
    private static String hostName;
    
    public static HostNameHelper getInstance()
    {
        if (instance == null)
        {
            hostName = "Unknown";
            instance = new HostNameHelper();
        }
        return instance;
    }
    
    private HostNameHelper()
    {
        try
        {
            InetAddress address;
            address = InetAddress.getLocalHost();
            hostName = address.getHostName();
        } catch (UnknownHostException ex)
        {
            System.out.println("Hostname can not be resolved");
        }
    }
    
    public String getHostName()
    {
        return hostName;
    }
}

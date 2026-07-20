package dk.nicsilver.pcio.action;

import dk.nicsilver.pcio.UDPSender;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ActionSender
{
    private static ActionSender actionSender;
    private UDPSender udpSender;
    private int port;
    private String ip;
    private ThreadPoolExecutor threadPoolExecutor;
    
    private ActionSender()
    {
        this.udpSender = UDPSender.getInstance();
        this.threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }
    
    public synchronized static ActionSender getInstance()
    {
        if (actionSender == null)
        {
            actionSender = new ActionSender();
        }
        return actionSender;
    }
    
    public void sendAction(String action)
    {
        threadPoolExecutor.execute(() -> udpSender.SendUdpPacket(action, ip, port));
    }
    
    public void setPort(String port)
    {
        try
        {
            this.port = Integer.parseInt(port);
        } catch (NumberFormatException e)
        {
            e.printStackTrace();
            this.port = -1;
        }
    }
    
    public void setIp(String ip)
    {
        this.ip = ip;
    }
}

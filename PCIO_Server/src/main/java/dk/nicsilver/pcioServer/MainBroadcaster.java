package dk.nicsilver.pcioServer;

public class MainBroadcaster implements Runnable
{
    private final HostNameHelper hostNameHelper;
    private final UDPSender udpSender;
    private boolean running;
    private static final int timeBetweenBroadcasts = 500;
    
    public MainBroadcaster()
    {
        System.out.println("MainBroadcaster.MainBroadcaster");
        this.hostNameHelper = HostNameHelper.getInstance();
        this.udpSender = UDPSender.getInstance();
    }
    
    @Override
    public void run()
    {
        running = true;
        while (running)
        {
            try
            {
                //Pinging broadcast/255.255.255.255 to hit all clients on network.
                udpSender.SendUdpPacket("PCIOServerBroadcast#!" + hostNameHelper.getHostName(), "255.255.255.255", 47601);
                Thread.sleep(timeBetweenBroadcasts);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}

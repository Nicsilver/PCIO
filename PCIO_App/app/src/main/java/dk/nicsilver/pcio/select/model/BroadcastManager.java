package dk.nicsilver.pcio.select.model;

import dk.nicsilver.pcio.UDPSender;

public class BroadcastManager implements NewPortListenerCallback
{
    private static final String TAG = "BroadcastHandler";
    private BroadcastReceiver broadcastReceiver;
    private boolean broadcastListenerRunning, newPortListenerRunning = false;
    private BroadcastManagerCallback broadcastManagerCallback;
    private NewPortListener newPortListener;
    
    public BroadcastManager(BroadcastManagerCallback broadcastManagerCallback)
    {
        this.broadcastManagerCallback = broadcastManagerCallback;
        newPortListener = new NewPortListener(this);
        this.broadcastReceiver = new BroadcastReceiver();
    }
    
    public void startBroadcastListener()
    {
        if (!broadcastListenerRunning/* && !newPortListenerRunning*/)
        {
            System.out.println("BroadcastManager.startBroadcaster");
            new Thread(() -> broadcastReceiver.ListenForServerIp()).start();
            startIpListGetter();
        }
    }
    
    private void startIpListGetter()
    {
        new Thread(new Runnable()
        {
            int getListCounter = 6; //This is a slightly ghetto way of doing this, perhaps it should run without counting until it has an ip in the list?
            @Override
            public void run()
            {
                broadcastListenerRunning = true;
                while (broadcastListenerRunning)
                {
                    if (getListCounter < 10)
                    {
                        getListCounter++;
                    } else
                    {
                        broadcastManagerCallback.onListUpdatedCallback(broadcastReceiver.getIpList());
                        getListCounter = 0;
                    }
                    try
                    {
                        Thread.sleep(500);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    
    //Start the listener for the new port, and sends a msg to the server that it wants to connect
    public void connect(final String ip)
    {
        if (!newPortListenerRunning)
        {
            newPortListenerRunning = true;
            new Thread(newPortListener).start();
        }
        
        new Thread(() ->
        {
            try
            {
                Thread.sleep(100);
                UDPSender.getInstance().SendUdpPacket("PCIOServerStartSocket", ip, 47600);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }).start();
    }
    
    @Override
    public void onPortReceived(String port, String receivedIp) // Successful connect
    {
        broadcastListenerRunning = false;
        newPortListenerRunning = false;
        broadcastReceiver.stopBroadcastReceiver(); // sets local variable to false, will stop running after one loop
        broadcastManagerCallback.onPortReceived(port, receivedIp);
        System.out.println(TAG + " onPortReceived: called");
    }
}

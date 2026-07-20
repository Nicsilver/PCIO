package dk.nicsilver.pcioServer;

import java.io.IOException;
import java.net.*;

public class MainListener implements Runnable, ActionListenerCallback
{
    private DatagramSocket socket;
    private final UDPSender udpSender;
    private boolean connectionPortListenerRunning = false;
    
    public MainListener()
    {
        this.udpSender = UDPSender.getInstance();
        try
        {
            socket = new DatagramSocket(47600); // The hardcoded ports should be changed to a user changeable port
        } catch (SocketException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run()
    {
        while (true)
        {
            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try
            {
                socket.receive(packet);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            InetAddress address = packet.getAddress();
            
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
            String received = new String(packet.getData(), packet.getOffset(), packet.getLength());
            //System.out.println(packet.getData());
            System.out.println("MainListener.run - received: " + received);
            if (received.contains("PCIO"))
            {
                if (received.contains("ServerStartSocket"))
                {
                    int connectionPort = 47604; //TODO this is gonna be switched with user defined port
                    if (!connectionPortListenerRunning)
                    {
                        System.out.println("MainListener.run - got in here");
                        //TODO Get port from config file
                        new Thread(new ActionListener(connectionPort, this)).start();
                        connectionPortListenerRunning = true;
                    }
                    udpSender.SendUdpPacket("PCIOPortReady#" + connectionPort, "255.255.255.255", 47602);
                }
            }
        }
    }

//    private int getRandomPort()
//    {
//        int randomPort = rng.nextInt(65000 - 40000) + 40000;
//        if (ports.contains(randomPort))
//        {
//            randomPort = getRandomPort();
//        }
//        return randomPort;
//    }

//    @Override
//    public void onOpenPortTimeout(int portNumber)
//    {
//        if (ports.contains(portNumber))
//        {
//            ports.remove(portNumber);
//        }
//    }
    
    @Override
    public void onPortClosed()
    {
        this.connectionPortListenerRunning = false;
    }
}
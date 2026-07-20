 package dk.nicsilver.pcioServer;

import java.io.IOException;
import java.net.*;

public class ActionListener implements Runnable
{
    private final int portNumber;
    private final ActionListenerCallback actionListenerCallback;
    MouseActionHandler mouseActionHandler;
    
    public ActionListener(int portNumber, ActionListenerCallback actionListenerCallback)
    {
        System.out.println("ConnectionListener.ConnectionListener - called");
        this.portNumber = portNumber;
        this.actionListenerCallback = actionListenerCallback;
        this.mouseActionHandler = new MouseActionHandler();
    }
    
    @Override
    public void run()
    {
        boolean running = true;
        System.out.println("ConnectionListener.run");
        try (DatagramSocket socket = new DatagramSocket(portNumber))
        {
            socket.setSoTimeout(1000 * 60 * 30); // After not receiving for 30 min the socket will close.
            while (running)
            {
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                try
                {
                    socket.receive(packet);
                } catch (SocketTimeoutException e)
                {
                    running = false;
                    e.printStackTrace();
//                    socket.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
//                InetAddress address = packet.getAddress();
//                int port = packet.getPort();
//                packet = new DatagramPacket(buf, buf.length, address, port);
                String received = new String(packet.getData(), packet.getOffset(), packet.getLength());
                if (received.contains("PCIO"))
                {
                    System.out.println(received);
                    
                    if (received.contains("MouseAction"))
                    {
                        mouseActionHandler.executeAction(received);
                    }
                }
            }
            this.actionListenerCallback.onPortClosed();
        } catch (SocketException e)
        {
            e.printStackTrace();
        }
//        socket.close();
        System.out.println("socket closed");
    }
    
}
    
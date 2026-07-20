package dk.nicsilver.pcio.select.model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class NewPortListener implements Runnable
{
    private static final String TAG = "NewPortListener";
    private NewPortListenerCallback newPortListenerCallback;
    
    public NewPortListener(NewPortListenerCallback newPortListenerCallback)
    {
        this.newPortListenerCallback = newPortListenerCallback;
    }
    
    @Override
    public void run()
    {
        try (DatagramSocket socket = new DatagramSocket(47602))
        {
            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try
            {
                socket.receive(packet);
                String received = new String(packet.getData(), packet.getOffset(), packet.getLength());
                System.out.println(TAG + " run: received " + received);
                
                if (received.contains("PCIOPortReady"))
                {
                    String receivedIp = String.valueOf(packet.getAddress()).substring(1);
                    System.out.println(TAG + " run: packetAddress" + packet.getAddress() + " pc ip");
                    // Separate the port number from the rest of the packet
                    String newPort = received.substring(received.indexOf('#') + 1);
                    newPortListenerCallback.onPortReceived(newPort, receivedIp);
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        } catch (SocketException e)
        {
            e.printStackTrace();
        }
    }
}

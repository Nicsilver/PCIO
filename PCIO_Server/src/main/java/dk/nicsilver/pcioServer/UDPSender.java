package dk.nicsilver.pcioServer;

import java.io.IOException;
import java.net.*;

public class UDPSender
{
    private DatagramSocket socket;
    private static UDPSender instance;
    
    private UDPSender()
    {
        System.out.println("UDPSender created");
        try
        {
            socket = new DatagramSocket();
        } catch (SocketException e)
        {
            e.printStackTrace();
        }
    }
    
    public synchronized static UDPSender getInstance()
    {
        if (instance == null)
        {
            instance = new UDPSender();
        }
        return instance;
    }
    
    public void SendUdpPacket(String msg, String ip, int port)
    {
        byte[] buf = msg.getBytes();
        DatagramPacket packet = null;
        try
        {
            packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip), port); // The hardcoded ports should be changed to a user changeable port
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        try
        {
            //socket = null;
            socket.send(packet);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

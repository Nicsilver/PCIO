package dk.nicsilver.pcio.select.model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class BroadcastReceiver
{
    private List<IIpAndName> ips = new ArrayList<>();
    private boolean broadCastReceiverRunning = false;
    
    public void ListenForServerIp()
    {
        try (DatagramSocket socket = new DatagramSocket(47601))
        {
            socket.setSoTimeout(1000);
            broadCastReceiverRunning = true;
            while (broadCastReceiverRunning)
            {
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                try
                {
                    socket.receive(packet);
                } catch (SocketTimeoutException e)
                {
                    // Socket timeout ignored.
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                String received = new String(packet.getData(), packet.getOffset(), packet.getLength());
                
                //Check if packet is from PCIO Server.
                if (received.contains("PCIOServerBroadcast"))
                {
                    System.out.println("BroadcastReceiver.ListenForServerIp: msg: " + received);
                    String receivedIp = String.valueOf(packet.getAddress()).substring(1);

                    boolean ipAlreadyPresent = false;
                    for (IIpAndName ip : ips)
                    {
                        if (receivedIp.equals(ip.getIp()))
                        {
                            ipAlreadyPresent = true;
                            break;
                        }
                    }
                    
                    if (!ipAlreadyPresent) // If the received ip is not in the list add it.
                    {
                        String hostName = received.substring(received.indexOf("#!") + 2).substring(received.indexOf("!#") + 1);
                        this.ips.add(new IpAndName(receivedIp, hostName));
                    }
                }
            }
            //While loop over meaning the socket should close.
        } catch (SocketException e)
        {
            e.printStackTrace();
        }
    }
    
    public List<IIpAndName> getIpList()
    {
        System.out.println("BroadcastReceiver.getIpList - number of ips in list: " + ips.size());
        List<IIpAndName> ips = this.ips;
        this.ips = new ArrayList<>();
        for (IIpAndName ip : ips)
        {
            System.out.println("BroadcastReceiver.getIpList - hostname: " + ip.getName() + " ip: " + ip.getIp());
        }
        return ips;
    }
    
    public void stopBroadcastReceiver()
    {
        broadCastReceiverRunning = false;
    }
}

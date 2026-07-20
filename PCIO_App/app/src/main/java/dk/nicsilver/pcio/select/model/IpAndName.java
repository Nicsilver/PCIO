package dk.nicsilver.pcio.select.model;

public class IpAndName implements IIpAndName
{
    private String ipAddress;
    private String name;
    
    public IpAndName(String ipAddress, String name)
    {
        this.ipAddress = ipAddress;
        this.name = name;
    }
    
    @Override
    public String getIp()
    {
        return ipAddress;
    }
    
    @Override
    public void setIp(String ip)
    {
        this.ipAddress = ip;
    }
    
    @Override
    public String getName()
    {
        return name;
    }
}

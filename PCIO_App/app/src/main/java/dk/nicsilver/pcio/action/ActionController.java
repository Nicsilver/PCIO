package dk.nicsilver.pcio.action;

public class ActionController //TODO Change this name!
{
    private ActionSender actionSender;
    
    public ActionController(String ip, String port)
    {
        this.actionSender = ActionSender.getInstance();
        this.actionSender.setIp(ip);
        this.actionSender.setPort(port);
    }
}

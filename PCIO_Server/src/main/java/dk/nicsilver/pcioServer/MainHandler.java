package dk.nicsilver.pcioServer;

public class MainHandler implements TrayIconHelperCallback
{
    public MainHandler()
    {
        new Thread(new MainListener()).start();
        new Thread(new MainBroadcaster()).start();
        
        new TrayIconHelper(this);
    }
    
    @Override
    public void onExitCalled()
    {
        System.exit(0);
    }
}

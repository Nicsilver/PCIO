package dk.nicsilver.pcio.action;

public class ActionPresenter implements ActionContract.presenter
{
    ActionContract.view view;
    ActionController actionController;
    
    public ActionPresenter(ActionContract.view view, String ip, String port)
    {
        this.view = view;
        this.actionController = new ActionController(ip, port);
    }
    
    @Override
    public void onConnectionLost()
    {
    
    }
    
    @Override
    public void onConnectionRegained()
    {
    
    }
}

package dk.nicsilver.pcio.select;

import dk.nicsilver.pcio.select.model.BroadcastManagerCallback;
import dk.nicsilver.pcio.select.model.IIpAndName;
import dk.nicsilver.pcio.select.model.BroadcastManager;

import java.util.List;

public class SelectPresenter implements SelectContract.Presenter, BroadcastManagerCallback
{
    SelectContract.View view;
    BroadcastManager broadcastManager;
    
    public SelectPresenter(SelectContract.View view)
    {
        this.view = view;
        this.broadcastManager = new BroadcastManager(this);
    }
    
    @Override
    public void startBroadcast()
    {
        broadcastManager.startBroadcastListener();
    }
    
    @Override
    public void connect(String ip)
    {
        broadcastManager.connect(ip);
    }
    
    @Override
    public void onListUpdatedCallback(List<IIpAndName> ips)
    {
        view.setupIpList(ips);
    }
    
    @Override
    public void onPortReceived(String port, String receivedIp)
    {
        view.successFullConnect(port, receivedIp);
        System.out.println("SelectPresenter.onPortReceived");
    }
}

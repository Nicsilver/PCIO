package dk.nicsilver.pcio.action;

public interface ActionContract
{
    interface presenter
    {
        void onConnectionLost();
        
        void onConnectionRegained();
    }
    
    interface view
    {
        void onConnectionLost();
        
        void onConnectionRegained();
    }
}

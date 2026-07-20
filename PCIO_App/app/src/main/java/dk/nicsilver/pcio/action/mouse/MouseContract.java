package dk.nicsilver.pcio.action.mouse;

public interface MouseContract
{
    interface presenter
    {
        void onDownLeft(float x, float y);
        
        void onUpLeft(float x, float y);
        
        void onDownRight(float x, float y);
        
        void onMoveLeft(float x, float y);
        
        void onMoveRight(float x);
    }
    
    interface view
    {
        //These may or may not be used.

    }
}

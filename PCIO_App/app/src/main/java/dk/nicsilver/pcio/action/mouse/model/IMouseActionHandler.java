package dk.nicsilver.pcio.action.mouse.model;

public interface IMouseActionHandler
{
    void onDownLeft(float x, float y);
    
    void onUpLeft(float x, float y);
    
    void onDownRight(float x, float y);
    
    void onMoveLeft(float x, float y);
    
    void onMoveRight(float y);
}

package dk.nicsilver.pcio.action.mouse;

import dk.nicsilver.pcio.action.mouse.model.IMouseActionHandler;
import dk.nicsilver.pcio.action.mouse.model.MouseActionHandler;

public class MousePresenter implements MouseContract.presenter
{
    private IMouseActionHandler mouseActionHandler;
    private MouseContract.view view;
    
    public MousePresenter(MouseContract.view view)
    {
        this.view = view;
        this.mouseActionHandler = new MouseActionHandler();
    }
    
    @Override
    public void onDownLeft(float x, float y)
    {
        mouseActionHandler.onDownLeft(x, y);
    }
    
    @Override
    public void onUpLeft(float x, float y)
    {
        mouseActionHandler.onUpLeft(x, y);
    }
    
    @Override
    public void onDownRight(float x, float y)
    {
        mouseActionHandler.onDownRight(x, y);
    }
    
    @Override
    public void onMoveLeft(float x, float y)
    {
        mouseActionHandler.onMoveLeft(x, y);
    }
    
    @Override
    public void onMoveRight(float y)
    {
        mouseActionHandler.onMoveRight(y);
    }
}

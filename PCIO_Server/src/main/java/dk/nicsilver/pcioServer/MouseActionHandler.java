package dk.nicsilver.pcioServer;

import java.awt.*;
import java.awt.event.InputEvent;

public class MouseActionHandler
{
    private Robot robot;
    
    public MouseActionHandler()
    {
        try
        {
            robot = new Robot();
        } catch (AWTException e)
        {
            e.printStackTrace();
        }
    }
    
    public void executeAction(String received)
    {
        String action = received.substring(received.indexOf('#') + 1, received.indexOf('%'));
        String parametar = received.substring(received.indexOf('%') + 1);
//        System.out.println("MouseActionHandler.executeAction - action: " + action + ", parametar: " + parametar);
        
        switch (action)
        {
            case "OnMove":
                moveMouse(parametar);
                break;
            case "OnScroll":
                scroll(parametar);
                break;
            case "OnRightClick":
                rightClick();
                break;
            case "OnLeftClick":
                leftClick();
                break;
            case "OnLeftDown":
                leftDown();
                break;
            case "OnLeftUp":
                leftUp();
                break;
            default:
                System.out.println("MouseActionHandler.executeAction: " + "Unknown action: " + action);
                break;
        }
    }
    
    private void scroll(String amount)
    {
        float scrollAmount = Float.parseFloat(amount);
        robot.mouseWheel((int) scrollAmount);
    }
    
    private void rightClick()
    {
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }
    
    private void leftUp()
    {
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
    
    private void leftDown()
    {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    }
    
    private void moveMouse(String parameter)
    {
        double addX = Double.parseDouble(parameter.substring(0, parameter.indexOf('&')));
        double addY = Double.parseDouble(parameter.substring(parameter.indexOf('&') + 1));
        
        Point point = MouseInfo.getPointerInfo().getLocation();
        robot.mouseMove((int) (point.getX() + addX), (int) (point.getY() + addY));
    }
    
    private void leftClick()
    {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
}

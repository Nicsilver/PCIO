package dk.nicsilver.pcio.action.mouse.model;

public class MouseLibrary
{
    public static String onMove(float x, float y)
    {
        return appendParametersToAction(appendActionToHeader("OnMove"), x + "&" + y);
    }
    
    public static String onScroll(float amount)
    {
        return appendParametersToAction(appendActionToHeader("OnScroll"), Float.toString(amount));
    }
    
//    public static String onLeftClick()
//    {
//        return appendActionToHeader("OnLeftClick");
//    }
    
    public static String onLeftUp()
    {
        return appendActionToHeader("OnLeftUp");
    }
    public static String onLeftDown()
    {
        return appendActionToHeader("OnLeftDown");
    }
    
//    public static String onDrag()
//    {
//        return appendActionToHeader("OnDrag");
//    }
    
    private static String appendActionToHeader(String action)
    {
        return "PCIOMouseAction#" + action + "%";
    }
    
    private static String appendParametersToAction(String actionWithHeader, String parameters)
    {
        return actionWithHeader + parameters;
    }
    
    public static String onRightClick()
    {
        return appendActionToHeader("OnRightClick");
    }
}

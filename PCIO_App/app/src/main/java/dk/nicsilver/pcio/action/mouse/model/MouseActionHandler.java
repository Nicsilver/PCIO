package dk.nicsilver.pcio.action.mouse.model;

import dk.nicsilver.pcio.action.ActionSender;

public class MouseActionHandler implements IMouseActionHandler
{
    private ActionSender actionSender;
    private float lastXLeft, lastYLeft, lastYRight, travelYRight;
    private boolean leftDown, rightDown = false;
    private boolean moveThresholdLeftMet, moveThresholdRightMet = false;
    private boolean isDragging, isLeftClickAboutToHappen, isDoubleTap = false;
    private long onDownLeftActivatedAt = 0;
    private long onUpLeftActivatedAt = 0;
    
    private Thread onUpThread;
    private long onDoubleTapTime = 0;
    
    public MouseActionHandler()
    {
        this.actionSender = ActionSender.getInstance();
    }
    
    @Override
    public void onDownLeft(float x, float y)
    {
        
        onDownLeftActivatedAt = System.currentTimeMillis(); //This is used to determine if we have double taps
        
        if (isTimeElapsedLessThan(onUpLeftActivatedAt, 200))
        {
            System.out.println("MouseActionHandler.onDownLeft - fast enough");
            onDoubleTapTime = System.currentTimeMillis();
            isDoubleTap = true;
        }
        
        leftDown = true;
        moveThresholdLeftMet = false;
        lastXLeft = x;
        lastYLeft = y;
    }
    
    @Override
    public void onUpLeft(float x, float y)
    {
        if (!moveThresholdLeftMet)
        {
            if (isDoubleTap && isTimeElapsedLessThan(onDownLeftActivatedAt, 70))
            {
                actionSender.sendAction(MouseLibrary.onLeftUp());
                actionSender.sendAction(MouseLibrary.onLeftDown());
                actionSender.sendAction(MouseLibrary.onLeftUp());
            } else if (isTimeElapsedLessThan(onDownLeftActivatedAt, 200))
            {
                actionSender.sendAction(MouseLibrary.onLeftDown());
                isDragging = true;
                onUpThread = new Thread(() ->
                {
                    try
                    {
                        Thread.sleep(250);
                        if (!isDoubleTap)
                        {
                            actionSender.sendAction(MouseLibrary.onLeftUp());
                        }
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    isDoubleTap = false;
                });
                onUpThread.start();
            }
        }
        
        onUpLeftActivatedAt = System.currentTimeMillis();
        leftDown = false;
        rightDown = false;
    }
    
    private boolean isTimeElapsedLessThan(long time, long timeDifference)
    {
        return System.currentTimeMillis() - time < timeDifference;
    }
    
    @Override
    public void onDownRight(float x, float y)
    {
        rightDown = true;
        moveThresholdRightMet = false;
        lastYRight = y;
        travelYRight = 0;
    }
    
    @Override
    public void onMoveLeft(float x, float y)
    {
        float xDifferenceFromLast = calculateMoveDifference(x, lastXLeft);
        float yDifferenceFromLast = calculateMoveDifference(y, lastYLeft);
        
        if (!rightDown && moveThresholdLeftMet
                && (getMoveThreshold(xDifferenceFromLast, 1)
                || getMoveThreshold(yDifferenceFromLast, 1)))
        {
            actionSender.sendAction(MouseLibrary.onMove(xDifferenceFromLast, yDifferenceFromLast));
        } else if (getMoveThreshold(xDifferenceFromLast, 1)
                || getMoveThreshold(yDifferenceFromLast, 1))
        {
            moveThresholdLeftMet = true; // This is to filter out the first movement, it can sometimes be jerky if this is not done.
        }
        lastXLeft = x;
        lastYLeft = y;
    }
    
    private float calculateMoveDifference(float currentValue, float lastValue)
    {
        return currentValue - lastValue;
    }
    
    /**
     * Return true if value is more than threshold, either negative or positive.
     *
     * @param value      to test
     * @param threshHold to test on
     * @return true if larger than, threshold false if not
     */
    private boolean getMoveThreshold(float value, float threshHold)
    {
        return value > threshHold || value < -threshHold;
    }
    
    @Override
    public void onMoveRight(float y)
    {
        if (leftDown && rightDown)
        {
            float sendY = calculateMoveDifference(y, lastYRight);
            travelYRight += sendY;
            lastYRight = y;
            
            if (getMoveThreshold(travelYRight, 10))
            {
                actionSender.sendAction(MouseLibrary.onScroll(sendY / 2));
                travelYRight = 0;
                moveThresholdRightMet = true;
            }
        }
    }
}

package dk.nicsilver.pcioServer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class TrayIconHelper implements ActionListener
{
    private final TrayIconHelperCallback trayIconHelperCallback;
    
    private static final String EXIT = "Exit";
    private static final String SETTINGS = "Settings";
    
    public TrayIconHelper(TrayIconHelperCallback trayIconHelperCallback)
    {
        this.trayIconHelperCallback = trayIconHelperCallback;
        
        TrayIcon trayIcon;
        if (SystemTray.isSupported())
        {
            // get the SystemTray instance
            SystemTray tray = SystemTray.getSystemTray();
            
            // load an image
            URL imageTestString = getClass().getClassLoader().getResource("images/PCIO_ICON_PC_512.png");
            Image trayIconImage = Toolkit.getDefaultToolkit().getImage(imageTestString);
            
            // create a popup menu
            PopupMenu popup = new PopupMenu();
            // create menu item for the default action
            MenuItem exitItem = new MenuItem(EXIT);
            MenuItem settingsItem = new MenuItem(SETTINGS);
            
            exitItem.addActionListener(this);
            settingsItem.addActionListener(this);
            
            popup.add(settingsItem);
            popup.add(exitItem);
            
            // construct a TrayIcon
            trayIcon = new TrayIcon(trayIconImage, "PCIO", popup);
            trayIcon.setImageAutoSize(true);
            // set the TrayIcon properties
            trayIcon.addActionListener(this);
            // ...
            // add the tray icon
            try
            {
                tray.add(trayIcon);
            } catch (AWTException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String actionCommand = e.getActionCommand();
        
        switch (actionCommand)
        {
            case EXIT:
                trayIconHelperCallback.onExitCalled();
                break;
                
            case SETTINGS:
                new SettingsGUI("Settings").setVisible(true);
                break;
            //Show settings window.
        }
    }
}

package dk.nicsilver.pcioServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsGUI extends JFrame implements ActionListener
{
    private static final String SETTINGS = "Settings"; //Used for later if we get more actions
    private JPanel settingsPanel;
    private JTextField mainListenerPortText;
    private JTextField newConnectionListenerPortText;
    private JButton saveButton;
    private final PropertiesHelper propertiesHelper;
    
    public SettingsGUI(String title) throws HeadlessException
    {
        super(title);
        
        //TODO set look and feel, and maybe create custom look.
        this.propertiesHelper = PropertiesHelper.getInstance();
        
        this.mainListenerPortText.setText(propertiesHelper.getValue(PropertiesHelper.MAIN_PORT));
        this.newConnectionListenerPortText.setText(propertiesHelper.getValue(PropertiesHelper.CONNECTION_PORT));
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(settingsPanel);
        this.pack();
        
        saveButton.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String action = e.getActionCommand();
        if (action.equals("Save"))
        {
            String mainPort = this.mainListenerPortText.getText();
            String connectionPort = this.newConnectionListenerPortText.getText();
            
            if (isValidPort(mainPort) && isValidPort(connectionPort))
            {
                propertiesHelper.setValue(PropertiesHelper.MAIN_PORT, mainPort);
                propertiesHelper.setValue(PropertiesHelper.CONNECTION_PORT, connectionPort);
                propertiesHelper.commitValues();
            } else
            {
                JOptionPane.showMessageDialog(this,"Ports must be a number between 1024 - 65534!");
            }
            //Save ports to persistant file.
        }
    }
    
    private boolean isValidPort(String port)
    {
        try
        {
            int portInt = Integer.parseInt(port);
            if (portInt > 1023 && portInt < 65534)
            {
                return true;
            }
        } catch (NumberFormatException ignored)
        {
        
        }
        return false;
    }
}

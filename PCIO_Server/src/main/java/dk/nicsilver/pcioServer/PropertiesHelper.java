package dk.nicsilver.pcioServer;

import java.io.*;
import java.util.Objects;
import java.util.Properties;

public class PropertiesHelper implements IPropertiesHelper
{
    public final static String MAIN_PORT = "db.main_listener_port";
    public final static String CONNECTION_PORT = "db.connection_listener_port";
    
    private static PropertiesHelper instance;
    
    private String configPath;
    
    private final Properties properties;
    
    public static PropertiesHelper getInstance()
    {
        if (instance == null)
            instance = new PropertiesHelper();
        
        return instance;
    }
    
    private PropertiesHelper()
    {
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        if (rootPath != null)
        {
            this.configPath = rootPath + "config.properties"; // done like this to make adding more property files easier
        }
        this.properties = getProperties();
    }
    
    public String getValue(String key)
    {
        String value = "";
        try
        {
            value = this.properties.get(key).toString();
        } catch (NullPointerException e)
        {
            System.out.println("PropertiesHelper.getValue - key is null: " + key);
//            e.printStackTrace();
        }
        return value;
    }
    
    public void setValue(String key, String value)
    {
        this.properties.setProperty(key, value);
    }
    
    public boolean commitValues()
    {
        OutputStream outputStream = getOutputStream();
        if (outputStream != null)
        {
            try (outputStream)
            {
                this.properties.store(outputStream, null); //Null = no comments.
                return true;
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    /**
     * @return If successful will return filled Properties object, otherwise an empty Properties object
     */
    private Properties getProperties()
    {
        Properties properties = new Properties();
        InputStream inputStream = getInputStream();
        if (inputStream != null)
        {
            try
            {
                properties.load(inputStream);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return properties;
    }
    
    private InputStream getInputStream()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(configPath);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return inputStream;
    }
    
    private OutputStream getOutputStream()
    {
        OutputStream outputStream = null;
        try
        {
            outputStream = new FileOutputStream(configPath);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return outputStream;
    }
}

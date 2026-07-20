package dk.nicsilver.pcio;

import android.app.Application;

import com.nicsilver.pcio.BuildConfig;

import org.jetbrains.annotations.NotNull;

import java.util.Timer;

import timber.log.Timber;

public class ApplicationController extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        if (BuildConfig.DEBUG)
        {
//            Timber.plant(new Timber.DebugTree());
            Timber.plant(new Timber.DebugTree()
            {
                //Change the tag returned so it shows class and method.
                @Override
                protected String createStackElementTag(@NotNull StackTraceElement element)
                {
                    return String.format("%s.%s",
                            super.createStackElementTag(element),
                            element.getMethodName()
                    );
                }
            });
        }
    }
}

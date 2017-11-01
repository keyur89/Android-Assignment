package com.test.assignment.Utils;

import com.test.assignment.BuildConfig;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Keyur Tailor on 01-Nov-17.
 */

public class Injector {

    private static EventBus eventBus;

    public static EventBus provideEventBus ()
    {
        if ( eventBus == null )
        {
            eventBus = EventBus.builder()
                    .logNoSubscriberMessages( BuildConfig.DEBUG )
                    .sendNoSubscriberEvent( BuildConfig.DEBUG )
                    .build();
        }
        return eventBus;
    }
}

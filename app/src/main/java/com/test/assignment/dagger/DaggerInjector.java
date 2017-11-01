package com.test.assignment.dagger;


import com.test.assignment.dagger.components.AppComponent;
import com.test.assignment.dagger.components.DaggerAppComponent;
import com.test.assignment.dagger.modules.AppModule;

/**
 * Created by Keyur Tailor on 01-Nov-17.
 */


public class DaggerInjector {
    private static AppComponent appComponent =
            DaggerAppComponent.builder().appModule(new AppModule()).build();


    public static AppComponent get() {
        return appComponent;
    }
}
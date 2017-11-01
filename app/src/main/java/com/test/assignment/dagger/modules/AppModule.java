package com.test.assignment.dagger.modules;


import com.test.assignment.ApiManager.ApiManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Created by Keyur Tailor on 01-Nov-17.
 */


@Module
public class AppModule {

    @Provides
    @Singleton
    ApiManager provideApiManager() {
        return new ApiManager();
    }

}

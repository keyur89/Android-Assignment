package com.test.assignment.dagger.components;


import com.test.assignment.dagger.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Keyur Tailor on 01-Nov-17.
 */


@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

}

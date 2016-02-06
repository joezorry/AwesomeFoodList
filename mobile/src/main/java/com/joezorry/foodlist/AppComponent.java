package com.joezorry.foodlist;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { AppModule.class })
public interface AppComponent {
    void inject(MainActivity mainActivity);
}

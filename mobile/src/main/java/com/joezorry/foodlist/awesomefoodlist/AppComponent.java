package com.joezorry.foodlist.awesomefoodlist;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { AppModule.class })
public interface AppComponent {
}

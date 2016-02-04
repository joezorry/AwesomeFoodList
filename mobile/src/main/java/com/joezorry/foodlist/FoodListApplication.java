package com.joezorry.foodlist;

import android.app.Application;

public class FoodListApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                                          .appModule(new AppModule())
                                          .build();
    }
}

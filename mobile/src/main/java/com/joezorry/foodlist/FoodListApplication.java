package com.joezorry.foodlist;

import android.app.Application;

import com.joezorry.foodlist.awesomefoodlist.DaggerAppComponent;

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

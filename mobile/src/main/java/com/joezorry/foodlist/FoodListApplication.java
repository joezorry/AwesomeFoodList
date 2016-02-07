package com.joezorry.foodlist;

import android.app.Application;
import android.content.Context;

public class FoodListApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                                          .appModule(new AppModule(this))
                                          .build();
    }

    public static AppComponent getAppComponent(final Context context) {
        final FoodListApplication foodListApplication = (FoodListApplication) context.getApplicationContext();
        return foodListApplication.mAppComponent;
    }
}

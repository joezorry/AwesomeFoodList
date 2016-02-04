package com.joezorry.foodlist;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.JacksonConverterFactory;
import retrofit2.Retrofit;

@Module
public class AppModule {

    public AppModule() {}

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create())
            .baseUrl("BASE_URL")
            .build();
    }
}

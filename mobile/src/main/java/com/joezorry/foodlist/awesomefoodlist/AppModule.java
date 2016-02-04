package com.joezorry.foodlist.awesomefoodlist;

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

//
//    @Singleton
//    @Provides
//    public OrmaDatabase provideOrmaDatabase(Context context) {
//        return OrmaDatabase.builder(context)
//                           .build();
//    }
}

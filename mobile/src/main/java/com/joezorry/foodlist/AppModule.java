package com.joezorry.foodlist;

import android.content.Context;

import com.joezorry.foodlist.models.OrmaDatabase;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.JacksonConverterFactory;
import retrofit2.Retrofit;

@Module
public class AppModule {

    public AppModule() {}

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(final Chain chain) throws IOException {
                final Request request =
                    chain.request()
                         .newBuilder()
                         .addHeader(
                             "Authorization",
                             BuildConfig.AUTH_TOKEN)
                         .build();
                return chain.proceed(request);
            }
        });

        return new Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create())
            .baseUrl("https://api.lifesum.com/icebox/v1/foods/en/se/Orange")
            .client(httpClient)
        .build();
    }


    @Singleton
    @Provides
    public OrmaDatabase provideOrmaDatabase(Context context) {
        return OrmaDatabase.builder(context)
                           .build();
    }
}

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

    private final Context mContext;

    public AppModule(final Context context) {
        mContext = context;
    }

    @Provides
    public Context provideContext() {
        return mContext;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        final OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(
            new Interceptor() {
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
        }).build();

        return new Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create())
            .baseUrl("https://api.lifesum.com/")
            .client(httpClient)
        .build();
    }


    @Singleton
    @Provides
    public OrmaDatabase provideOrmaDatabase() {
        return OrmaDatabase.builder(mContext)
                           .build();
    }
}

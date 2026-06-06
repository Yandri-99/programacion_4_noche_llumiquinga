// di/NetworkModule.kt
package com.transportapp.di

import com.transportapp.BuildConfig
import com.transportapp.data.local.TokenDataStore
import com.transportapp.data.remote.api.*
import com.transportapp.data.remote.interceptor.AuthInterceptor
import com.transportapp.data.remote.interceptor.BearerTokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides @Singleton
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides @Singleton
    fun provideOkHttpClient(
        tokenDataStore: TokenDataStore,
        authInterceptor: AuthInterceptor,
        logging: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .authenticator(authInterceptor)
        .addInterceptor(BearerTokenInterceptor(tokenDataStore))
        .addInterceptor(logging)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides @Singleton
    fun provideRouteApi(retrofit: Retrofit): RouteApi =
        retrofit.create(RouteApi::class.java)

    @Provides @Singleton
    fun provideVehicleApi(retrofit: Retrofit): VehicleApi =
        retrofit.create(VehicleApi::class.java)

    @Provides @Singleton
    fun provideTripApi(retrofit: Retrofit): TripApi =
        retrofit.create(TripApi::class.java)

    @Provides @Singleton
    fun provideDriverApi(retrofit: Retrofit): DriverApi =
        retrofit.create(DriverApi::class.java)
}

package com.harunbekcan.roomdatabasewithpaging3project.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.harunbekcan.roomdatabasewithpaging3project.R
import com.harunbekcan.roomdatabasewithpaging3project.data.api.ServiceInterface
import com.harunbekcan.roomdatabasewithpaging3project.data.database.PopularTvDatabase
import com.harunbekcan.roomdatabasewithpaging3project.utils.Constant.BASE_URL
import com.harunbekcan.roomdatabasewithpaging3project.utils.CustomHttpLogger
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi()))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun moshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()!!

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient{
        val logging = HttpLoggingInterceptor(CustomHttpLogger())
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BASIC)
        }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): ServiceInterface {
        return retrofit.create(ServiceInterface::class.java)
    }

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide
        .with(context).setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
        )

    @Provides
    @Singleton
    fun providesPopularTvDatabase(context: Application) =
        Room.databaseBuilder(context,PopularTvDatabase::class.java,"popularTvDatabase")
            .build()

    @Provides
    @Singleton
    fun providesPopularTvDao(popularTvDatabase: PopularTvDatabase) =
        popularTvDatabase.getPopularTvDao()

}
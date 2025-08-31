package com.example.step5app.di

import com.example.step5app.data.remote.AffiliateService
import com.example.step5app.data.remote.AuthService
import com.example.step5app.data.remote.FeedService
import com.example.step5app.data.remote.PlanService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://31.97.156.169:4000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun providePlanService(retrofit: Retrofit): PlanService {
        return retrofit.create(PlanService::class.java)
    }

    @Provides
    @Singleton
    fun provideFeedService(retrofit: Retrofit): FeedService {
        return retrofit.create(FeedService::class.java)
    }

    @Provides
    @Singleton
    fun provideAffiliateService(retrofit: Retrofit): AffiliateService {
        return retrofit.create(AffiliateService::class.java)
    }

}
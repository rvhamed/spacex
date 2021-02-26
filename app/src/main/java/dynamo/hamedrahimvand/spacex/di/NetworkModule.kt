package dynamo.hamedrahimvand.spacex.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dynamo.hamedrahimvand.spacex.BuildConfig
import dynamo.hamedrahimvand.spacex.data.repository.remote.Apis
import dynamo.hamedrahimvand.spacex.data.repository.remote.retrofit.FlowCallAdapterFactory
import dynamo.hamedrahimvand.spacex.data.repository.remote.retrofit.FlowResponseBodyConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/26/21
 */
@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideOkHttp(
        loggerInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()

        //Logger has to just show logs in debug mode
        if (BuildConfig.DEBUG)
            builder.addInterceptor(loggerInterceptor)

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        flowCallAdapterFactory: FlowCallAdapterFactory,
        flowResponseBodyConverterFactory: FlowResponseBodyConverterFactory,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(flowCallAdapterFactory)
            .addConverterFactory(flowResponseBodyConverterFactory)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(GsonBuilder().create())

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): Apis = retrofit.create(Apis::class.java)
}
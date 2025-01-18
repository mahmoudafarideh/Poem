package m.a.nobahar.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Logger
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

const val WEB_URL = "https://api.ganjoor.net"
const val API_URL = "https://nobahar.app/api/"

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private fun provideRetrofitBuilder(
        okHttpClient: OkHttpClient,
        converter: Converter.Factory
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .client(okHttpClient)
            .addConverterFactory(converter)
    }

    @Provides
    fun providesRetrofitBuilder(okHttpClient: OkHttpClient, converter: Converter.Factory) =
        provideRetrofitBuilder(okHttpClient, converter)

    @Provides
    fun providesConverterFactory(
        json: Json
    ): Converter.Factory = json.asConverterFactory("application/json".toMediaType())

    @Provides
    fun provideOkHttpInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            var original = chain.request()
            val request: Request.Builder = original.newBuilder()
            request.addHeader("Accept", "application/json")
            original = request.build()
            val originalHttpUrl = original.url
            val url = originalHttpUrl.newBuilder()
                .build()
            val requestBuilder: Request.Builder = original.newBuilder().url(url)
            val requestFinal: Request = requestBuilder.build()
            chain.proceed(requestFinal)
        }
    }

    @Provides
    fun providesOkHttpClient(
        interceptor: Interceptor,
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            followSslRedirects(true)
            addInterceptor(interceptor)
            addInterceptor(ChuckerInterceptor(context))
            addInterceptor(HttpLoggingInterceptor(Logger.DEFAULT).also {
                it.setLevel(HttpLoggingInterceptor.Level.BODY)
            })
        }.build()
    }

    @Provides
    fun provideCoroutineCallAdapterFactory(): CallAdapter.Factory {
        return CoroutineCallAdapterFactory()
    }

    private fun provideRetrofit(
        callAdapterFactory: CallAdapter.Factory,
        retrofitBuilder: Retrofit.Builder
    ): Retrofit {
        return retrofitBuilder
            .addCallAdapterFactory(callAdapterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(
        callAdapterFactory: CallAdapter.Factory,
        retrofitBuilder: Retrofit.Builder
    ): Retrofit {
        return provideRetrofit(callAdapterFactory, retrofitBuilder)
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideJson() = Json {
        this.ignoreUnknownKeys = true
        this.allowComments = true
        this.explicitNulls = false
        this.coerceInputValues = true
    }

}
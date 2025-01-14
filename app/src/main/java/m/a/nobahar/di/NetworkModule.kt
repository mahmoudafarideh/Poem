package m.a.nobahar.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.a.nobahar.api.contract.BookApi
import m.a.nobahar.api.contract.CenturyApi
import m.a.nobahar.api.contract.PoemApi
import m.a.nobahar.api.contract.PoetApi
import m.a.nobahar.api.contract.RandomApi
import m.a.nobahar.api.contract.SearchApi
import m.a.nobahar.api.contract.SplashApi
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun providesCenturyApi(retrofit: Retrofit): CenturyApi = retrofit.create(CenturyApi::class.java)

    @Provides
    fun providesPoetApi(retrofit: Retrofit): PoetApi = retrofit.create(PoetApi::class.java)

    @Provides
    fun providesSearchApi(retrofit: Retrofit): SearchApi = retrofit.create(SearchApi::class.java)

    @Provides
    fun providesRandomApi(retrofit: Retrofit): RandomApi = retrofit.create(RandomApi::class.java)

    @Provides
    fun providesPoemApi(retrofit: Retrofit): PoemApi = retrofit.create(PoemApi::class.java)

    @Provides
    fun providesBookApi(retrofit: Retrofit): BookApi = retrofit.create(BookApi::class.java)

    @Provides
    fun providesSplashApi(retrofit: Retrofit): SplashApi = retrofit.create(SplashApi::class.java)


}
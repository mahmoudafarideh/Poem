package m.a.poem.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.a.poem.api.contract.BookApi
import m.a.poem.api.contract.CenturyApi
import m.a.poem.api.contract.PoemApi
import m.a.poem.api.contract.PoetApi
import m.a.poem.api.contract.RandomApi
import m.a.poem.api.contract.SearchApi
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


}
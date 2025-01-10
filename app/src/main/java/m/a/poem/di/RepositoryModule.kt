package m.a.poem.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.a.poem.api.repository.BookRepositoryImp
import m.a.poem.api.repository.CenturyPoetsRepositoryImp
import m.a.poem.api.repository.HomeCommunicationRepositoryImp
import m.a.poem.api.repository.MediaPlayerRepositoryImp
import m.a.poem.api.repository.PoemRepositoryImp
import m.a.poem.api.repository.PoemSearchRepositoryImp
import m.a.poem.api.repository.PoetRepositoryImp
import m.a.poem.api.repository.RandomRepositoryImp
import m.a.poem.api.repository.SplashRepositoryImp
import m.a.poem.domain.repository.BookRepository
import m.a.poem.domain.repository.CenturyPoetsRepository
import m.a.poem.domain.repository.HomeCommunicationRepository
import m.a.poem.domain.repository.MediaPlayerRepository
import m.a.poem.domain.repository.PoemRepository
import m.a.poem.domain.repository.PoetRepository
import m.a.poem.domain.repository.RandomRepository
import m.a.poem.domain.repository.SearchRepository
import m.a.poem.domain.repository.SplashRepository


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun providesCenturyPoetsRepository(
        centuryPoetsRepository: CenturyPoetsRepositoryImp
    ): CenturyPoetsRepository

    @Binds
    fun providesPoetRepository(repo: PoetRepositoryImp): PoetRepository

    @Binds
    fun providesMediaPlayerRepository(repo: MediaPlayerRepositoryImp): MediaPlayerRepository

    @Binds
    fun providesSearchRepository(repo: PoemSearchRepositoryImp): SearchRepository

    @Binds
    fun providesRandomRepository(repo: RandomRepositoryImp): RandomRepository

    @Binds
    fun providesPoemRepository(repo: PoemRepositoryImp): PoemRepository

    @Binds
    fun providesBookRepository(repo: BookRepositoryImp): BookRepository

    @Binds
    fun providesSplashRepository(repo: SplashRepositoryImp): SplashRepository

    @Binds
    fun providesHomeCommunicationRepository(repo: HomeCommunicationRepositoryImp): HomeCommunicationRepository

}
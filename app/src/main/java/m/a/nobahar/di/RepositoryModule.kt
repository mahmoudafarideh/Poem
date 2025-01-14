package m.a.nobahar.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.a.nobahar.api.repository.BookRepositoryImp
import m.a.nobahar.api.repository.CenturyPoetsRepositoryImp
import m.a.nobahar.api.repository.HomeCommunicationRepositoryImp
import m.a.nobahar.api.repository.MediaPlayerRepositoryImp
import m.a.nobahar.api.repository.PoemRepositoryImp
import m.a.nobahar.api.repository.PoemSearchRepositoryImp
import m.a.nobahar.api.repository.PoetRepositoryImp
import m.a.nobahar.api.repository.RandomRepositoryImp
import m.a.nobahar.api.repository.SplashRepositoryImp
import m.a.nobahar.domain.repository.BookRepository
import m.a.nobahar.domain.repository.CenturyPoetsRepository
import m.a.nobahar.domain.repository.HomeCommunicationRepository
import m.a.nobahar.domain.repository.MediaPlayerRepository
import m.a.nobahar.domain.repository.PoemRepository
import m.a.nobahar.domain.repository.PoetRepository
import m.a.nobahar.domain.repository.RandomRepository
import m.a.nobahar.domain.repository.SearchRepository
import m.a.nobahar.domain.repository.SplashRepository


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
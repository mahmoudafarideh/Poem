package m.a.nobahar.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import m.a.nobahar.ui.book.screen.BookViewModel
import m.a.nobahar.ui.poem.screen.PoemViewModel
import m.a.nobahar.ui.poet.screen.PoetViewModel
import m.a.nobahar.ui.search.screen.SearchViewModel


@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {

    fun poetViewModelFactory(): PoetViewModel.Factory
    fun bookViewModelFactory(): BookViewModel.Factory
    fun poemViewModelFactory(): PoemViewModel.Factory
    fun searchViewModelFactory(): SearchViewModel.Factory

}
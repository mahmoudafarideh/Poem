package m.a.poem.ui.poet.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.toImmutableList
import m.a.poem.domain.model.Loading
import m.a.poem.domain.model.PoetDetails
import m.a.poem.domain.repository.PoetRepository
import m.a.poem.ui.poet.model.PoetBioUiModel
import m.a.poem.ui.poet.model.PoetBooksUiModel
import m.a.poem.ui.poet.model.PoetScreenTabsUiModel
import m.a.poem.ui.poet.model.PoetScreenUiModel
import m.a.poem.ui.poet.model.PoetScreenUiModel.PoetInfo
import m.a.poem.ui.shared.BaseViewModel
import m.a.poem.ui.shared.model.PoetUiModel

class PoetViewModel @AssistedInject constructor(
    @Assisted private val poetUiModel: PoetUiModel,
    private val poetRepository: PoetRepository
) : BaseViewModel<PoetScreenUiModel>(PoetScreenUiModel(poetUiModel)) {

    init {
        getPoetDetails()
    }

    fun tabClicked(tab: PoetScreenTabsUiModel) {
        if (state.value.poetInfo is Loading) return
        updateState {
            copy(selectedTabsUiModel = tab)
        }
    }

    fun retryClicked() {
        getPoetDetails()
    }

    private fun getPoetDetails() {
        executeLoadable(
            currentValue = state.value.poetInfo,
            action = {
                poetRepository.getPoet(poetUiModel.id).toPoetUiModel()
            },
            data = {
                updateState { copy(poetInfo = it) }
            }
        )
    }

    private fun PoetDetails.toPoetUiModel() = PoetInfo(
        poetBioUiModel = PoetBioUiModel(
            bio.text,
            bio.bornCity,
        ),
        poetBooks = books.map {
            PoetBooksUiModel(it.id, it.label)
        }.toImmutableList()
    )


    @AssistedFactory
    interface Factory {
        fun create(poetUiModel: PoetUiModel): PoetViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            poetUiModel: PoetUiModel,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(poetUiModel) as T
            }
        }
    }
}
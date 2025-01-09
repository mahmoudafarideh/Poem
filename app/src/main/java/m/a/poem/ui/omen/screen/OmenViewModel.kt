package m.a.poem.ui.omen.screen

import dagger.hilt.android.lifecycle.HiltViewModel
import m.a.poem.domain.model.LoadableData
import m.a.poem.domain.model.NotLoaded
import m.a.poem.domain.repository.RandomRepository
import m.a.poem.ui.omen.model.OmenUiModel
import m.a.poem.ui.shared.BaseViewModel
import m.a.poem.ui.toPoetUiModel
import javax.inject.Inject

@HiltViewModel
class OmenViewModel @Inject constructor(
    private val randomRepository: RandomRepository
) : BaseViewModel<LoadableData<OmenUiModel>>(NotLoaded) {

    private fun getOmenPoem() {
        executeLoadable(
            currentValue = state.value,
            action = {
                val poem = randomRepository.getOmenPoem()
                OmenUiModel(
                    poetUiModel = poem.poet.toPoetUiModel(),
                    poemId = poem.id
                )
            },
            data = {
                updateState { it }
            }
        )
    }

    fun retryClicked() {
        getOmenPoem()
    }

    fun errorDismissed() {
        updateState {
            NotLoaded
        }
    }

    fun omenClicked() {
        getOmenPoem()
    }

    fun navigatedToPoemScreen() {
        updateState { NotLoaded }
    }
}
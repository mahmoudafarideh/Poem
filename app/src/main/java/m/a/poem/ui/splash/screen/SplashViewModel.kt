package m.a.poem.ui.splash.screen

import dagger.hilt.android.lifecycle.HiltViewModel
import m.a.poem.domain.model.LoadableData
import m.a.poem.domain.model.NotLoaded
import m.a.poem.domain.repository.SplashRepository
import m.a.poem.ui.shared.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val splashRepository: SplashRepository
) : BaseViewModel<LoadableData<Unit>>(NotLoaded) {

    init {
        getSplash()
    }

    fun retryClicked() {
        getSplash()
    }

    private fun getSplash() {
        executeLoadable(
            currentValue = state.value,
            action = {
                splashRepository.getSplash()
            },
            data = {
                updateState { it }
            }
        )
    }
}
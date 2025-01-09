package m.a.poem.ui.widget.viewmodel

import android.icu.util.Calendar
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import m.a.poem.domain.model.Loaded
import m.a.poem.domain.model.NotLoaded
import m.a.poem.domain.model.RandomPoem
import m.a.poem.domain.repository.RandomRepository
import m.a.poem.ui.shared.BaseViewModel
import m.a.poem.ui.widget.model.WidgetPoemVerseUiModel
import m.a.poem.ui.widget.model.WidgetUiModel
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class PoemWidgetViewModel @Inject constructor(
    private val randomRepository: RandomRepository
) : BaseViewModel<WidgetUiModel>(WidgetUiModel()) {

    private var _currentPoem: RandomPoem? = null
    val currentPoem get() = _currentPoem
    private var currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    init {
        getRandomVerse()
        observeShouldUpdatePoem()
    }

    private fun observeShouldUpdatePoem() {
        viewModelScope.launch {
            while (true) {
                if (currentDate != Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
                    refreshClicked()
                }
                delay(360000)
            }
        }
    }

    private fun getRandomVerse() {
        if (state.value.poemVerse is Loaded) return
        executeLoadable(
            currentValue = state.value.poemVerse,
            action = {
                val poem = randomRepository.getRandomPoem()
                _currentPoem = poem
                val randomIndex = Random.nextInt(0, poem.verses.lastIndex - 1)
                WidgetPoemVerseUiModel(
                    firstVerse = poem.verses[randomIndex].text,
                    secondVerse = poem.verses[randomIndex + 1].text,
                    poet = poem.poet.nickName,
                    book = poem.book.label,
                )
            },
            data = {
                updateState {
                    copy(poemVerse = it)
                }
            }
        )
    }

    fun retryClicked() {
        getRandomVerse()
    }

    fun refreshClicked() {
        if (state.value.poemVerse !is Loaded) return
        updateState {
            copy(poemVerse = NotLoaded)
        }
        getRandomVerse()
    }
}
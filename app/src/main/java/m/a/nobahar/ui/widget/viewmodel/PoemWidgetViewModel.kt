package m.a.nobahar.ui.widget.viewmodel

import android.icu.util.Calendar
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import m.a.nobahar.domain.model.Loaded
import m.a.nobahar.domain.model.NotLoaded
import m.a.nobahar.domain.model.RandomPoem
import m.a.nobahar.domain.repository.RandomRepository
import m.a.nobahar.ui.shared.BaseViewModel
import m.a.nobahar.ui.widget.model.WidgetPoemVerseUiModel
import m.a.nobahar.ui.widget.model.WidgetUiModel
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
                delay(getMillisUntilNextDay())
            }
        }
    }

    fun getMillisUntilNextDay(): Long {
        val now = Calendar.getInstance()
        val nextDay = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        return nextDay.timeInMillis - now.timeInMillis
    }

    private fun getRandomVerse() {
        if (state.value.poemVerse is Loaded) return
        executeLoadable(
            currentValue = state.value.poemVerse,
            action = {
                val poem = randomRepository.getRandomPoem()
                _currentPoem = poem
                val randomIndex = getRandomIndex(poem)
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

    private fun getRandomIndex(poem: RandomPoem): Int =
        Random.nextInt(0, poem.verses.lastIndex - 1).let {
            if (it % 2 == 1) it - 1
            else it
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
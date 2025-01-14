package m.a.nobahar.ui.artwork.screen

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import m.a.nobahar.storage.BitmapSaver
import m.a.nobahar.ui.artwork.model.ArtFontUiModel
import m.a.nobahar.ui.artwork.model.ArtSavingState
import m.a.nobahar.ui.artwork.model.ArtScreenUiModel
import m.a.nobahar.ui.artwork.model.ArtTabUiModel
import m.a.nobahar.ui.shared.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ArtworkViewModel @Inject constructor(
    private val bitmapSaver: BitmapSaver
) :
    BaseViewModel<ArtScreenUiModel>(ArtScreenUiModel.default) {

    fun tabClicked(tab: ArtTabUiModel.Tab) {
        updateState {
            copy(
                tabs = tabs.map {
                    it.copy(selected = tab == it.tab)
                }.toImmutableList()
            )
        }
    }

    fun fontClicked(font: ArtFontUiModel.Font) {
        updateState {
            copy(
                fonts = fonts.map {
                    it.copy(selected = font == it.font)
                }.toImmutableList()
            )
        }
    }

    fun fontSizeChanged(size: Int) {
        Log.d("SXO", "fontSizeChanged: $size")
        updateState {
            copy(
                fontSizes = fontSizes.map {
                    it.copy(selected = it.size.progress == size )
                }.toImmutableList()
            )
        }
    }

    fun colorClicked(color: Color) {
        updateState {
            copy(
                colors = colors.map {
                    it.copy(selected = color == it.color)
                }.toImmutableList()
            )
        }
    }

    fun backgroundClicked(id: Int) {
        updateState {
            copy(
                backgrounds = backgrounds.map {
                    it.copy(selected = id == it.image)
                }.toImmutableList()
            )
        }
    }

    fun saveButtonClicked() {
        updateState {
            copy(savingState = ArtSavingState.Saving)
        }
    }

    fun savingFailed() {
        updateState {
            copy(savingState = ArtSavingState.None)
        }
    }

    fun bitmapLoaded(bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                bitmapSaver.savePhoto(bitmap)
            }.onFailure {
                updateState {
                    copy(savingState = ArtSavingState.Failed)
                }
            }.onSuccess {
                updateState {
                    copy(savingState = ArtSavingState.Saved)
                }
            }
        }
    }

}
package m.a.nobahar.di

import android.content.Context
import android.media.MediaPlayer
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MediaPlayerModule {

    @Provides
    fun provideMediaPlayer() = MediaPlayer()

    @Provides
    fun provideExoPlayer(
        @ApplicationContext context: Context
    ) = ExoPlayer.Builder(context).build()

}
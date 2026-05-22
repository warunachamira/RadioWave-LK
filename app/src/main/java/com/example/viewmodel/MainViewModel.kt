package com.example.viewmodel

import android.content.ComponentName
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.data.StationRepository
import com.example.model.Station
import com.example.service.RadioPlayerService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: StationRepository,
    private val context: Context
) : ViewModel() {

    val stations = repository.getAllStations()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _currentStation = MutableStateFlow<Station?>(null)
    val currentStation = _currentStation.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var player: MediaController? = null

    init {
        initializePlayer()
    }

    private fun initializePlayer() {
        val sessionToken = SessionToken(context, ComponentName(context, RadioPlayerService::class.java))
        val factory = MediaController.Builder(context, sessionToken).buildAsync()

        factory.addListener(
            {
                player = factory.get()
                player?.addListener(object : Player.Listener {
                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        _isPlaying.value = isPlaying
                        if (isPlaying) _isLoading.value = false
                    }

                    override fun onPlaybackStateChanged(playbackState: Int) {
                        _isLoading.value = playbackState == Player.STATE_BUFFERING
                        if (playbackState == Player.STATE_READY) {
                            _isLoading.value = false
                        }
                    }

                    override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                        super.onPlayerError(error)
                        _isLoading.value = false
                        _isPlaying.value = false
                        android.util.Log.e("RadioPlayer", "Playback Error", error)
                    }
                })
            },
            ContextCompat.getMainExecutor(context)
        )
    }

    fun playStation(station: Station) {
        _currentStation.value = station
        _isLoading.value = true
        player?.let {
            val mediaMetadata = MediaMetadata.Builder()
                .setTitle(station.name)
                .setArtist(station.category)
                .setArtworkUri(android.net.Uri.parse(station.logoUrl))
                .build()

            val mediaItem = MediaItem.Builder()
                .setMediaId(station.id)
                .setUri(station.streamUrl)
                .setMediaMetadata(mediaMetadata)
                .build()

            it.setMediaItem(mediaItem)
            it.prepare()
            it.play()
        }
    }

    fun togglePlayPause() {
        player?.let {
            if (it.playbackState == Player.STATE_IDLE || it.playerError != null) {
                it.prepare()
            }
            if (it.isPlaying) {
                it.pause()
            } else {
                it.play()
            }
        }
    }

    fun stopPlayer() {
        player?.stop()
    }

    override fun onCleared() {
        super.onCleared()
        player?.release()
    }

    fun toggleFavorite(station: Station) {
        viewModelScope.launch {
            repository.toggleFavorite(station.id, station.isFavorite)
        }
    }

    class Factory(private val repository: StationRepository, private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository, context) as T
        }
    }
}

package com.bignerdranch.android.starwars.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.starwars.domain.entities.MovieDetails
import com.bignerdranch.android.starwars.domain.usecases.GetDetailMovieUseCase
import com.bignerdranch.android.starwars.domain.usecases.GetVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getDetailMovieUseCase: GetDetailMovieUseCase,
    private val getVideoUseCase: GetVideoUseCase,
) : ViewModel() {

    private var disposable: Disposable? = null
    private var disposable2: Disposable? = null

    private val _movieLiveData = MutableLiveData<MovieDetails>()
    val movieLiveData: LiveData<MovieDetails> = _movieLiveData

    private val _errorLiveData = MutableLiveData<Throwable>()

    private val _errorVideoLiveData = MutableLiveData<String>()

    private val _videoLiveData = MutableLiveData<List<String>>()
    val videoLiveData: LiveData<List<String>> = _videoLiveData

    fun getVideo(id: Int?) {
        id?.let {
            disposable2 = getVideoUseCase.getVideo(id)
                .subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ video ->
                    if (video.results.isNotEmpty()) {
                        _videoLiveData.value = video.results.map { it.key }
                    }
                    _errorVideoLiveData.value = "No Video"
                }, {
                    _errorVideoLiveData.value = "Error Video"
                })
        }
    }

    fun getMovieDetail(id: Int?) {
        id?.let {
            disposable = getDetailMovieUseCase.getDetailMovie(id)
                .subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ movieDetails ->
                    _movieLiveData.value = movieDetails
                },
                    {
                        _errorLiveData.value = it
                    })
        }
    }
}
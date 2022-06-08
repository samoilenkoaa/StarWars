package com.bignerdranch.android.starwars.ui.crew
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
class CrewViewModel @Inject constructor(
    private val getDetailMovieUseCase: GetDetailMovieUseCase,
) : ViewModel() {

    private var disposable: Disposable? = null

    private val _movieLiveData = MutableLiveData<MovieDetails>()
    val movieLiveData: LiveData<MovieDetails> = _movieLiveData

    private val _errorLiveData = MutableLiveData<Throwable>()
    val errorLiveData: LiveData<Throwable> = _errorLiveData


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
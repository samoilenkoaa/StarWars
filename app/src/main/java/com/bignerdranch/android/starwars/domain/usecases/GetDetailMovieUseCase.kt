package com.bignerdranch.android.starwars.domain.usecases
import android.util.Log
import com.bignerdranch.android.starwars.domain.entities.MovieDetails
import com.bignerdranch.android.starwars.domain.repos.MovieRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetDetailMovieUseCase @Inject constructor(private val repository: MovieRepository) {

    fun getDetailMovie(id: Int): Single<MovieDetails> {
        return repository.getDetailMovie(id)
        Log.e("tagtag", repository.getDetailMovie(id).toString())
    }
}
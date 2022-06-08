package com.bignerdranch.android.starwars.domain.usecases
import com.bignerdranch.android.starwars.datalayer.repos.MovieRepositoryImpl
import com.bignerdranch.android.starwars.domain.entities.Movie
import com.bignerdranch.android.starwars.domain.entities.MovieDetails
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class AddBdMovieUseCase @Inject constructor(private val movieRepositoryImpl: MovieRepositoryImpl) {

    fun insertMovie(movie: List<Movie>): Completable {
        return movieRepositoryImpl.insertMovie(movie)
    }
}
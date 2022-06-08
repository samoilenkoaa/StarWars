package com.bignerdranch.android.starwars.domain.usecases
import com.bignerdranch.android.starwars.datalayer.repos.MovieRepositoryImpl
import com.bignerdranch.android.starwars.domain.entities.Movie
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetAllBdMoviesUseCase @Inject constructor(private val movieRepositoryImpl: MovieRepositoryImpl) {

    fun getAllBdMovies(): Single<List<Movie>> {
        return movieRepositoryImpl.getBdMovieList()
    }
}
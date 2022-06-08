package com.bignerdranch.android.starwars.domain.usecases
import com.bignerdranch.android.starwars.domain.entities.Movie
import com.bignerdranch.android.starwars.domain.repos.MovieRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetMovieListUseCase @Inject constructor(private val repository: MovieRepository) {

    fun getPopularMovieList(): Single<List<Movie>> {
        return repository.getPopularMovieList()
    }
}

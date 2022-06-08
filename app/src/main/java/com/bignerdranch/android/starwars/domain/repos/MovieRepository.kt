package com.bignerdranch.android.starwars.domain.repos
import com.bignerdranch.android.starwars.datalayer.remote.dto.video.VideoResponse
import com.bignerdranch.android.starwars.domain.entities.Movie
import com.bignerdranch.android.starwars.domain.entities.MovieDetails
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface MovieRepository {

    fun getPopularMovieList(): Single<List<Movie>>

    fun getVideo(id: Int): Single<VideoResponse>

    fun getDetailMovie(id: Int): Single<MovieDetails>

    fun insertMovie(movie: List<Movie>): Completable

    fun getBdMovieList(): Single<List<Movie>>
}
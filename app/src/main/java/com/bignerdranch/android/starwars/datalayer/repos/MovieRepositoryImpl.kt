package com.bignerdranch.android.starwars.datalayer.repos
import com.bignerdranch.android.starwars.datalayer.remote.RemoteDataSource
import com.bignerdranch.android.starwars.datalayer.remote.dto.video.VideoResponse
import com.bignerdranch.android.starwars.datalayer.room.MovieDao
import com.bignerdranch.android.starwars.datalayer.room.dto.ListMovie
import com.bignerdranch.android.starwars.domain.repos.MovieRepository
import com.bignerdranch.android.starwars.domain.entities.Cast
import com.bignerdranch.android.starwars.domain.entities.Movie
import com.bignerdranch.android.starwars.domain.entities.MovieDetails
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dataSource: RemoteDataSource,
    private val movieDao: MovieDao
) : MovieRepository {

    override fun getPopularMovieList(): Single<List<Movie>> {
        return dataSource.getPopular().map { responseResult ->
            responseResult.results.map { movieFromApi ->
                Movie(
                    id = movieFromApi.id,
                    overview = movieFromApi.overview,
                    poster_path = movieFromApi.poster_path,
                    release_date = movieFromApi.release_date,
                    title = movieFromApi.title,
                    vote_average = movieFromApi.vote_average
                )
            }
        }
    }

    override fun getDetailMovie(id: Int): Single<MovieDetails> {
        val castsSingle = dataSource.getCrewForMovie(id)
        val movieDetailsSingle = dataSource.getDetailMovie(id)

        return Single.zip(movieDetailsSingle, castsSingle) { responseResult, casts ->
            MovieDetails(
                id = responseResult.id,
                title = responseResult.title,
                poster_path = responseResult.poster_path,
                casts = casts.castFromApi.map { castFromApi ->
                    Cast(
                        id = castFromApi.id,
                        name = castFromApi.name,
                        profile_path = castFromApi.profile_path,
                        original_name = castFromApi.original_name
                    )
                },
                release_date = responseResult.release_date,
                overview = responseResult.overview,
                runtime = responseResult.runtime,
                original_title = responseResult.original_title,
                vote_average = responseResult.vote_average
            )
        }

    }

    override fun insertMovie(movie: List<Movie>): Completable {
        return movieDao.insert(convertMovieDetailsToFavorite(movie))

    }

    override fun getBdMovieList(): Single<List<Movie>> {
        val listMoviesSingle: Single<List<ListMovie>> = movieDao.getAllMovies()
        return listMoviesSingle.map { favoriteMovies ->
            val result = mutableListOf<Movie>()
            for (favoriteMovie in favoriteMovies)
                result.add(
                    Movie(
                        id = favoriteMovie.id,
                        title = favoriteMovie.title,
                        poster_path = favoriteMovie.poster_path,
                        release_date = favoriteMovie.release_date,
                        overview = favoriteMovie.overview,
                        vote_average = favoriteMovie.vote_average,
                    )
                )
            result
        }
    }

    private fun convertMovieDetailsToFavorite(movieDetails: List<Movie>): List<ListMovie> {

        return movieDetails.map { movieDetails ->
            ListMovie(
                id = movieDetails.id,
                title = movieDetails.title,
                poster_path = movieDetails.poster_path,
                release_date = movieDetails.release_date,
                overview = movieDetails.overview,
                vote_average = movieDetails.vote_average,
            )
        }
    }

    override fun getVideo(id: Int): Single<VideoResponse> {
        return dataSource.getVideo(id)
    }
}

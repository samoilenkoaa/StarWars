package com.bignerdranch.android.starwars.datalayer.remote
import com.bignerdranch.android.starwars.BuildConfig
import com.bignerdranch.android.starwars.datalayer.remote.dto.cinema.ApiResponse
import com.bignerdranch.android.starwars.datalayer.remote.dto.detail.MovieDetailResponse
import com.bignerdranch.android.starwars.datalayer.remote.dto.detail.crew.MovieCrewResponse
import com.bignerdranch.android.starwars.datalayer.remote.dto.video.VideoResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor() {

    companion object {
        private const val LANGUAGE_ENG = "eng-ENG"
        private const val BASE_URL = "https://api.themoviedb.org"
    }

    private var retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    private var retrofitService = retrofit.create(ApiService::class.java)

    fun getPopular(): Single<ApiResponse> {
        return retrofitService.getPopular(BuildConfig.CINEMA_API_KEY)
    }

    fun getCrewForMovie(id: Int): Single<MovieCrewResponse> {
        return retrofitService.getCrewForMovie(id, BuildConfig.CINEMA_API_KEY, LANGUAGE_ENG)
    }

    fun getVideo(id: Int): Single<VideoResponse> {
        return retrofitService.getVideo(id, BuildConfig.CINEMA_API_KEY, LANGUAGE_ENG)
    }

    fun getDetailMovie(id: Int): Single<MovieDetailResponse> {
        return retrofitService.getMovieDetails(id, LANGUAGE_ENG, BuildConfig.CINEMA_API_KEY)
    }
}
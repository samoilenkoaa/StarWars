package com.bignerdranch.android.starwars.domain.usecases
import com.bignerdranch.android.starwars.datalayer.remote.dto.video.VideoResponse
import com.bignerdranch.android.starwars.domain.repos.MovieRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetVideoUseCase @Inject constructor(private val repository: MovieRepository) {

    fun getVideo(id: Int): Single<VideoResponse> {
        return repository.getVideo(id)
    }

}
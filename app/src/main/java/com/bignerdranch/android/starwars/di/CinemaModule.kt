package com.bignerdranch.android.starwars.di
import android.content.Context
import androidx.room.Room
import com.bignerdranch.android.starwars.datalayer.remote.RemoteDataSource
import com.bignerdranch.android.starwars.datalayer.repos.MovieRepositoryImpl
import com.bignerdranch.android.starwars.datalayer.room.MovieDao
import com.bignerdranch.android.starwars.domain.repos.MovieRepository
import com.bignerdranch.android.starwars.datalayer.room.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CinemaModule {

    @Singleton
    @Provides
    fun provideCinemaRepository(
        dataSource: RemoteDataSource,
        movieDao: MovieDao
    ): MovieRepository {
        return MovieRepositoryImpl(dataSource, movieDao)
    }

    @Singleton
    @Provides
    fun provideMovieDao(@ApplicationContext context: Context): MovieDao {
        val database = Room.databaseBuilder(context, Database::class.java, "db").build()
        return database.favoriteDao()
    }
}
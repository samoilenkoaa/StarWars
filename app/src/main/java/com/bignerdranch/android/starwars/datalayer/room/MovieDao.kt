package com.bignerdranch.android.starwars.datalayer.room
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bignerdranch.android.starwars.datalayer.room.dto.ListMovie
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: List<ListMovie>): Completable

    @Query("SELECT * FROM FavoriteFilms")
    fun getAllMovies(): Single<List<ListMovie>>
}
package com.bignerdranch.android.starwars.datalayer.room
import androidx.room.Database
import androidx.room.RoomDatabase
import com.bignerdranch.android.starwars.datalayer.room.dto.ListMovie

@Database(entities = [ListMovie::class], version = 1)
abstract class Database : RoomDatabase(){
    abstract fun favoriteDao(): MovieDao
}
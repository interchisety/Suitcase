package com.example.suitcase.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.suitcase.data.converters.DateConverter
import com.example.suitcase.data.room.models.UserItem
import com.example.suitcase.data.room.models.UserList
import com.example.suitcase.data.room.models.UserItemDao
import com.example.suitcase.data.room.models.UserListDao

@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [UserList::class, UserItem::class],
    version = 1, exportSchema = false
)
abstract class SuitcaseDb:RoomDatabase() {
    abstract fun userListDao(): UserListDao
    abstract fun userItemDao(): UserItemDao


    companion object{
        @Volatile
        var INSTANCE: SuitcaseDb? = null
        fun getDb(context:Context): SuitcaseDb {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    SuitcaseDb::class.java,
                    "suit_listitem_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}
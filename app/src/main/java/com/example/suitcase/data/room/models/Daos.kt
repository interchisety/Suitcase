package com.example.suitcase.data.room.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.suitcase.data.room.models.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserListDao {
    @Upsert
    suspend fun upsertUserList(userList: UserList)

    @Delete
    suspend fun deleteUserList(userList: UserList)

    @Query("SELECT * FROM UserList")
    fun getAllUserList(): Flow<List<UserList>>

    @Query("SELECT * FROM UserList where list_id =:listID")
    fun getUserListById(listID:Int): Flow<List<UserList>>

    @Query("SELECT * FROM UserList LIMIT 5")
    fun getTopFiveUserList(): Flow<List<UserList>>
}


//
@Dao
interface UserItemDao {
    @Upsert
    suspend fun upsertUserItem(userItem: UserItem)

    @Delete
    suspend fun deleteUserItem(userItem: UserItem)

    @Query("SELECT * FROM UserItem")
    fun getAllUserItem(): Flow<List<UserItem>>

    @Query("SELECT * FROM UserItem where item_id=:itemID")
    fun getUserItem(itemID:Int): Flow<List<UserItem>>
}
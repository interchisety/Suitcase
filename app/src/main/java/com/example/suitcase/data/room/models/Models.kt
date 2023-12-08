package com.example.suitcase.data.room.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDateTime
import java.util.Date

@Entity
data class UserList(

    @ColumnInfo(name = "list_id")
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,

    val title:String,
    val desc:String,
    val createdDate: LocalDateTime,
)


@Entity
data class UserItem(

    @ColumnInfo(name = "item_id")
    @PrimaryKey
    val id: Int,

    val name:String,
    val desc:String,
    val purchase:Boolean,
    val price:Int,
    val unit: Int,
    val imageUrl:String,
    val lat:Double,
    val lng:Double,
    val listIdFK:Int
)

@Entity
data class ListWithItems(
    @Embedded
    val userList:UserList,
    @Relation(
        parentColumn = "id",
        entityColumn = "listIdFK"
    )
    val items:List<UserItem>
)
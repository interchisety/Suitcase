package com.example.suitcase

import android.content.Context
import com.example.suitcase.data.room.SuitcaseDb
import com.example.suitcase.ui.repository.Repository
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

object AppContainer {
    lateinit var db:SuitcaseDb
        private set

    val repository by lazy {
        Repository(
            userListDao = db.userListDao(),
            userItemDao = db.userItemDao()
        )
    }

    fun provide(context:Context){
        db = SuitcaseDb.getDb(context)
    }

}
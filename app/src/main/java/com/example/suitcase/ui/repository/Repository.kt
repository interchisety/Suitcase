package com.example.suitcase.ui.repository

import android.util.Log
import com.example.suitcase.data.room.models.UserItemDao
import com.example.suitcase.data.room.models.UserList
import com.example.suitcase.data.room.models.UserListDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.tasks.await

class Repository (
    private val userListDao:UserListDao,
    private val userItemDao:UserItemDao,
){
    private val auth = FirebaseAuth.getInstance()

    suspend fun isEmailAlreadyExist(email: String):Boolean{
        return try {
            auth.createUserWithEmailAndPassword(email,"temporaryPass0@1").await()
            false
        }catch (e:FirebaseAuthUserCollisionException){
            true
        }catch (e:Exception){
            false
        }
    }

    suspend fun signUp(email:String,password:String):Boolean{
        return try{
            auth.createUserWithEmailAndPassword(email,password).await()
            true
        }catch (e:Exception){
            false
        }
    }

    suspend fun signIn(email:String,password: String):Boolean{
        return try {
            auth.signInWithEmailAndPassword(email,password).await()
            true
        }catch (e:Exception){
            false
        }
    }

    fun signOut(){
        auth.signOut()
    }

    fun getCurrentUserEmail():String?{
        return auth.currentUser?.email
    }

    suspend fun upsertUserList(userlist:UserList){
        userListDao.upsertUserList(userlist)
    }

    suspend fun deleteUserList(userlist: UserList){
        userListDao.deleteUserList(userlist)
    }

    val userlists = userListDao.getAllUserList()

    val gettop5userlist = userListDao.getTopFiveUserList()

    fun getuserlistbyid(id:Int) = userListDao.getUserListById(id)


}
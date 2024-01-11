package com.example.booksharing.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDataDAO {
    // 初回設定時にユーザー名を登録する
    @Insert
    suspend fun insertUserData(userData: UserDataEntity)

    // ユーザー名からidを取得する
    @Query("SELECT id FROM UserDataEntity WHERE userName = :userName")
    suspend fun getIdByUserName(userName: String): Int

    // ユーザー名を変更する
    @Update
    suspend fun updateUserData(userData: UserDataEntity)

    // id = 0 のデータを取得する
    @Query("SELECT * FROM UserDataEntity WHERE id = 0")
    suspend fun getUserData(): UserDataEntity

    @Query("SELECT * FROM UserDataEntity WHERE id = 0")
    fun getUser(): UserDataEntity

}
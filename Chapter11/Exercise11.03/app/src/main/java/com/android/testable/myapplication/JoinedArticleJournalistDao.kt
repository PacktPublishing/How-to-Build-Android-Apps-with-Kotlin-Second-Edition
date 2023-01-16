package com.android.testable.myapplication

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface JoinedArticleJournalistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticleJournalist(joinedArticleJournalist: JoinedArticleJournalist)

    @Delete
    fun deleteArticleJournalist(joinedArticleJournalist: JoinedArticleJournalist)
}

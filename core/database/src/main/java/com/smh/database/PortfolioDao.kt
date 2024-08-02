package com.smh.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PortfolioDao {
    @Query("SELECT * FROM portfolioentity")
    fun getAll(): Flow<List<PortfolioEntity>>

    @Update
    fun update(entity: PortfolioEntity)

    @Delete
    fun delete(entity: PortfolioEntity)

    @Insert
    fun insert(entity: PortfolioEntity)
}
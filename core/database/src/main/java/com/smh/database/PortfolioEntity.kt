package com.smh.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("PortfolioEntity")
data class PortfolioEntity(
    @PrimaryKey
    val coinId: String,
    val amount: Double
)

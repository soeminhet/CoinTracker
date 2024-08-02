package com.smh.home

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEqualTo
import com.smh.database.CoinTrackerDb
import com.smh.database.PortfolioDao
import com.smh.database.PortfolioEntity
import com.smh.home.data.datasource.HomeLocalDataSourceImpl
import com.smh.home.domain.datasource.HomeLocalDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeLocalDataSourceImplTest {

    private lateinit var portfolioDao: PortfolioDao
    private lateinit var db: CoinTrackerDb
    private lateinit var homeLocalDataSource: HomeLocalDataSource

    // Constants
    val btc = PortfolioEntity(coinId = "btc", amount = 10.0)
    val eth = PortfolioEntity(coinId = "eth", amount = 3.3)
    val usdt = PortfolioEntity(coinId = "usdt", amount = 100.12)

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = CoinTrackerDb.createTestDb(context)
        portfolioDao = db.portfolioDao()
        homeLocalDataSource = HomeLocalDataSourceImpl(portfolioDao = portfolioDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun test()  {
        assertThat(1).isEqualTo(1)
    }

    @Test
    fun Insert_And_Get_Suceess() = runBlocking {
        homeLocalDataSource.insertPortfolio(entity = btc)
        homeLocalDataSource.insertPortfolio(entity = eth)
        homeLocalDataSource.insertPortfolio(entity = usdt)

        val actualResult = homeLocalDataSource.getAllPortfolio().first()
        assertThat(actualResult.count()).isEqualTo(3)
        assertThat(actualResult).contains(btc)
    }

    @Test
    fun Insert_Update_Get_NotEqual() = runBlocking {
        homeLocalDataSource.insertPortfolio(entity = btc)
        homeLocalDataSource.updatePortfolio(entity = btc.copy(amount = 12.0))

        val actualResult = homeLocalDataSource.getAllPortfolio().first()
        assertThat(actualResult[0].amount).isNotEqualTo(btc.amount)
    }

    @Test
    fun Insert_Delete_Get_Success() = runBlocking {
        homeLocalDataSource.insertPortfolio(entity = eth)
        homeLocalDataSource.insertPortfolio(entity = btc)
        homeLocalDataSource.deletePortfolio(entity = eth)

        val actualResult = homeLocalDataSource.getAllPortfolio().first()
        assertThat(actualResult.count()).isEqualTo(1)
        assertThat(actualResult.first()).isNotEqualTo(eth)
    }
}
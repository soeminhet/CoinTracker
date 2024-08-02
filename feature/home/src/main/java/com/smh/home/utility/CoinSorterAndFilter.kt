package com.smh.home.utility

import com.smh.home.domain.model.CoinModel
import com.smh.home.ui.SortOption
import javax.inject.Inject

class CoinSorterAndFilter @Inject constructor() {
    fun sortLiveCoins(sortOption: SortOption, data: List<CoinModel>): List<CoinModel> {
        return when (sortOption) {
            SortOption.Rank, SortOption.Holding -> data.sortedBy { it.rank }
            SortOption.Price -> data.sortedBy { it.price }
            SortOption.RankReverse, SortOption.HoldingReverse -> data.sortedByDescending { it.rank }
            SortOption.PriceReverse -> data.sortedByDescending { it.price }
        }
    }

    fun sortPortfolioCoins(sortOption: SortOption, data: List<CoinModel>): List<CoinModel> {
        return when (sortOption) {
            SortOption.Rank -> data.sortedBy { it.rank }
            SortOption.Price -> data.sortedBy { it.price }
            SortOption.Holding -> data.sortedBy { it.holdingPrice }
            SortOption.RankReverse -> data.sortedByDescending { it.rank }
            SortOption.PriceReverse -> data.sortedByDescending { it.price }
            SortOption.HoldingReverse -> data.sortedByDescending { it.holdingPrice }
        }
    }

    fun filterCoins(query: String, coins: List<CoinModel>): List<CoinModel> {
        return if (query.isEmpty()) coins else coins.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.symbol.contains(query, ignoreCase = true) ||
                    it.id.contains(query, ignoreCase = true)
        }
    }

    fun filterAndSortLiveCoins(
        query: String,
        sortOption: SortOption,
        allCoins: List<CoinModel>
    ): List<CoinModel> {
        val sortedCoins = sortLiveCoins(sortOption, allCoins)
        return filterCoins(query, sortedCoins)
    }

    fun filterAndSortPortfolioCoins(
        query: String,
        sortOption: SortOption,
        allCoins: List<CoinModel>
    ): List<CoinModel> {
        val sortedCoins = sortPortfolioCoins(sortOption, allCoins)
        return filterCoins(query, sortedCoins)
    }
}

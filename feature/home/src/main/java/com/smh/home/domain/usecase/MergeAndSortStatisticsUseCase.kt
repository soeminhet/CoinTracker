package com.smh.home.domain.usecase

import com.smh.home.ui.model.StatisticModel
import javax.inject.Inject

class MergeAndSortStatisticsUseCase @Inject constructor() {
    operator fun invoke(
        oldStatistics: List<StatisticModel>,
        newStatistics: List<StatisticModel>
    ): List<StatisticModel> {
        val statisticsMap = oldStatistics.associateBy { it.id }.toMutableMap()
        newStatistics.forEach { newStat->
            statisticsMap[newStat.id] = newStat
        }
        return statisticsMap.values.sortedBy { it.id }
    }
}

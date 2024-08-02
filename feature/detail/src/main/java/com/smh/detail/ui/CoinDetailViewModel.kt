package com.smh.detail.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smh.design.MessageBar
import com.smh.detail.domain.model.CoinDetailModel
import com.smh.detail.domain.usecase.GetCoinDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinDetailUseCase: GetCoinDetailUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow(CoinDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun fetchCoinDetail(coinId: String) {
        setLoading(true)
        viewModelScope.launch {
            getCoinDetailUseCase.execute(coinId)
                .tap { data ->
                    setLoading(false)
                    _uiState.update {
                        it.copy(coinDetail = data)
                    }
                }
                .tapLeft {
                    Log.e("CoinDetail", it.message.toString())
                    setLoading(false)
                    MessageBar.showMessage(it.message.toString())
                }
        }
    }

    private fun setLoading(loading: Boolean) {
        _uiState.update {
            it.copy(
                loading = loading
            )
        }
    }
}

data class CoinDetailUiState(
    val loading: Boolean = false,
    val coinDetail: CoinDetailModel? = null
)
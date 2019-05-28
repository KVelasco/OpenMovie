package com.kvelasco.openmovie.trending

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kvelasco.core.api.Status
import com.kvelasco.domain.trending.TrendingUseCases
import com.kvelasco.openmovie.common.BaseViewModel
import com.kvelasco.openmovie.common.UiState
import javax.inject.Inject

class TrendingViewModel @Inject constructor(private val trendingUseCases: TrendingUseCases): BaseViewModel() {

    private val _uiState = MutableLiveData<UiState<List<TrendingUiModel>>>()
    val uiState: LiveData<UiState<List<TrendingUiModel>>>
        get() = _uiState

    init {
       getTrendingMovies()
    }

    fun getTrendingMovies() {
        disposables.add(
            trendingUseCases.getTrendingMovies()
                .subscribe({
                    when (it.status) {
                        Status.LOADING -> {
                            _uiState.value = UiState(
                                View.VISIBLE,
                                null,
                                null
                            )
                        }
                        Status.SUCCESS -> {
                            _uiState.value = UiState(
                                View.GONE,
                                null,
                                it.data?.map { domain -> domain.toUiModel() }
                            )
                        }
                        Status.ERROR -> {
                            _uiState.value = UiState(
                                View.GONE,
                                it.error,
                                null
                            )
                        }
                    }
                }, {

                })
        )
    }
}
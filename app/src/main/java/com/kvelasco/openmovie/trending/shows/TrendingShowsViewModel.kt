package com.kvelasco.openmovie.trending.shows

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kvelasco.core.api.Status
import com.kvelasco.domain.trending.TrendingUseCases
import com.kvelasco.openmovie.common.BaseViewModel
import com.kvelasco.openmovie.common.UiState
import com.kvelasco.openmovie.trending.TrendingUiModel
import com.kvelasco.openmovie.trending.toUiModel
import javax.inject.Inject

class TrendingShowsViewModel @Inject constructor(private val trendingUseCases: TrendingUseCases): BaseViewModel() {

    private val _uiState = MutableLiveData<UiState<List<TrendingUiModel>>>()
    val uiState: LiveData<UiState<List<TrendingUiModel>>>
        get() = _uiState

    init {
        getTrendingShows()
    }

    fun getTrendingShows() {
        disposables.add(
            trendingUseCases.getTrendingShows()
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
                    _uiState.value = UiState(
                        View.GONE,
                        it,
                        null
                    )
                })
        )
    }
}
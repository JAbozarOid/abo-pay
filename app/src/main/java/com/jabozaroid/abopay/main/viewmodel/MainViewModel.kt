package com.jabozaroid.abopay.main.viewmodel

import com.jabozaroid.abopay.core.domain.usecase.user.GetCurrentUserUseCase
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.main.model.MainAction
import com.jabozaroid.abopay.main.model.MainEvent
import com.jabozaroid.abopay.main.model.MainViewState
import javax.inject.Inject


internal class MainViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
) :
    BaseViewModel<MainViewState, MainAction, MainEvent>(initialState = MainViewState()) {

    override fun handleAction(action: MainAction) {
        when (action) {
            is MainAction.FetchStartUpData -> {
                start()
            }
        }
    }

    override val onRefresh: () -> Unit
        get() = { }

    init {
//        getLoginOtp()
    }

    // todo (abozar) : impl current user usecase
    private fun start() {
        updateState {
            it.copy(loading = false)
        }
    }


    fun getStartupData() {
        process(MainAction.FetchStartUpData)
    }



    private fun getLoginOtp() {


    }
}


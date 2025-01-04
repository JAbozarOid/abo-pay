package com.jabozaroid.abopay.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jabozaroid.abopay.core.ui.entity.DisplayedError
import com.jabozaroid.abopay.core.ui.model.IAction
import com.jabozaroid.abopay.core.ui.model.IEvent
import com.jabozaroid.abopay.core.ui.navigation.NavigationCommand
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


abstract class BaseViewModel<State, Action : IAction, Event : IEvent>(
    val initialState: State
) : ViewModel() {

    val onRefreshState = MutableStateFlow(false)

    // don't expose mutable types
    private val _uiStateFlow = MutableStateFlow(initialState)
    val uiStateFlow: StateFlow<State>
        get() = _uiStateFlow

    val currentState: State
        get() = _uiStateFlow.value

    private val _uiActionFlow = MutableSharedFlow<Action>()

    // don't expose mutable types
    private val _uiEventFlow = MutableSharedFlow<IEvent>()
    val uiEventFlow: SharedFlow<IEvent>
        get() = _uiEventFlow

    private val _navigationFlow = MutableSharedFlow<NavigationCommand>()
    val navigationFlow: Flow<NavigationCommand> = _navigationFlow

    private val _errorFlow: MutableSharedFlow<DisplayedError> = MutableSharedFlow()
    val errorFlow: Flow<DisplayedError>
        get() = _errorFlow

    abstract fun handleAction(action: Action)

    init {
        viewModelScope.launch {
            // collected the emitted actions in process method
            _uiActionFlow.collect(this@BaseViewModel::handleAction)
        }
        refresh()
    }

    // this method called from other viewmodels to process actions -> emit actions
    fun process(action: Action) {
        viewModelScope.launch {
            _uiActionFlow.emit(action)
        }
    }


    // this mutation works as Reducer -> change the current state to new state
    fun updateState(mutation: (currentState: State) -> State) {
        _uiStateFlow.update {
            mutation.invoke(it)
        }
    }

    // handle events of applications like snake, toast..
    fun sendEvent(event: IEvent) {
        viewModelScope.launch {
            _uiEventFlow.emit(event)
        }
    }

    fun navigateTo(command: NavigationCommand.ToScreen) {
        viewModelScope.launch {
            _navigationFlow.emit(command)
        }
    }

    fun navigateTo(command: NavigationCommand.ToWithData) {
        viewModelScope.launch {
            _navigationFlow.emit(command)
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            _navigationFlow.emit(NavigationCommand.Back)
        }
    }

    abstract val onRefresh: () -> Unit

    private fun refresh() {
        viewModelScope.launch {
            onRefreshState.collect { state ->
                if (state) {
                    onRefreshState.emit(false)
                    onRefresh()
                }
            }
        }
    }

}
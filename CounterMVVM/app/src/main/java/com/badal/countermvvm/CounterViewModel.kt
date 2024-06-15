package com.badal.countermvvm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CounterViewModel() : ViewModel() {

    private val repository: CounterRepository = CounterRepository()

    private val _count = mutableIntStateOf(repository.getCounter().count)

    val count: MutableState<Int> = _count

    fun increment() {
        repository.increment()
        _count.intValue = repository.getCounter().count
    }

    fun decrement() {
        repository.decrement()
        _count.intValue = repository.getCounter().count
    }

}
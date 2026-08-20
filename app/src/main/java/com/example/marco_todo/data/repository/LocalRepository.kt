package com.example.marco_todo.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.marco_todo.data.model.LocalItem

object LocalRepository {
    private val _items = mutableStateListOf<LocalItem>(
        LocalItem(1, "Primer elemento", "Descripción del primer elemento local"),
        LocalItem(2, "Segundo elemento", "Descripción del segundo elemento local")
    )

    val items: List<LocalItem> get() = _items

    fun addItem(title: String, description: String) {
        val newId = if (_items.isEmpty()) 1 else _items.maxOf { it.id } + 1
        _items.add(0, LocalItem(newId, title, description))
    }

    fun removeItem(id: Int) {
        _items.removeAll { it.id == id }
    }

    fun updateItem(id: Int, newTitle: String, newDescription: String) {
        val index = _items.indexOfFirst { it.id == id }
        if (index != -1) {
            _items[index] = _items[index].copy(title = newTitle, description = newDescription)
        }
    }

    fun clearAll() {
        _items.clear()
    }
}

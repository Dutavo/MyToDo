package br.com.dutavo.mytodo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dutavo.mytodo.data.model.Todo
import br.com.dutavo.mytodo.data.repository.TodoRepository

class TodoViewModel : ViewModel() {
    private val todoRepository = TodoRepository()
    private val _todos = MutableLiveData<List<Todo>>()
    val todos: LiveData<List<Todo>> = _todos

    fun loadTodos() {
        todoRepository.getTodos { todoList -> _todos.postValue(todoList) }
    }

    fun addTodo(titulo: String, descricao: String) {
        todoRepository.addTodo(titulo, descricao) { success, _ ->
            if (success) loadTodos()
        }
    }

    fun deleteTodo(todoId: String) {
        todoRepository.deleteTodo(todoId) { success ->
            if (success) loadTodos()
        }
    }

    fun updateTodo(todoId: String, completed: Boolean) {
        todoRepository.updateTodo(todoId, completed) { success ->
            if (success) loadTodos()
        }
    }
}
package br.com.dutavo.mytodo.data.repository

import br.com.dutavo.mytodo.data.model.Todo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TodoRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun addTodo(titulo: String, descricao: String, callback: (Boolean, String?) -> Unit) {
        val usuarioId = auth.currentUser?.uid?: return
        val todoId = db.collection("todos").document().id
        val todo = Todo(id = todoId, usuarioId = usuarioId, titulo = titulo, descricao = descricao)

        db.collection("todos").document(todoId).set(todo)
            .addOnSuccessListener { callback(true, null) }
            .addOnFailureListener { e -> callback(false, e.message) }
    }

    fun getTodos(callback: (List<Todo>) -> Unit) {
        val usuarioId = auth.currentUser?.uid ?: return
        db.collection("todos")
            .whereEqualTo("usuarioId", usuarioId)
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    val todos = it.documents.map { doc -> doc.toObject(Todo::class.java)!! }
                    callback(todos)
                }
            }
    }

    fun deleteTodo(todoId: String, callback: (Boolean) -> Unit) {
        db.collection("todos").document(todoId).delete()
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }

    fun updateTodo(todoId: String, completed: Boolean, callback: (Boolean) -> Unit) {
        db.collection("todos").document(todoId)
            .update("completed", completed)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }
}
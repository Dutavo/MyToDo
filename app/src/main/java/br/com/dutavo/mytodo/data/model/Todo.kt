package br.com.dutavo.mytodo.data.model

data class Todo(
    val id: String = "",
    val usuarioId: String = "",
    val titulo: String = "",
    val descricao: String = "",
    val completed: Boolean = false,
)

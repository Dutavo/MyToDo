package br.com.dutavo.mytodo.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.dutavo.mytodo.data.model.Todo
import br.com.dutavo.mytodo.databinding.ItemTodoBinding

class TodoAdapter (private val todos: MutableList<Todo>, private val viewModel: TodoViewModel) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){

    class TodoViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTodoBinding.inflate(inflater, parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos[position]
        holder.binding.todoTitle.text = todo.titulo
        holder.binding.todoDescription.text = todo.descricao
        holder.binding.todoCheckbox.isChecked = todo.completed

        // Atualizar tarefa ao marcar/desmarcar
        holder.binding.todoCheckbox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateTodo(todo.id, isChecked)
        }
        holder.binding.deleteButton.setOnClickListener {
            viewModel.deleteTodo(todo.id)
        }
    }

    override fun getItemCount() = todos.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newTodos: List<Todo>) {
        todos.clear()
        todos.addAll(newTodos)
        notifyDataSetChanged()
    }
}
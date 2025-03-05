package br.com.dutavo.mytodo.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.dutavo.mytodo.R
import br.com.dutavo.mytodo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: TodoViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter

    //Tela Inicial após o login
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoAdapter = TodoAdapter(mutableListOf(), viewModel)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = todoAdapter

        // Observar mudanças na lista de tarefas
        viewModel.todos.observe(this) { todos ->
            todoAdapter.updateList(todos)
        }

        // Botão para adicionar tarefas
        binding.addButton.setOnClickListener {
            val titulo = binding.editTitle.text.toString()
            val descricao = binding.editDescription.text.toString()
            if ( titulo.isNotEmpty()) {
                viewModel.addTodo(titulo, descricao)
                binding.editTitle.text.clear()
                binding.editDescription.text.clear()
            }
        }

        viewModel.loadTodos()
    }
}
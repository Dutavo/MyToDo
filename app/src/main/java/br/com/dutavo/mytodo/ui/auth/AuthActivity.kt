package br.com.dutavo.mytodo.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.dutavo.mytodo.databinding.ActivityAuthBinding
import br.com.dutavo.mytodo.ui.main.MainActivity

class AuthActivity : AppCompatActivity(){
    //  Tela de login e registro de usuário
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.authResult.observe(this) { result ->
            if (result.first) {
                Toast.makeText(this, "Sucesso!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, result.second, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.userData.observe(this) { data ->
            data?.let {
                val userName = it["nome"] as? String ?: "Desconhecido"
                Toast.makeText(this, "Bem-vindo, $userName!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
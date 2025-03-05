package br.com.dutavo.mytodo.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dutavo.mytodo.data.repository.UserRepository

class AuthViewModel : ViewModel() {

    //Gerencia estados de autenticação
    private val userRepository = UserRepository()
    private val _authResult = MutableLiveData<Pair<Boolean, String?>>()
    val authResult: LiveData<Pair<Boolean, String?>> = _authResult

    private val _userData = MutableLiveData<Map<String, Any>?>()
    val userData: LiveData<Map<String, Any>?> = _userData

    val email = MutableLiveData<String>()
    val senha = MutableLiveData<String>()
    val nome = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    // Função para realizar o login
    fun register() {
        isLoading.value = true
        userRepository.register(
            email.value ?: "",
            senha.value ?: "",
            nome.value ?: "",
        ) { success, message ->
            // Atualiza o valor do LiveData
            isLoading.postValue(false)
            _authResult.postValue(Pair(success, message))
        }
    }

    // Função para realizar o login
    fun login() {
        isLoading.value = true
        userRepository.login(email.value ?: "", senha.value ?: "") { success, message ->
            // Atualiza o valor do LiveData
            isLoading.postValue(false)
            _authResult.postValue(Pair(success, message))
        }
    }

    fun loadUserData() {
        userRepository.getUserData { success, data, error ->
            if (success) {
                _userData.postValue(data)
            } else {
                _userData.postValue(null)
            }
        }
    }
}
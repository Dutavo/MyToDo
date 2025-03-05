package br.com.dutavo.mytodo.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()

    fun register(email: String, senha : String, nome : String, callback : (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Salvar dados do usuário no Firestore
                    val usuarioId = auth.currentUser!!.uid
                    val usuario = hashMapOf(
                        "id" to usuarioId,
                        "nome" to nome,
                        "email" to email,
                        "createdAt" to System.currentTimeMillis())
                    db.collection("usuarios").document(usuarioId).set(usuario)
                        .addOnSuccessListener { callback(true, null) }
                        .addOnFailureListener { e -> callback(false, e.message) }
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    fun login(email : String, senha : String, callback : (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true, null)
            } else {
                callback(false, task.exception?.message)
            }
        }
    }

    fun getUserData(callback:(Boolean, Map<String, Any>?, String?) -> Unit) {
        val usuarioId = auth.currentUser?.uid
        if (usuarioId != null) {
            db.collection("usuarios").document(usuarioId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        callback(true, document.data, null)
                    } else {
                        callback(false, null, "Usuário não encontrado!")
                    }
                }
                .addOnFailureListener { e -> callback(false, null, e.message) }
        } else {
            callback(false, null, "Usuário não autenticado!")
        }
    }
}
package com.igsolution.safeneighborhood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.igsolution.safeneighborhood.databinding.ActivityFormLoginBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class formLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        VerificarLogin()

        binding.textForgetPassword.setOnClickListener {
            Toast.makeText(applicationContext,R.string.toast_msg, Toast.LENGTH_LONG).show()
        }

        binding.btEntrar.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()
            val mensagem_erro = "Preencha todos os campos"

            if(email.isEmpty() || senha.isEmpty()){
                Toast.makeText(applicationContext, mensagem_erro, Toast.LENGTH_SHORT).show()
            }else{
                AutenticarUsuario()
            }
        }


    }

    private fun AutenticarUsuario(){
        val email = binding.editEmail.text.toString()
        val senha = binding.editSenha.text.toString()
        val mensagem_erro = "Preencha todos os campos"
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(this, "Bem-vindo!", Toast.LENGTH_SHORT).show()
                AbrirNavegacao()
            }
        }.addOnFailureListener {

            var erro = it

            when{
                erro is FirebaseAuthInvalidCredentialsException -> Toast.makeText(this, "Usuario e/ou Senha inválidos", Toast.LENGTH_LONG).show()
                erro is FirebaseNetworkException -> Toast.makeText(this, "Sem Conexão com a Internet", Toast.LENGTH_LONG).show()
                else -> Toast.makeText(this, "Erro ao efetuar login", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun VerificarLogin(){
        val usuariologado = FirebaseAuth.getInstance().currentUser

        if(usuariologado != null){
            AbrirNavegacao()
        }

    }

    private fun AbrirNavegacao()
    {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}


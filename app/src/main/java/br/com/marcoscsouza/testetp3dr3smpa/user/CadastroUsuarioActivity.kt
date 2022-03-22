package br.com.marcoscsouza.testetp3dr3smpa.user

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import br.com.marcoscsouza.testetp3dr3smpa.R
import br.com.marcoscsouza.testetp3dr3smpa.databinding.ActivityCadastroUsuarioBinding
import br.com.marcoscsouza.testetp3dr3smpa.ui.AvaliarActivity
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.IllegalArgumentException

class CadastroUsuarioActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCadastroUsuarioBinding.inflate(layoutInflater)
    }
    private val firebaseAuth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastre seu e-mail"

        configuraCadastro()
    }

    private fun configuraCadastro() {

        val emailCadastrar = binding.cadastroUsuarioEmail.text
        val senhaCadastrar = binding.cadastroUsuarioSenha.text
        val confirmaSenhaCadastrar = binding.cadastroUsuarioConfirmaSenha.text

        binding.btnCadastrarUsuario.setOnClickListener {

            binding.cadastroUsuarioEmail.error = null
            binding.cadastroUsuarioSenha.error = null
            binding.cadastroUsuarioConfirmaSenha.error = null

            var valido = true

            if (emailCadastrar.isNullOrBlank()) {
                binding.cadastroUsuarioEmail.error = "E-mail necessário!"
                valido = false
            }
            if (senhaCadastrar.isNullOrBlank()) {
                binding.cadastroUsuarioSenha.error = "Senha necessária!"
                valido = false
            }
            if (senhaCadastrar.toString() != confirmaSenhaCadastrar.toString()) {
                binding.cadastroUsuarioConfirmaSenha.error = "Senha diferente"
                valido = false
            }

            if (valido) {

                cadastrarUsuario(emailCadastrar, senhaCadastrar).observe(this, Observer {
                    it?.let {
                        if (it) {
                            val i = Intent(this, AvaliarActivity::class.java)
                            i.putExtra("user_id", firebaseAuth.uid)
                            startActivity(i)
//                            finish()
                        } else {
                            Log.e("cadastro user", "livedata error no cadastro do usuario")
                        }
                    }
                })

            }

        }
    }

    private fun cadastrarUsuario(
        emailCadastrar: Editable?,
        senhaCadastrar: Editable?
    ): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        try {
            firebaseAuth.createUserWithEmailAndPassword(
                emailCadastrar.toString(),
                senhaCadastrar.toString()
            )
                .addOnSuccessListener {
                    Toast.makeText(
                        this,
                        "usuario cadastrado com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()
                    liveData.value = true
                }
                .addOnFailureListener { exception ->
                    Log.e("CadastroActivity", "onCreate: ", exception)
                    val msgError: String = when (exception) {
                        is FirebaseAuthWeakPasswordException -> "Senha deve conter 6 ou mais digitos"
                        is FirebaseAuthInvalidCredentialsException -> "E-mail inválido"
                        is FirebaseAuthUserCollisionException -> "E-mail já cadastrado!"
                        else -> {
                            "Erro desconhecido!"
                        }
                    }
                    Toast.makeText(this, msgError, Toast.LENGTH_LONG).show()
                    liveData.value = false
                }

        } catch (e: IllegalArgumentException) {
            Toast.makeText(
                this,
                "Campos de e-mail ou senha não podem ser vazios",
                Toast.LENGTH_LONG
            ).show()
        }
        return liveData
    }
}
package br.com.marcoscsouza.testetp3dr3smpa.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewTreeLifecycleOwner.get
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import br.com.marcoscsouza.testetp3dr3smpa.R
import br.com.marcoscsouza.testetp3dr3smpa.databinding.ActivityAvaliarBinding
import br.com.marcoscsouza.testetp3dr3smpa.db.AppDatabase
import br.com.marcoscsouza.testetp3dr3smpa.db.Avaliar
import br.com.marcoscsouza.testetp3dr3smpa.db.AvaliarDao
import br.com.marcoscsouza.testetp3dr3smpa.user.LoginUsuarioActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AvaliarActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAvaliarBinding.inflate(layoutInflater)
    }

    private val firebaseAuth = Firebase.auth
    private val avaliarId = 0L
    private val avaliarDao: AvaliarDao by lazy {
        val db = AppDatabase.instancia(this)
        db.avaliarDao()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)




        avaliarEmpresa()

        configurarBtn()


        Toast.makeText(this, "User ID: ${firebaseAuth.uid}", Toast.LENGTH_LONG).show()
    }

    private fun configurarBtn() {
        binding.btnVerTodasAvaliadas.setOnClickListener {
            val i = Intent(this, ListaTodasAvaliadasActivity::class.java)
            startActivity(i)
        }

        binding.btnVerMinhasAvaliadas.setOnClickListener {
            val i = Intent(this, ListaMinhasAvaliadasActivity::class.java)
            startActivity(i)
        }
    }

    private fun avaliarEmpresa() {

        binding.btnAvaliar.setOnClickListener {



            val id: String = firebaseAuth.uid.toString()
            val empresaAvaliada = binding.empresaAvaliada.text.toString()
            val bairroAvaliado = binding.bairroAvaliada.text.toString()

            val primeiraResposta: Boolean = binding.switch1.isChecked
            val segundaResposta: Boolean = binding.switch2.isChecked
            val terceiraResposta: Boolean = binding.switch3.isChecked
            val quartaResposta: Boolean = binding.switch4.isChecked
            val quintaResposta: Boolean = binding.switch5.isChecked
            val sextaResposta: Boolean = binding.switch6.isChecked


            if (empresaAvaliada.isBlank() || bairroAvaliado.isBlank()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {

                val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
                val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

                val empresaEncrypt = EncryptedSharedPreferences.create(
                    empresaAvaliada,
                    mainKeyAlias,
                    applicationContext,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )

                val bairroEncrypt = EncryptedSharedPreferences.create(
                    bairroAvaliado,
                    mainKeyAlias,
                    applicationContext,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )

                val avaliarNova = Avaliar(
                    id = avaliarId,
                    uid = id,
                    empresa = empresaEncrypt.toString(),
                    bairro = bairroEncrypt.toString(),
                    pergunta1 = primeiraResposta,
                    pergunta2 = segundaResposta,
                    pergunta3 = terceiraResposta,
                    pergunta4 = quartaResposta,
                    pergunta5 = quintaResposta,
                    pergunta6 = sextaResposta
                )
                avaliarDao.insert(avaliarNova)
                startActivity(Intent(this, ListaTodasAvaliadasActivity::class.java))

            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!estaLogado()){
            val i = Intent(this, LoginUsuarioActivity::class.java)
            startActivity(i)
        }
    }


    fun estaLogado(): Boolean{
        val userFire: FirebaseUser? = firebaseAuth.currentUser
        if (userFire != null){
            Toast.makeText(this, "Usuário logado: ${userFire.email}", Toast.LENGTH_SHORT).show()
            return true
        }else{
            Toast.makeText(this, "Usuário não está logado!", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menuuser, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.exitMenu -> {
                Toast.makeText(this, "Usuário deslogado!", Toast.LENGTH_SHORT).show()
                firebaseAuth.signOut()
                val i = Intent(this, LoginUsuarioActivity::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
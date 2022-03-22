package br.com.marcoscsouza.testetp3dr3smpa.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.com.marcoscsouza.testetp3dr3smpa.R
import br.com.marcoscsouza.testetp3dr3smpa.adapter.AvaliadosAdapter
import br.com.marcoscsouza.testetp3dr3smpa.adapter.MinhasAvaliadasAdapter
import br.com.marcoscsouza.testetp3dr3smpa.databinding.ActivityListaMinhasAvaliadasBinding
import br.com.marcoscsouza.testetp3dr3smpa.db.AppDatabase
import br.com.marcoscsouza.testetp3dr3smpa.user.LoginUsuarioActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ListaMinhasAvaliadasActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityListaMinhasAvaliadasBinding.inflate(layoutInflater)
    }
    private val firebaseAuth = Firebase.auth
    private val adapter = MinhasAvaliadasAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val rv = binding.rvMinhasAvaliadas
        rv.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.instancia(this)
        val avaliarDao = db.avaliarDao()
        adapter.atualiza(avaliarDao.getAll())

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
package br.com.marcoscsouza.testetp3dr3smpa.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import br.com.marcoscsouza.testetp3dr3smpa.databinding.AvaliarItemBinding
import br.com.marcoscsouza.testetp3dr3smpa.db.Avaliar
import java.io.ByteArrayOutputStream

class AvaliadosAdapter(
    private val context: Context,
    avaliados: List<Avaliar> = emptyList()
): RecyclerView.Adapter<AvaliadosAdapter.ViewHolder>() {
    private val avaliados = avaliados.toMutableList()

    inner class ViewHolder(private val binding: AvaliarItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
                private lateinit var avaliar: Avaliar

                fun vincula(avaliar: Avaliar){
                    this.avaliar = avaliar

                    val avaliador = binding.avaliarItemAvaliador
                    avaliador.text = "Avaliador ID: ${avaliar.uid.toString()}"

                    val empresa = binding.avaliarItemEmpresa
                    empresa.text = "Empresa: ${avaliar.empresa}"

                    val bairro = binding.avaliarItemBairro
                    bairro.text = "Bairro: ${avaliar.bairro}"

                    val primeiraPergunta = binding.avaliarItemPergunta1
                    primeiraPergunta.text = "Local possui bom atendimento?   ${ converterResposta(avaliar.pergunta1)}"

                    val segundaPergunta = binding.avaliarItemPergunta2
                    segundaPergunta.text = "Local possui boa higienização?   ${converterResposta(avaliar.pergunta2)}"

                    val terceiraPergunta = binding.avaliarItemPergunta3
                    terceiraPergunta.text = "Local possui comida de qualidade?   ${converterResposta(avaliar.pergunta3)}"

                    val quartaPergunta = binding.avaliarItemPergunta4
                    quartaPergunta.text = "Local possui preço justo?   ${converterResposta(avaliar.pergunta4)}"

                    val quintaPergunta = binding.avaliarItemPergunta5
                    quintaPergunta.text = "Local possui um ambiente agradável?   ${converterResposta(avaliar.pergunta5)}"

                    val sextaPergunta = binding.avaliarItemPergunta6
                    sextaPergunta.text = "Local possui uma cozinha limpa?   ${converterResposta(avaliar.pergunta6)}"
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = AvaliarItemBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val avaliado = avaliados[position]
        holder.vincula(avaliado)
    }

    override fun getItemCount(): Int = avaliados.size

    fun atualiza(avaliar: List<Avaliar>){
        this.avaliados.clear()
        this.avaliados.addAll(avaliar)
        notifyDataSetChanged()
    }

    fun converterResposta(tipo: Boolean): String {
        if (tipo){
            return "Sim"
        }else {
            return "Não"
        }
    }


}
package br.com.marcoscsouza.testetp3dr3smpa.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Avaliar(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "user_id") val uid: String,
    @ColumnInfo(name = "bairro_avaliar") val bairro: String,
    @ColumnInfo(name = "empresa_avaliar") val empresa: String,
    @ColumnInfo(name = "pergunta1_avaliar") val pergunta1: Boolean,
    @ColumnInfo(name = "pergunta2_avaliar") val pergunta2: Boolean,
    @ColumnInfo(name = "pergunta3_avaliar") val pergunta3: Boolean,
    @ColumnInfo(name = "pergunta4_avaliar") val pergunta4: Boolean,
    @ColumnInfo(name = "pergunta5_avaliar") val pergunta5: Boolean,
    @ColumnInfo(name = "pergunta6_avaliar") val pergunta6: Boolean
) : Parcelable
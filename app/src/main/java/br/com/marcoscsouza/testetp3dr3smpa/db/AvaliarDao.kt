package br.com.marcoscsouza.testetp3dr3smpa.db

import androidx.room.*

@Dao
interface AvaliarDao {
    @Query("SELECT * FROM Avaliar")
    fun getAll(): List<Avaliar>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg avaliar: Avaliar)

    @Delete
    fun remove(avaliar: Avaliar)

    @Query("SELECT * FROM Avaliar WHERE id = :id")
    fun getById(id: Long): Avaliar?

    @Query("SELECT * FROM Avaliar WHERE id = :uid")
    fun getByUid(uid: String): Avaliar?

}
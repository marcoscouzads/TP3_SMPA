package br.com.marcoscsouza.testetp3dr3smpa.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Avaliar::class], version = 1, exportSchema = true)
abstract class AppDatabase: RoomDatabase() {

    abstract fun avaliarDao(): AvaliarDao

    companion object {
        fun instancia(context: Context) : AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "lista_avaliada.db"
            ).allowMainThreadQueries()
                .build()
        }
    }
}
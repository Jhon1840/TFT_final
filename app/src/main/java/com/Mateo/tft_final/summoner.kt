package com.Mateo.tft_final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class summoner : AppCompatActivity() {


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://LA2.api.riotgames.com") // Ajusta la región según corresponda
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val riotApiService = retrofit.create(RiotApiService::class.java)
    private lateinit var rank: TextView
    private lateinit var lvl: TextView
    private val summonerName = "elsublime69"
    private val apiKey = "RGAPI-d3982fb3-e6ca-4c4a-9091-d81defb8468e" // Reemplaza con tu clave de API



    private lateinit var name: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summoner)

        name = findViewById(R.id.name)
        val textView = findViewById<TextView>(R.id.textView)
        val text = intent.getStringExtra("text")

        name.text = text

        rank = findViewById(R.id.rank) // Asigna el TextView con el ID correcto
        lvl = findViewById(R.id.textView4)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                // Obtén los datos del invocador por su nombre de invocador
                val summoner = riotApiService.getSummonerByName(summonerName, apiKey)

                // Utiliza el ID del invocador para obtener las estadísticas de TFT
                val tftStats = riotApiService.getTftPlayerStats(summoner.id, apiKey)



                // Crea una cadena de texto con las estadísticas
                  val statsText = buildString {
                     for (stats in tftStats) {
                         append("Account ID: ${stats.accountId}\n")
                         append("ID: ${stats.id}\n")
                         append("Name: ${stats.name}\n")
                         append("Profile Icon ID: ${stats.profileIconId}\n")
                         append("PUUID: ${summoner.puuid}\n")
                         append("Revision Date: ${summoner.revisionDate}\n")
                         append("Summoner Level: ${summoner.summonerLevel}\n")
                         // Agrega otros campos según las estadísticas que desees mostrar
                         append("\n")
                     }
                 }

                // Muestra las estadísticas en el TextView

                lvl.text = summoner.summonerLevel.toString()
                // Imprimir la respuesta en la consola
                println("Respuesta de la API: $tftStats")

                println("Respuesta de la API: $summoner")

            } catch (e: Exception) {
                // Maneja errores
                textView.text = "Error al obtener las estadísticas de TFT: ${e.message}"
            }
        }




    }
}
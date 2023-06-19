package com.Mateo.tft_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://LA2.api.riotgames.com") // Ajusta la región según corresponda
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val riotApiService = retrofit.create(RiotApiService::class.java)
    private lateinit var textView: TextView // Variable para mostrar las estadísticas
    private val summonerName = "elsublime69"
    private val apiKey = "RGAPI-d3982fb3-e6ca-4c4a-9091-d81defb8468e" // Reemplaza con tu clave de API

    private lateinit var myButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myButton = findViewById(R.id.myButton)

        myButton.setOnClickListener {
            val text = textView.text.toString()
            val intent = Intent(this, summoner::class.java)
            intent.putExtra("text", text)
            startActivity(intent)
        }

        textView = findViewById(R.id.statsTextView) // Asigna el TextView con el ID correcto


        GlobalScope.launch(Dispatchers.Main) {
            try {
                // Obtén los datos del invocador por su nombre de invocador
                val summoner = riotApiService.getSummonerByName(summonerName, apiKey)


                // Crea una cadena de texto con las estadísticas
               /* val statsText = buildString {
                    for (stats in tftStats) {
                        append("Account ID: ${summoner.accountId}\n")
                        append("ID: ${summoner.id}\n")
                        append("Name: ${summoner.name}\n")
                        append("Profile Icon ID: ${summoner.profileIconId}\n")
                        append("PUUID: ${summoner.puuid}\n")
                        append("Revision Date: ${summoner.revisionDate}\n")
                        append("Summoner Level: ${summoner.summonerLevel}\n")
                        // Agrega otros campos según las estadísticas que desees mostrar
                        append("\n")
                    }
                }*/

                // Muestra las estadísticas en el TextView
                textView.text = summoner.summonerLevel.toString()
                // Imprimir la respuesta en la consola
                println("Respuesta de la API: $summoner")

            } catch (e: Exception) {
                // Maneja errores
                textView.text = "Error al obtener las estadísticas de TFT: ${e.message}"
            }
        }
    }
}
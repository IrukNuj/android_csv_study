package com.example.csv_study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    var colmun: Array<String> = emptyArray()
    var pokemons: Array<Shelter> = emptyArray()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readCsv("dex.csv")

        val listView: ListView = findViewById(R.id.listView)
        val listItems: Array<String> = formatItems(pokemons)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,listItems)
        listView.adapter = adapter
    }

    fun readCsv(filename: String){
        try {
            val file = resources.assets.open(filename)
            val fileReader = BufferedReader(InputStreamReader(file))
            var i: Int = 0
            fileReader.forEachLine {
                if (it.isNotBlank()) {
                    if ( i == 0 ) {

                        colmun = it.split(",").toTypedArray()
                    } else {
                        val line = it.split(",").toTypedArray()
                        fetchCSV(line)
                    }
                }
                i++
            }

        }catch (e: IOException) {
            print(e)
        }
    }

    fun fetchCSV(line: Array<String>){
        val shelter = Shelter(
            id = line[0].toInt(),
            name = line[1],
            weight = line[2].toFloat(),
            types = line[3],
            abilities = line[10]
        )

        pokemons += shelter
    }

    fun formatItems(pokemons: Array<Shelter>): Array<String>{
        var items: Array<String> = emptyArray()
        pokemons.forEach {
            items += """
                |名前: ${it.name}
                |図鑑番号: ${it.id}
                |重さ: ${it.weight}
                |タイプ: ${it.types}
                |特性: ${it.abilities}""".trimMargin()
        }
        return items

    }
}

package com.edu.pasteleria

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val button_ofertas = findViewById<Button>(R.id.button_ofertas)
        val button_catalogo = findViewById<Button>(R.id.button_catalogo)
        val button_contactos = findViewById<Button>(R.id.button_contactos)
        val button_pedido = findViewById<Button>(R.id.button_pedidos)
        val button_salir = findViewById<Button>(R.id.button_salir)

        button_ofertas.setOnClickListener(){
            val intent = Intent(this@MainActivity, ofertaActivity::class.java)
            startActivity(intent)
        }

        button_catalogo.setOnClickListener(){
            val intent = Intent(this@MainActivity, catalogoActivity::class.java)
            startActivity(intent)
        }

        button_contactos.setOnClickListener(){
            val intent = Intent(this@MainActivity, contactosActivity::class.java)
            startActivity(intent)
        }

        button_pedido.setOnClickListener(){
            val intent = Intent(this@MainActivity, pedidosActivity::class.java)
            startActivity(intent)
        }

        button_salir.setOnClickListener(){
            finish();
        }
    }
}
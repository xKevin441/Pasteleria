package com.edu.pasteleria

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class pedidosActivity : AppCompatActivity() {
    var dbHandler: BaseDatos? = null
    var listTasks: List<Pedidos> = ArrayList<Pedidos>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pedidos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonDBcrear = findViewById<Button>(R.id.buttoncrear)
        val buttonDBmostrar = findViewById<Button>(R.id.buttonmostrar)
        val editTextproducto = findViewById<EditText>(R.id.editTextprod)
        val editTextcantidad = findViewById<EditText>(R.id.editTextcant)

        buttonDBcrear.setOnClickListener(){
            dbHandler = BaseDatos(this)
            var success: Boolean = false
            val pedidos: Pedidos = Pedidos()
            pedidos.nombre_producto = editTextproducto.text.toString() // nombre
            pedidos.cantidad = editTextcantidad.text.toString()
            success = dbHandler?.addLugar(pedidos) as Boolean
        }

        buttonDBmostrar.setOnClickListener(){
            listTasks = (dbHandler as BaseDatos).lugar

            Log.d("Datos","--->" + listTasks[0].nombre_producto)
            for(lugares in listTasks){
                //Log.d("Datos","--->" + lugares.nombre)
                Toast.makeText(this@pedidosActivity, "-->"+lugares.nombre_producto, Toast.LENGTH_SHORT).show()
                Toast.makeText(this@pedidosActivity, "-->"+lugares.cantidad, Toast.LENGTH_SHORT).show()
            }
        }

    }
}
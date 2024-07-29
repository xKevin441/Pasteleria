package com.edu.pasteleria

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextClock
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class actualizarbdActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        var dbHandler: BaseDatos? = null
        var listTasks: List<Pedidos> = ArrayList<Pedidos>()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_actualizarbd)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonBuscar = findViewById<Button>(R.id.buttonBuscar)
        val buttonActualizar = findViewById<Button>(R.id.buttonActualizar)

        val editTextproducto = findViewById<EditText>(R.id.editTextprodUpdate)
        val editTextfecha = findViewById<EditText>(R.id.editTextFecha)

        val editTextNombre = findViewById<EditText>(R.id.editTextprodUp)
        val editTextCantidad = findViewById<EditText>(R.id.editTextcantUpdate)
        val editTextecha = findViewById<EditText>(R.id.editTextFechaEntregaUpdate)
        val editTextDireccion = findViewById<EditText>(R.id.editTextDirEntregaUpdate)


        buttonBuscar.setOnClickListener(){
            dbHandler = BaseDatos(this)
            listTasks = dbHandler?.lugar ?: emptyList()
            var success: Boolean = false
            val pedidos: Pedidos = Pedidos()

            val nombreProducto = editTextproducto.text.toString() // nombre
            val fechaProducto = editTextfecha.text.toString() // Fecha entrega
            //pedidos.dir_entrega = editTextdirEntrega.text.toString()  // Direccion de entrega

            var id_encontrado = 0
            for(pedidos in listTasks){
                if (pedidos.nombre_producto == nombreProducto && pedidos.fecha == fechaProducto){
                    id_encontrado = pedidos.id
                }
            }

            if (id_encontrado != 0){
                if (pedidos.nombre_producto == nombreProducto && pedidos.fecha == fechaProducto){
                    editTextNombre.setText(pedidos.nombre_producto)
                    editTextCantidad.setText(pedidos.cantidad)
                    editTextecha.setText(pedidos.fecha)
                    editTextDireccion.setText(pedidos.dir_entrega)
                }
            }
            else{
                Toast.makeText(this@actualizarbdActivity, "Pedido no encontrado", Toast.LENGTH_SHORT).show()
            }
        }


    }
}
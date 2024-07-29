package com.edu.pasteleria

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class eliminarbdActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        var dbHandler: BaseDatos? = null
        var listTasks: List<Pedidos> = ArrayList<Pedidos>()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_eliminarbd)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editTextproducto = findViewById<EditText>(R.id.editTextprod)
        val editTextfecha = findViewById<EditText>(R.id.editTextFecha1)
        val buttonActualizar = findViewById<Button>(R.id.buttonactualizar1)
        val buttonEliminar = findViewById<Button>(R.id.buttoneliminar1)
        val buttonMostrar = findViewById<Button>(R.id.buttonmostrar1)
        val textViewMostrarPedidos = findViewById<TextView>(R.id.textViewMostrarPedidos)

        buttonActualizar.setOnClickListener(){
            dbHandler = BaseDatos(this)
            listTasks = dbHandler?.lugar ?: emptyList()
            var success: Boolean = false
            val pedidos: Pedidos = Pedidos()

            val nombreProducto = editTextproducto.text.toString() // nombre
            val fechaProducto = editTextfecha.text.toString() // Fecha entrega
            //pedidos.dir_entrega = editTextdirEntrega.text.toString()  // Direccion de entrega

            var id_eliminar = 0
            for(pedidos in listTasks){
                if (pedidos.nombre_producto == nombreProducto && pedidos.fecha == fechaProducto){
                    id_eliminar = pedidos.id
                }
            }

            if (id_eliminar != 0){
                success = dbHandler?.deleteLugar(id_eliminar)!!
                if(success) {
                    Toast.makeText(this@eliminarbdActivity, "Eliminado exitosamente", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@eliminarbdActivity, "Algo salio mal", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this@eliminarbdActivity, "Pedido no encontrado", Toast.LENGTH_SHORT).show()
            }

        }

        buttonMostrar.setOnClickListener(){
            dbHandler = BaseDatos(this)
            listTasks = dbHandler?.lugar ?: emptyList()

            val detallesConcatenados = listTasks.joinToString(separator = "\n\n") {
                "Producto: ${it.nombre_producto}\nCantidad: ${it.cantidad}\nMetodo de entrega: ${it.metodo_entrega}\n" +
                        "Fecha de entrega: ${it.fecha}\nDireccion de entrega: ${it.dir_entrega}"
            }
            textViewMostrarPedidos.text = detallesConcatenados
        }



    }
}
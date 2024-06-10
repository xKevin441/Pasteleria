package com.edu.pasteleria

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class pedidosActivity : AppCompatActivity() {
    var dbHandler: BaseDatos? = null
    var listTasks: List<Pedidos> = ArrayList<Pedidos>()

    @SuppressLint("MissingInflatedId", "WrongViewCast")
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
        val textPedidos = findViewById<TextView>(R.id.textViewPedidos)
        val buttonEliminar = findViewById<Button>(R.id.buttonEliminar)

        val radioGroup = findViewById<RadioGroup>(R.id.RadioGroup)
        val selectedRadioButtonId = radioGroup.checkedRadioButtonId
        val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)

        val editTextFecha = findViewById<EditText>(R.id.editTextFechaEntrega)

        buttonDBcrear.setOnClickListener(){
            dbHandler = BaseDatos(this)
            var success: Boolean = false
            val pedidos: Pedidos = Pedidos()

            pedidos.nombre_producto = editTextproducto.text.toString() // nombre
            pedidos.cantidad = editTextcantidad.text.toString() // cantidad
            pedidos.metodo_entrega = "Entrega a domicilio"  // método de entrega
            pedidos.fecha = editTextFecha.text.toString() // Fecha entrega

            success = dbHandler?.addLugar(pedidos) as Boolean
            if(success) {
                // Aquí puedes agregar código para manejar el caso de éxito
                Toast.makeText(this@pedidosActivity, "Guardado exitosamente", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@pedidosActivity, "Algo salio mal", Toast.LENGTH_SHORT).show()
            }
        }

        buttonDBmostrar.setOnClickListener(){
            // Obtener la lista de lugares desde la base de datos
            dbHandler = BaseDatos(this)
            listTasks = dbHandler?.lugar ?: emptyList()

            // Concatenar todos los detalles en una sola cadena
            val detallesConcatenados = listTasks.joinToString(separator = "\n\n") {
                "Producto: ${it.nombre_producto}\nCantidad: ${it.cantidad}\nMetodo de entrega: ${it.metodo_entrega}\nFecha de entrega: ${it.fecha}"
                //"Producto: ${it.nombre_producto}\nCantidad: ${it.cantidad}"
            }

            // Actualizar el contenido del TextView
            textPedidos.text = detallesConcatenados

           //listTasks = (dbHandler as BaseDatos).lugar
          /*  Log.d("Datos","--->" + listTasks[0].nombre_producto)
            for(pedidos in listTasks){
                //Log.d("Datos","--->" + lugares.nombre)
                Toast.makeText(this@pedidosActivity, "-->"+pedidos.nombre_producto, Toast.LENGTH_SHORT).show()
                Toast.makeText(this@pedidosActivity, "-->"+pedidos.cantidad, Toast.LENGTH_SHORT).show()
            }*/
        }

        buttonEliminar.setOnClickListener(){
            var exito: Boolean = false
            dbHandler = BaseDatos(this)
            exito = dbHandler?.deleteAllLugares() as Boolean
            if(exito) {
                // Aquí puedes agregar código para manejar el caso de éxito
                Toast.makeText(this@pedidosActivity, "Eliminado exitosamente", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@pedidosActivity, "Algo salio mal", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
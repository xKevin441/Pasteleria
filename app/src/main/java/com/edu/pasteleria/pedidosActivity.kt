package com.edu.pasteleria

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
        val editTextFecha = findViewById<EditText>(R.id.editTextFechaEntrega)
        val radioGroup = findViewById<RadioGroup>(R.id.RadioGroup)
        val editTextdirEntrega = findViewById<EditText>(R.id.editTextDirEntrega)

        val buttonEliminar = findViewById<Button>(R.id.buttonEliminar)
        val buttonActualizar = findViewById<Button>(R.id.buttonActualizar)

        buttonDBcrear.setOnClickListener(){
            dbHandler = BaseDatos(this)
            var success: Boolean = false
            val pedidos: Pedidos = Pedidos()

            val selectedOptionId = radioGroup.checkedRadioButtonId
            val selectedRadioButton = findViewById<RadioButton>(selectedOptionId)
            //val textoRadioButton = selectedRadioButton.text.toString()

            pedidos.nombre_producto = editTextproducto.text.toString() // nombre
            pedidos.cantidad = editTextcantidad.text.toString() // cantidad
            pedidos.metodo_entrega = selectedRadioButton.text.toString() // método de entrega
            pedidos.fecha = editTextFecha.text.toString() // Fecha entrega
            pedidos.dir_entrega = editTextdirEntrega.text.toString()  // Direccion de entrega

            success = dbHandler?.addLugar(pedidos) as Boolean
            if(success) {
                // Aquí puedes agregar código para manejar el caso de éxito
                Toast.makeText(this@pedidosActivity, "Guardado exitosamente", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@pedidosActivity, "Algo salio mal", Toast.LENGTH_SHORT).show()
            }
        }

        buttonDBmostrar.setOnClickListener(){
            dbHandler = BaseDatos(this)
            listTasks = dbHandler?.lugar ?: emptyList()

            val detallesConcatenados = listTasks.joinToString(separator = "\n\n") {
                "Producto: ${it.nombre_producto}\nCantidad: ${it.cantidad}\nMetodo de entrega: ${it.metodo_entrega}\n" +
                        "Fecha de entrega: ${it.fecha}\nDireccion de entrega: ${it.dir_entrega}"
            }
            textPedidos.text = detallesConcatenados
        }


      /* buttonEliminar.setOnClickListener(){
            var exito: Boolean = false
            dbHandler = BaseDatos(this)
            exito = dbHandler?.deleteAllLugares() as Boolean
            if(exito) {
                Toast.makeText(this@pedidosActivity, "Eliminado exitosamente", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@pedidosActivity, "Algo salio mal", Toast.LENGTH_SHORT).show()
            }
        }*/

        buttonActualizar.setOnClickListener(){
            intent = Intent(this@pedidosActivity, actualizarbdActivity::class.java)
            startActivity(intent)
        }

        buttonEliminar.setOnClickListener(){
            val intent = Intent(this@pedidosActivity, eliminarbdActivity::class.java)
            startActivity(intent)
        }
    }
}
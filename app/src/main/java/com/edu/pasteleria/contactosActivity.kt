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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class contactosActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mapView: MapView
    private lateinit var mMap: GoogleMap
    //private lateinit var binding: ActivityMapsBinding
    var dbHandler: BaseDatosClientes? = null
    var listTasks: List<Clientes> = ArrayList<Clientes>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contactos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mapView = findViewById(R.id.mapView1)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        val editTextNombre = findViewById<EditText>(R.id.editTextname)
        val editTextEmail = findViewById<EditText>(R.id.editTextemail)
        val buttonEnviar = findViewById<Button>(R.id.send_button)
        val buttonMostrarClientes = findViewById<Button>(R.id.button_mostrar_clientes)
        val textMostrarClientes = findViewById<TextView>(R.id.textMostrarClientes)

        buttonEnviar.setOnClickListener(){
            dbHandler = BaseDatosClientes(this)
            var success: Boolean = false
            val clientes: Clientes = Clientes()

            clientes.nombre = editTextNombre.text.toString() // nombre
            clientes.correo = editTextEmail.text.toString() // email

            success = dbHandler?.addLugar(clientes) as Boolean
            if(success) {
                // Aquí puedes agregar código para manejar el caso de éxito
                Toast.makeText(this@contactosActivity, "Guardado exitosamente", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@contactosActivity, "Algo salio mal", Toast.LENGTH_SHORT).show()
            }
        }

        buttonMostrarClientes.setOnClickListener(){
            dbHandler = BaseDatosClientes(this)
            listTasks = dbHandler?.lugar ?: emptyList()

            val detallesConcatenados = listTasks.joinToString(separator = "\n\n") {
                " Nombre: ${it.nombre}\n E-mail: ${it.correo}\n"
            }
            textMostrarClientes.text = detallesConcatenados
        }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val Principal = LatLng(-19.049209, -65.259841)
        mMap.addMarker(MarkerOptions().position(Principal).title("Pasteleria principal"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Principal, 14f))

        val Sucursal1 = LatLng(-19.046419, -65.252256)
        mMap.addMarker(MarkerOptions().position(Sucursal1).title("CupCakes Sucursal"))

        val Sucursal2 = LatLng(-19.043374, -65.263479)
        mMap.addMarker(MarkerOptions().position(Sucursal2).title("CupCakes sucursal"))

        val Sucursal3 = LatLng(-19.045108, -65.257826)
        mMap.addMarker(MarkerOptions().position(Sucursal3).title("CupCakes Sucursal"))

        val Sucursal4 = LatLng(-19.047087, -65.263557)
        mMap.addMarker(MarkerOptions().position(Sucursal4).title("CupCakes Sucursal"))
    }
}
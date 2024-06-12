package com.edu.pasteleria

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatosClientes(context: Context): SQLiteOpenHelper(context,BaseDatosClientes.NOMBRE_BASE_DATOS_CLIENTES,null,BaseDatosClientes.VERSION_BASE_DATOS_CLIENTES) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREAR_TABLA = "CREATE TABLE $NOMBRE_TABLA ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $NOMBRE TEXT, $CORREO TEXT);"
        db?.execSQL(CREAR_TABLA)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val BORRAR_TABLA = "DROP TABLE IF EXISTS $NOMBRE_TABLA"
        db?.execSQL(BORRAR_TABLA)
        onCreate(db)
    }

    fun addLugar(clientes: Clientes):Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NOMBRE, clientes.nombre)
        values.put(CORREO, clientes.correo)
        val _success = db.insert(NOMBRE_TABLA,null,values)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }

    @SuppressLint("Range")
    fun getLugar1(_id: Integer): Clientes{
        val clientes = Clientes()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $NOMBRE_TABLA WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor != null){
            cursor.moveToFirst()
            while(cursor.moveToNext()){
                clientes.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                clientes.nombre = cursor.getString(cursor.getColumnIndex(NOMBRE))
                clientes.correo = cursor.getString(cursor.getColumnIndex(CORREO))
            }
        }
        cursor.close()
        return clientes
    }

    val lugar: List<Clientes> @SuppressLint("Range")
    get(){
        val pedidosList = ArrayList<Clientes>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $NOMBRE_TABLA"
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor != null){
            cursor.moveToFirst()
            while(cursor.moveToNext()){
                val clientes = Clientes()
                clientes.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                clientes.nombre = cursor.getString(cursor.getColumnIndex(NOMBRE))
                clientes.correo = cursor.getString(cursor.getColumnIndex(CORREO))
                pedidosList.add(clientes)
            }
        }
        cursor.close()
        return pedidosList
    }

    fun updateLugar1(clientes: Clientes): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NOMBRE,clientes.nombre)
        values.put(CORREO,clientes.correo)
        val _success = db.update(NOMBRE_TABLA,values,ID+"=?",arrayOf(clientes.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteLugar1(_id: Int): Boolean{
        val db = this.writableDatabase
        val _success = db.delete(NOMBRE_TABLA,ID+"=?",arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteAllLugares1(): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(NOMBRE_TABLA, null, null).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    companion object{
        private val VERSION_BASE_DATOS_CLIENTES = 1
        private val NOMBRE_BASE_DATOS_CLIENTES = "dbclientes"
        private val NOMBRE_TABLA = "clientes"
        private val ID = "id"
        private val NOMBRE = "nombre_producto"
        private val CORREO = "correo"
    }
}
package com.edu.pasteleria

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(context: Context): SQLiteOpenHelper(context,BaseDatos.NOMBRE_BASE_DATOS,null,BaseDatos.VERSION_BASE_DATOS) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREAR_TABLA = "CREATE TABLE $NOMBRE_TABLA ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $NOMBRE TEXT, $CANTIDAD INT, $METODO_ENTREGA TEXT, $FECHA TEXT, $DIR_ENTREGA TEXT);"
        db?.execSQL(CREAR_TABLA)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val BORRAR_TABLA = "DROP TABLE IF EXISTS $NOMBRE_TABLA"
        db?.execSQL(BORRAR_TABLA)
        onCreate(db)
    }

    fun addLugar(pedidos: Pedidos):Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NOMBRE, pedidos.nombre_producto)
        values.put(CANTIDAD, pedidos.cantidad)
        values.put(METODO_ENTREGA, pedidos.metodo_entrega)
        values.put(FECHA, pedidos.fecha)
        values.put(DIR_ENTREGA, pedidos.dir_entrega)
        val _success = db.insert(NOMBRE_TABLA,null,values)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }

    @SuppressLint("Range")
    fun getLugar(_id: Integer): Pedidos{
        val pedidos = Pedidos()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $NOMBRE_TABLA WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor != null){
            cursor.moveToFirst()
            while(cursor.moveToNext()){
                pedidos.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                pedidos.nombre_producto = cursor.getString(cursor.getColumnIndex(NOMBRE))
                pedidos.cantidad = cursor.getString(cursor.getColumnIndex(CANTIDAD))
                pedidos.metodo_entrega = cursor.getString(cursor.getColumnIndex(METODO_ENTREGA))
                pedidos.fecha = cursor.getString(cursor.getColumnIndex(FECHA))
                pedidos.dir_entrega = cursor.getString(cursor.getColumnIndex(DIR_ENTREGA))
            }
        }
        cursor.close()
        return pedidos
    }

    val lugar: List<Pedidos> @SuppressLint("Range")
    get(){
        val pedidosList = ArrayList<Pedidos>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $NOMBRE_TABLA"
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor != null){
            cursor.moveToFirst()
            while(cursor.moveToNext()){
                val pedidos = Pedidos()
                pedidos.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                pedidos.nombre_producto = cursor.getString(cursor.getColumnIndex(NOMBRE))
                pedidos.cantidad = cursor.getString(cursor.getColumnIndex(CANTIDAD))
                pedidos.metodo_entrega = cursor.getString(cursor.getColumnIndex(METODO_ENTREGA))
                pedidos.fecha = cursor.getString(cursor.getColumnIndex(FECHA))
                pedidos.dir_entrega = cursor.getString(cursor.getColumnIndex(DIR_ENTREGA))
                pedidosList.add(pedidos)
            }
        }
        cursor.close()
        return pedidosList
    }

    fun updateLugar(pedidos: Pedidos): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NOMBRE,pedidos.nombre_producto)
        values.put(CANTIDAD,pedidos.cantidad)
        values.put(METODO_ENTREGA, pedidos.metodo_entrega)
        values.put(FECHA, pedidos.fecha)
        values.put(DIR_ENTREGA, pedidos.dir_entrega)
        val _success = db.update(NOMBRE_TABLA,values,ID+"=?",arrayOf(pedidos.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteLugar(_id: Int): Boolean{
        val db = this.writableDatabase
        val _success = db.delete(NOMBRE_TABLA,ID+"=?",arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteAllLugares(): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(NOMBRE_TABLA, null, null).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    companion object{
        private val VERSION_BASE_DATOS = 3
        private val NOMBRE_BASE_DATOS = "dbsis104"
        private val NOMBRE_TABLA = "pedidos"
        private val ID = "id"
        private val NOMBRE = "nombre_producto"
        private val CANTIDAD = "cantidad"
        private val METODO_ENTREGA = "metodo_entrega"
        private val FECHA = "fecha"
        private val DIR_ENTREGA = "dir_entrega"
    }
}
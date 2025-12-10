package com.example.practicasqlite

import android.content.ContentValues
import android.database.Cursor
import android.util.Log

class CRUD(private val dbHelper: DatabaseHelper) {

    private val TAG = "CRUD_Operations"

    // --- 1. INSERTAR (Create) ---
    fun insertData(name: String, value: Double): Boolean {
        // Obtenemos la base de datos escribible
        val db = dbHelper.writableDatabase

        val contentValues = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NAME, name)
            put(DatabaseHelper.COLUMN_VALUE, value)
        }
        val success = db.insert(DatabaseHelper.TABLE_NAME, null, contentValues)
        db.close()

        if (success != -1L) {
            Log.i(TAG, "Inserción exitosa: ID $success, Nombre: $name")
        } else {
            Log.e(TAG, "Fallo en la inserción de: $name")
        }
        return success != -1L
    }

    // --- 2. SELECCIONAR/LEER (Read) ---
    fun readData(): Cursor? {
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_NAME}", null)

        if (cursor != null) {
            Log.i(TAG, "Consulta de datos exitosa. Resultados: ${cursor.count}")
        }
        return cursor
    }

    // --- 3. ACTUALIZAR (Update) ---
    fun updateData(id: Int, newName: String, newValue: Double): Int {
        // Obtenemos la base de datos escribible
        val db = dbHelper.writableDatabase

        val contentValues = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NAME, newName)
            put(DatabaseHelper.COLUMN_VALUE, newValue)
        }

        // Actualizamos
        val rowsAffected = db.update(
            DatabaseHelper.TABLE_NAME,
            contentValues,
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
        db.close()

        if (rowsAffected > 0) {
            Log.w(TAG, "Actualización exitosa. ID: $id, Filas afectadas: $rowsAffected")
        } else {
            Log.w(TAG, "No se encontró ID $id para actualizar.")
        }
        return rowsAffected
    }

    // --- 4. ELIMINAR (Delete) ---
    fun deleteData(id: Int): Int {
        val db = dbHelper.writableDatabase

        // Eliminamos
        val rowsAffected = db.delete(
            DatabaseHelper.TABLE_NAME,
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
        db.close()

        if (rowsAffected > 0) {
            Log.w(TAG, "Eliminación exitosa. ID: $id, Filas eliminadas: $rowsAffected")
        } else {
            Log.w(TAG, "No se encontró ID $id para eliminar.")
        }
        return rowsAffected
    }
}
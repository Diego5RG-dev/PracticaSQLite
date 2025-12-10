package com.example.practicasqlite
import android.app.Activity
import android.os.Bundle
import android.util.Log

class MainActivity : Activity() {

    private val TAG = "MainActivity"
    private lateinit var crud: CRUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbHelper = DatabaseHelper(this)
        crud = CRUD(dbHelper)

        Log.d(TAG, "Insertar datos")
        crud.insertData("Objeto X", 100.0) // ID 1
        crud.insertData("Objeto Y", 200.0) // ID 2
        crud.insertData("Objeto Z", 300.0) // ID 3

        // 2. ACTUALIZAR (Update)
        Log.d(TAG, "Actualizar datos")
        crud.updateData(2, "Objeto Y Actualizado", 250.50)

        // 3. ELIMINAR (Delete)
        Log.d(TAG, "Eliminar datos")
        crud.deleteData(3)

        // 4. CONSULTAR TODOS (Read All)
        Log.d(TAG, "Leer datos")
        readAndLogAllData()
    }

    private fun readAndLogAllData() {
        val cursor = crud.readData()

        cursor?.let {
            if (it.moveToFirst()) {
                do {
                    val id = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID))
                    val name = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME))
                    val value = it.getDouble(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_VALUE))
                    Log.v(TAG, "FINAL DB -> ID: $id, Nombre: $name, Valor: $value")
                } while (it.moveToNext())
            } else {
                Log.w(TAG, "No se encontraron datos después de las operaciones.")
            }
            it.close()
        }
    }

}
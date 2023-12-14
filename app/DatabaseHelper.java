import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class DatabaseHelper extends AppCompatActivity {

    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Crear o abrir la base de datos
        mDatabase = openOrCreateDatabase("NombreDeLaBaseDeDatos", MODE_PRIVATE, null);
        createTable(); // Método para crear la tabla si no existe

        // Insertar datos
        ContentValues values = new ContentValues();
        values.put("nombre", "Ejemplo");
        mDatabase.insert("MiTabla", null, values);

        // Consultar datos
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM MiTabla", null);
        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                // Hacer algo con los datos obtenidos
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void createTable() {
        // Crear la tabla si no existe
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS MiTabla (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT)");
    }
}

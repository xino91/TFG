package com.example.apprpe.modelo.DB;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.modelo.Ent_Realizado;
import com.example.apprpe.modelo.Entrenamiento;
import com.example.apprpe.modelo.EntrenamientoDao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Ejercicio.class, Entrenamiento.class, Ent_Realizado.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract  class RPERoomDatabase extends RoomDatabase {

    private static volatile RPERoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public abstract EntrenamientoDao entrenamientoDao();
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RPERoomDatabase getInstanceDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RPERoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RPERoomDatabase.class, "AppRpe_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.

                EntrenamientoDao dao_entrenamiento = INSTANCE.entrenamientoDao();
                List<Entrenamiento> entrenamientos = CrearEntrenamientosEjemplo();
                List<Ejercicio> ejercicios = CrearEjerciciosEjemplo();

                for(int i=0; i<entrenamientos.size(); i++){
                    dao_entrenamiento.insert(entrenamientos.get(i));
                }
                for(int i=0; i<ejercicios.size(); i++){
                    dao_entrenamiento.insert(ejercicios.get(i));
                }

                long time = System.currentTimeMillis();
                Date date = new Date(time);
                Log.i("FECHA", date.toString());
                Ent_Realizado entrealizado = new Ent_Realizado(1,"Entrenamiento Avanzado", date , 2400, "Fuerza", 240, "07:00", "07:40", 5, 6, 3, 0 );
                dao_entrenamiento.insert(entrealizado);
            });
        }
    };

    public static List<Entrenamiento> CrearEntrenamientosEjemplo(){
        List<Entrenamiento> entrenamiento = new ArrayList<Entrenamiento>();
        entrenamiento.add(new Entrenamiento(1,"Entrenamiento Básico", 2, 5, "Fuerza"));
        entrenamiento.add(new Entrenamiento(2,"Entrenamiento Intermedio", 1,  6,"Fuerza"));
        entrenamiento.add(new Entrenamiento(3,"Entrenamiento Avanzado", 3, 8,"Fuerza"));
        entrenamiento.add(new Entrenamiento(4,"Entrenamiento Aeróbico", 2, 4, "Aeróbico"));
        return entrenamiento;
    }

    public static List<Ejercicio> CrearEjerciciosEjemplo(){
        List<Ejercicio> ejercicio = new ArrayList<Ejercicio>();
        ejercicio.add(new Ejercicio(1,"Flexiones",12 , 2, 5 ,1));
        ejercicio.add(new Ejercicio(2,"Flexiones",20 , 2, 8 ,3));
        ejercicio.add(new Ejercicio(3,"Abdominales",20 , 2, 4 ,1));
        ejercicio.add(new Ejercicio(4,"Abdominales",20 , 3, 7 ,3));
        ejercicio.add(new Ejercicio(5,"Muscle up",5 , 2, 6 ,2));
        ejercicio.add(new Ejercicio(6,"Muscle up",20 , 3, 7 ,3));
        ejercicio.add(new Ejercicio(7,"Footing",0 , 0, 4 ,4));
        ejercicio.add(new Ejercicio(8,"Salto a la comba",0 , 0, 5 ,4));
        return ejercicio;
    }
}
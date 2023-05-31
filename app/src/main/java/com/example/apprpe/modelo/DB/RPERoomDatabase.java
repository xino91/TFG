package com.example.apprpe.modelo.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.modelo.Ent_Realizado;
import com.example.apprpe.modelo.Entrenamiento;
import com.example.apprpe.modelo.RpeDao;
import com.example.apprpe.modelo.Peso;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Ejercicio.class, Entrenamiento.class, Ent_Realizado.class, Peso.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract  class RPERoomDatabase extends RoomDatabase {
    private static volatile RPERoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public abstract RpeDao entrenamientoDao();
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

    public static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.

                RpeDao dao_entrenamiento = INSTANCE.entrenamientoDao();
                List<Entrenamiento> entrenamientos = CrearEntrenamientosEjemplo();
                List<Ejercicio> ejercicios = CrearEjerciciosEjemplo();
                List<Ent_Realizado> entrealizados = CrearEntRealizadosEjemplo();
                List<Peso> historialPeso = CrearHistorialPeso();

                for(int i=0; i<entrenamientos.size(); i++){
                    dao_entrenamiento.insert(entrenamientos.get(i));
                }
                for(int i=0; i<ejercicios.size(); i++){
                    dao_entrenamiento.insert(ejercicios.get(i));
                }
                for(int i=0; i<entrealizados.size(); i++){
                    dao_entrenamiento.insert(entrealizados.get(i));
                }
                for(int i=0; i<historialPeso.size(); i++){
                    dao_entrenamiento.insert(historialPeso.get(i));
                }
            });
        }
    };

    public static void recreateDatabase(Context context) {
        // Eliminar la instancia existente de la base de datos
        INSTANCE = null;

        // Borrar el archivo de la base de datos
        context.deleteDatabase("AppRpe_database");
    }

    public static List<Entrenamiento> CrearEntrenamientosEjemplo(){
        List<Entrenamiento> entrenamiento = new ArrayList<Entrenamiento>();
        entrenamiento.add(new Entrenamiento(1,"Entrenamiento Lunes", 2, 6, "Fuerza"));
        entrenamiento.add(new Entrenamiento(2,"Entrenamiento Martes", 1,  5,"Fuerza"));
        entrenamiento.add(new Entrenamiento(3,"Entrenamiento Miércoles", 3, 8,"Fuerza"));
        entrenamiento.add(new Entrenamiento(4,"Entrenamiento Jueves", 2, 0, "Aeróbico"));
        entrenamiento.add(new Entrenamiento(5,"Entrenamiento Viernes", 0, 6, "Fuerza"));
        entrenamiento.add(new Entrenamiento(6,"Entrenamiento Sábado", 0, 4, "Fuerza"));
        entrenamiento.add(new Entrenamiento(7,"Entrenamiento Domingo", 0, 4, "Aeróbico"));
        return entrenamiento;
    }

    public static List<Ejercicio> CrearEjerciciosEjemplo(){
        List<Ejercicio> ejercicio = new ArrayList<Ejercicio>();
        ejercicio.add(new Ejercicio(1,"Flexiones",12 , 2, 5, 0 ,1));
        ejercicio.add(new Ejercicio(2,"Flexiones",20 , 2, 8 ,0 ,3));
        ejercicio.add(new Ejercicio(3,"Abdominales",20 , 2, 4 , 0 ,1));
        ejercicio.add(new Ejercicio(4,"Abdominales",20 , 3, 7, 0 ,3));
        ejercicio.add(new Ejercicio(5,"Muscle up",5 , 2, 6, 0 ,2));
        ejercicio.add(new Ejercicio(6,"Muscle up",20 , 3, 7, 0 ,3));
        ejercicio.add(new Ejercicio(7,"Footing",0 , 1, 4, 20 ,4));
        ejercicio.add(new Ejercicio(8,"Salto a la comba",0 , 2, 5 , 12,4));
        return ejercicio;
    }

    public static List<Ent_Realizado> CrearEntRealizadosEjemplo(){
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        List<Ent_Realizado> entrealizados = new ArrayList<>();

        entrealizados.add(new Ent_Realizado(1,"Entrenamiento Lunes", date, 3000, "Fuerza", 280, "07:00", "07:50", 6, 7, 4, 0 ));
        entrealizados.add(new Ent_Realizado(2,"Entrenamiento Martes", date, 2400, "Fuerza", 180, "07:00", "07:40", 4, 4, 4, 0 ));
        entrealizados.add(new Ent_Realizado(3,"Entrenamiento Miércoles", date, 4200, "Fuerza", 360, "07:00", "08:10", 8, 9, 2, 1 ));
        entrealizados.add(new Ent_Realizado(4,"Entrenamiento Jueves", date, 0, "Aeróbico", 0, "07:00", "07:00", 0, 0, 5, 0 ));
        entrealizados.add(new Ent_Realizado(5,"Entrenamiento Viernes", date, 3000, "Fuerza", 260, "07:00", "07:50", 6, 6, 4, 0 ));
        entrealizados.add(new Ent_Realizado(6,"Entrenamiento Sábado", date, 2400, "Fuerza", 230, "07:00", "07:40", 4, 5, 3, 0 ));
        entrealizados.add(new Ent_Realizado(7,"Entrenamiento Domingo", date, 1200, "Aeróbico", 150, "07:00", "07:20", 3, 3, 4, 0 ));
        entrealizados.add(new Ent_Realizado(8,"Entrenamiento Lunes", date, 3000, "Fuerza", 280, "07:00", "07:50", 6, 7, 4, 0 ));
        entrealizados.add(new Ent_Realizado(9,"Entrenamiento Martes", date, 2400, "Fuerza", 180, "07:00", "07:40", 4, 4, 4, 0 ));
        entrealizados.add(new Ent_Realizado(10,"Entrenamiento Miércoles", date, 4200, "Fuerza", 360, "07:00", "08:10", 8, 9, 2, 1 ));
        entrealizados.add(new Ent_Realizado(11,"Entrenamiento Jueves", date, 0, "Aeróbico", 0, "07:00", "07:00", 0, 0, 5, 0 ));
        entrealizados.add(new Ent_Realizado(12,"Entrenamiento Viernes", date, 3000, "Fuerza", 260, "07:00", "07:50", 6, 6, 4, 0 ));
        entrealizados.add(new Ent_Realizado(13,"Entrenamiento Sábado", date, 2400, "Fuerza", 230, "07:00", "07:40", 4, 5, 3, 0 ));
        entrealizados.add(new Ent_Realizado(14,"Entrenamiento Domingo", date, 1200, "Aeróbico", 150, "07:00", "07:20", 3, 3, 4, 0 ));
        entrealizados.add(new Ent_Realizado(15,"Entrenamiento Lunes", date, 3000, "Fuerza", 280, "07:00", "07:50", 6, 7, 4, 0 ));
        entrealizados.add(new Ent_Realizado(16,"Entrenamiento Martes", date, 2400, "Fuerza", 180, "07:00", "07:40", 4, 4, 4, 0 ));
        entrealizados.add(new Ent_Realizado(17,"Entrenamiento Miércoles", date, 4200, "Fuerza", 360, "07:00", "08:10", 8, 8, 2, 1 ));
        entrealizados.add(new Ent_Realizado(18,"Entrenamiento Jueves", date, 0, "Aeróbico", 0, "07:00", "07:00", 0, 0, 5, 0 ));
        entrealizados.add(new Ent_Realizado(19,"Entrenamiento Viernes", date, 3000, "Fuerza", 260, "07:00", "07:50", 6, 6, 4, 0 ));
        entrealizados.add(new Ent_Realizado(20,"Entrenamiento Sábado", date, 2400, "Fuerza", 230, "07:00", "07:40", 4, 5, 3, 0 ));
        entrealizados.add(new Ent_Realizado(21,"Entrenamiento Domingo", date, 1200, "Aeróbico", 150, "07:00", "07:20", 3, 3, 4, 0 ));
        return entrealizados;
    }

    public static List<Peso> CrearHistorialPeso(){
        List<Peso> pesos = new ArrayList<Peso>();
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        pesos.add(new Peso(1,75.1,date));
        pesos.add(new Peso(2,75.2,date));
        pesos.add(new Peso(3,75.4,date));
        pesos.add(new Peso(4,75.3,date));
        pesos.add(new Peso(5,75.3,date));
        pesos.add(new Peso(6,75.5,date));
        pesos.add(new Peso(7,75.9,date));
        pesos.add(new Peso(8,76.1,date));
        return pesos;
    }
}
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
import com.example.apprpe.modelo.EntrenamientoDao;
import com.example.apprpe.modelo.Peso;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Ejercicio.class, Entrenamiento.class, Ent_Realizado.class, Peso.class}, version = 1, exportSchema = false)
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

    public static List<Ent_Realizado> CrearEntRealizadosEjemplo(){
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        List<Ent_Realizado> entrealizados = new ArrayList<>();

        entrealizados.add(new Ent_Realizado(1,"Entrenamiento Avanzado", date, 2400, "Fuerza", 340, "07:00", "07:40", 5, 6, 4, 0 ));
        entrealizados.add(new Ent_Realizado(2,"Entrenamiento Avanzado", date, 2400, "Fuerza", 255, "07:00", "07:40", 5, 5, 4, 0 ));
        entrealizados.add(new Ent_Realizado(3,"Entrenamiento Intermedio", date, 2400, "Fuerza", 240, "07:00", "07:40", 4, 5, 3, 0 ));
        entrealizados.add(new Ent_Realizado(4,"Entrenamiento Intermedio", date, 2400, "Fuerza", 260, "07:00", "07:40", 5, 6, 3, 0 ));
        entrealizados.add(new Ent_Realizado(5,"Entrenamiento Avanzado", date, 2400, "Fuerza", 240, "07:00", "07:40", 7, 7, 3, 1 ));
        entrealizados.add(new Ent_Realizado(6,"Entrenamiento Aeróbico", date, 2400, "Aeróbico", 240, "07:00", "07:40", 5, 5, 3, 0 ));
        entrealizados.add(new Ent_Realizado(7,"Entrenamiento Avanzado", date, 2400, "Fuerza", 240, "07:00", "07:40", 7, 8, 3, 1 ));
        entrealizados.add(new Ent_Realizado(8,"Entrenamiento Básico", date, 2400, "Fuerza", 220, "07:00", "07:40", 5, 5, 3, 0 ));
        entrealizados.add(new Ent_Realizado(9,"Entrenamiento Básico", date, 2400, "Fuerza", 180, "07:00", "07:40", 4, 5, 3, 0 ));
        entrealizados.add(new Ent_Realizado(10,"Entrenamiento Avanzado", date, 2400, "Fuerza", 310, "07:00", "07:40", 7, 7, 3, 0 ));
        entrealizados.add(new Ent_Realizado(11,"Entrenamiento Aeróbico", date, 2400, "Aeróbico", 240, "07:00", "07:40", 5, 4, 5, 0 ));
        entrealizados.add(new Ent_Realizado(12,"Entrenamiento Avanzado", date, 2400, "Fuerza", 240, "07:00", "07:40", 7, 7, 3, 0 ));
        entrealizados.add(new Ent_Realizado(13,"Entrenamiento Básico", date, 2400, "Fuerza", 240, "07:00", "07:40", 4, 5, 3, 0 ));
        entrealizados.add(new Ent_Realizado(14,"Entrenamiento Intermedio", date, 2400, "Fuerza", 240, "07:00", "07:40", 5, 6, 3, 0 ));
        entrealizados.add(new Ent_Realizado(15,"Entrenamiento Avanzado", date, 2400, "Fuerza", 240, "07:00", "07:40", 8, 9, 3, 3 ));
        entrealizados.add(new Ent_Realizado(16,"Entrenamiento Básico", date, 2400, "Fuerza", 240, "07:00", "07:40", 5, 5, 5, 0 ));
        entrealizados.add(new Ent_Realizado(17,"Entrenamiento Avanzado", date, 2400, "Fuerza", 240, "07:00", "07:40", 5, 6, 4, 0 ));
        entrealizados.add(new Ent_Realizado(18,"Entrenamiento Avanzado", date, 2400, "Fuerza", 240, "07:00", "07:40", 5, 5, 4, 0 ));
        entrealizados.add(new Ent_Realizado(19,"Entrenamiento Intermedio", date, 2400, "Fuerza", 250, "07:00", "07:40", 4, 5, 3, 0 ));
        entrealizados.add(new Ent_Realizado(20,"Entrenamiento Intermedio", date, 2400, "Fuerza", 278, "07:00", "07:40", 5, 6, 3, 0 ));
        entrealizados.add(new Ent_Realizado(21,"Entrenamiento Avanzado", date, 2400, "Fuerza", 240, "07:00", "07:40", 7, 7, 3, 1 ));
        entrealizados.add(new Ent_Realizado(22,"Entrenamiento Aeróbico", date, 2400, "Aeróbico", 240, "07:00", "07:40", 5, 5, 3, 0 ));
        entrealizados.add(new Ent_Realizado(23,"Entrenamiento Avanzado", date, 2400, "Fuerza", 240, "07:00", "07:40", 7, 8, 3, 1 ));
        entrealizados.add(new Ent_Realizado(24,"Entrenamiento Básico", date, 2400, "Fuerza", 245, "07:00", "07:40", 5, 5, 3, 0 ));
        entrealizados.add(new Ent_Realizado(25,"Entrenamiento Básico", date, 2400, "Fuerza", 240, "07:00", "07:40", 4, 5, 3, 0 ));
        entrealizados.add(new Ent_Realizado(26,"Entrenamiento Avanzado", date, 2400, "Fuerza", 240, "07:00", "07:40", 7, 7, 3, 0 ));
        entrealizados.add(new Ent_Realizado(27,"Entrenamiento Aeróbico", date, 2400, "Aeróbico", 240, "07:00", "07:40", 5, 4, 5, 0 ));
        entrealizados.add(new Ent_Realizado(28,"Entrenamiento Avanzado", date, 2400, "Fuerza", 240, "07:00", "07:40", 7, 7, 3, 0 ));
        entrealizados.add(new Ent_Realizado(29,"Entrenamiento Básico", date, 2400, "Fuerza", 240, "07:00", "07:40", 4, 5, 3, 0 ));
        entrealizados.add(new Ent_Realizado(30,"Entrenamiento Intermedio", date, 2400, "Fuerza", 240, "07:00", "07:40", 5, 6, 3, 0 ));
        entrealizados.add(new Ent_Realizado(31,"Entrenamiento Avanzado", date, 2400, "Fuerza", 240, "07:00", "07:40", 7, 7, 3, 0 ));
        entrealizados.add(new Ent_Realizado(32,"Entrenamiento Aeróbico", date, 2400, "Aeróbico", 240, "07:00", "07:40", 5, 4, 5, 0 ));
        entrealizados.add(new Ent_Realizado(33,"Entrenamiento Avanzado", date, 2400, "Fuerza", 240, "07:00", "07:40", 7, 7, 3, 0 ));
        entrealizados.add(new Ent_Realizado(34,"Entrenamiento Básico", date, 2400, "Fuerza", 240, "07:00", "07:40", 4, 5, 3, 0 ));
        entrealizados.add(new Ent_Realizado(35,"Entrenamiento Avanzado", date, 2400, "Fuerza", 290, "07:00", "07:40", 8, 9, 3, 3 ));
        entrealizados.add(new Ent_Realizado(36,"Entrenamiento Básico", date, 2400, "Fuerza", 240, "07:00", "07:40", 5, 5, 5, 0 ));
        entrealizados.add(new Ent_Realizado(37,"Entrenamiento Avanzado", date, 2400, "Fuerza", 240, "07:00", "07:40", 5, 6, 4, 0 ));
        entrealizados.add(new Ent_Realizado(38,"Entrenamiento Avanzado", date, 2400, "Fuerza", 240, "07:00", "07:40", 5, 5, 4, 0 ));
        entrealizados.add(new Ent_Realizado(39,"Entrenamiento Intermedio", date, 2400, "Fuerza", 240, "07:00", "07:40", 4, 5, 3, 0 ));
        entrealizados.add(new Ent_Realizado(40,"Entrenamiento Intermedio", date, 2400, "Fuerza", 240, "07:00", "07:40", 5, 6, 3, 0 ));
        entrealizados.add(new Ent_Realizado(41,"Entrenamiento Avanzado", date, 2400, "Fuerza", 240, "07:00", "07:40", 7, 7, 3, 1 ));
        entrealizados.add(new Ent_Realizado(42,"Entrenamiento Aeróbico", date, 2400, "Aeróbico", 240, "07:00", "07:40", 5, 5, 3, 0 ));
        entrealizados.add(new Ent_Realizado(43,"Entrenamiento Avanzado", date, 2400, "Fuerza", 240, "07:00", "07:40", 7, 8, 3, 1 ));
        entrealizados.add(new Ent_Realizado(44,"Entrenamiento Básico", date, 2400, "Fuerza", 240, "07:00", "07:40", 5, 5, 3, 0 ));
        entrealizados.add(new Ent_Realizado(45,"Entrenamiento Básico", date, 2400, "Fuerza", 240, "07:00", "07:40", 4, 5, 3, 0 ));
        entrealizados.add(new Ent_Realizado(46,"Entrenamiento Avanzado", date, 2400, "Fuerza", 240, "07:00", "07:40", 7, 7, 3, 0 ));
        entrealizados.add(new Ent_Realizado(47,"Entrenamiento Aeróbico", date, 2400, "Aeróbico", 240, "07:00", "07:40", 5, 4, 5, 0 ));
        entrealizados.add(new Ent_Realizado(48,"Entrenamiento Avanzado", date, 2400, "Fuerza", 240, "07:00", "07:40", 7, 7, 3, 0 ));
        entrealizados.add(new Ent_Realizado(49,"Entrenamiento Básico", date, 2400, "Fuerza", 240, "07:00", "07:40", 4, 5, 3, 0 ));
        entrealizados.add(new Ent_Realizado(50,"Entrenamiento Intermedio", date, 2400, "Fuerza", 240, "07:00", "07:40", 5, 6, 3, 0 ));
        entrealizados.add(new Ent_Realizado(51,"Entrenamiento Avanzado", date, 2400, "Fuerza", 240, "07:00", "07:40", 7, 7, 3, 0 ));
        entrealizados.add(new Ent_Realizado(52,"Entrenamiento Aeróbico", date, 2400, "Aeróbico", 170, "07:00", "07:40", 5, 4, 5, 0 ));
        entrealizados.add(new Ent_Realizado(53,"Entrenamiento Avanzado", date, 2400, "Fuerza", 324, "07:00", "07:40", 7, 7, 3, 0 ));

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
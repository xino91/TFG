package com.AntArDev.MyRpe_Assistant.modelo.DB;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.AntArDev.MyRpe_Assistant.modelo.Ejercicio;
import com.AntArDev.MyRpe_Assistant.modelo.Ent_Realizado;
import com.AntArDev.MyRpe_Assistant.modelo.Entrenamiento;
import com.AntArDev.MyRpe_Assistant.modelo.RpeDao;
import com.AntArDev.MyRpe_Assistant.modelo.Peso;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
                            //.addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        Log.e(TAG, "getInstanceDatabase: ENTRADO");
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
                    //List<Peso> historialPeso = CrearHistorialPeso();

                    for(int i=0; i<entrenamientos.size(); i++){
                        dao_entrenamiento.insert(entrenamientos.get(i));
                    }
                    for(int i=0; i<ejercicios.size(); i++){
                        dao_entrenamiento.insert(ejercicios.get(i));
                    }
                    for(int i=0; i<entrealizados.size(); i++){
                        dao_entrenamiento.insert(entrealizados.get(i));
                    }
                /*for(int i=0; i<historialPeso.size(); i++){
                    dao_entrenamiento.insert(historialPeso.get(i));
                }*/

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
        entrenamiento.add(new Entrenamiento(1,"Lunes - Pecho", 3, 6, "Fuerza"));
        entrenamiento.add(new Entrenamiento(2,"Martes - Core", 3,  5,"Fuerza"));
        entrenamiento.add(new Entrenamiento(3,"Miércoles - Tríceps y Bíceps", 3, 6,"Fuerza"));
        entrenamiento.add(new Entrenamiento(4,"Jueves - Piernas", 3, 4, "Fuerza"));
        entrenamiento.add(new Entrenamiento(5,"Viernes - Hombros", 3, 7, "Fuerza"));
        entrenamiento.add(new Entrenamiento(6,"Sábado - Descanso", 0, 0, "Fuerza"));
        entrenamiento.add(new Entrenamiento(7,"Domingo - Todo el cuerpo", 5, 8, "Fuerza"));
        return entrenamiento;
    }

    public static List<Ejercicio> CrearEjerciciosEjemplo(){
        List<Ejercicio> ejercicio = new ArrayList<Ejercicio>();
        ejercicio.add(new Ejercicio(1,"Flexiones",12 , 2, 5, 0 ,1));
        ejercicio.add(new Ejercicio(2,"Flexiones con brazos abiertos",12 , 2, 8 ,0 ,1));
        ejercicio.add(new Ejercicio(3,"Aperturas mancuernas",10 , 3, 4 , 0 ,1));
        ejercicio.add(new Ejercicio(4,"Abdominales",20 , 3, 5, 0 ,2));
        ejercicio.add(new Ejercicio(5,"Twist ruso",25 , 3, 5, 0 ,2));
        ejercicio.add(new Ejercicio(6,"Abdominales en V",20 , 2, 6, 0 ,2));
        ejercicio.add(new Ejercicio(7,"Trícep al suelo",12 , 3, 6, 0 ,3));
        ejercicio.add(new Ejercicio(8,"Curl de bíceps",12 ,2 , 6, 0,3));
        ejercicio.add(new Ejercicio(9,"Flexiones diamante",10 , 2, 5 , 0,3));
        ejercicio.add(new Ejercicio(10,"Sentadillas",15 , 2, 5 , 0,4));
        ejercicio.add(new Ejercicio(11,"Elevación de gemelos",20 , 2, 4 , 0,4));
        ejercicio.add(new Ejercicio(12,"Salto al cajón",8 , 2, 4 , 0,4));
        ejercicio.add(new Ejercicio(13,"Flexiones en pica",12 , 3, 7 , 0,5));
        ejercicio.add(new Ejercicio(14,"Apertura mancuernas",12 , 2, 7 , 0,5));
        ejercicio.add(new Ejercicio(15,"Remo",12 , 3, 7 , 0,5));
        ejercicio.add(new Ejercicio(16,"Flexiones",15 , 2, 8 , 0,7));
        ejercicio.add(new Ejercicio(17,"Rueda abdominal",15 , 2, 7 , 0,7));
        ejercicio.add(new Ejercicio(18,"Balón arriba",12 , 2, 7 , 0,7));
        ejercicio.add(new Ejercicio(19,"Remo vertical",12 , 2, 8 , 0,7));
        ejercicio.add(new Ejercicio(20,"Sentadillas",15 , 2, 8 , 0,7));

        return ejercicio;
    }

    public static List<Ent_Realizado> CrearEntRealizadosEjemplo(){
        List<Ent_Realizado> entrealizados = new ArrayList<>();

        entrealizados.add(new Ent_Realizado(1,"Lunes - Pecho", Date.valueOf(String.valueOf(LocalDate.of(2023, 5, 22))), 3000, "Fuerza", 162, "07:00", "07:50", 6, 7, 4, 1 ));
        entrealizados.add(new Ent_Realizado(2,"Martes - Core", Date.valueOf(String.valueOf(LocalDate.of(2023, 5, 23))), 2400, "Fuerza", 292, "07:00", "07:40", 5, 5, 4, 0 ));
        entrealizados.add(new Ent_Realizado(3,"Miércoles - Tríceps y Bíceps", Date.valueOf(String.valueOf(LocalDate.of(2023, 5, 24))), 4200, "Fuerza", 125, "07:00", "08:10", 6, 5, 5, 0 ));
        entrealizados.add(new Ent_Realizado(4,"Jueves - Piernas", Date.valueOf(String.valueOf(LocalDate.of(2023, 5, 25))), 0, "Fuerza", 124, "07:00", "07:00", 4, 4, 5, 0 ));
        entrealizados.add(new Ent_Realizado(5,"Viernes - Hombros", Date.valueOf(String.valueOf(LocalDate.of(2023, 5, 26))), 3000, "Fuerza", 224, "07:00", "07:50", 7, 7, 4, 0 ));
        entrealizados.add(new Ent_Realizado(6,"Sábado - Descanso", Date.valueOf(String.valueOf(LocalDate.of(2023, 5, 27))), 2400, "Fuerza", 0, "07:00", "07:40", 0, 0, 5, 0 ));
        entrealizados.add(new Ent_Realizado(7,"Domingo - Todo el cuerpo", Date.valueOf(String.valueOf(LocalDate.of(2023, 5, 28))), 1200, "Fuerza", 312, "07:00", "07:20", 8, 9, 4, 1 ));
        return entrealizados;
    }

    /*public static List<Peso> CrearHistorialPeso(){
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
    }*/
}
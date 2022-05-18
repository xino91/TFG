package com.example.apprpe;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.apprpe.modelo.Converters;
import com.example.apprpe.modelo.Ejercicio;
import com.example.apprpe.modelo.Ent_Realizado;
import com.example.apprpe.modelo.Entrenamiento;
import com.example.apprpe.modelo.EntrenamientoDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Ejercicio.class, Entrenamiento.class, Ent_Realizado.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract  class RPERoomDatabase extends RoomDatabase {

    public abstract EntrenamientoDao entrenamientoDao();

    private static volatile RPERoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static RPERoomDatabase getInstanceDatabase(final Context context) {
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
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.

                EntrenamientoDao daosesion = INSTANCE.entrenamientoDao();

                Ejercicio ejercicio = new Ejercicio("Flexiones", 2, 2, 2, 1);
                daosesion.insert(ejercicio);
                //Sesion sesion = new Sesion(0,"Sesion1", 3, 5);
                //daosesion.insert(sesion);
            });
        }
    };
}
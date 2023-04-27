package com.example.apprpe;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.not;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.apprpe.view.VistaEjerciciosActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class VistaEjerciciosActivityTest {

    @Rule
    public ActivityScenarioRule<VistaEjerciciosActivity> activityEscenarioRule = new ActivityScenarioRule<>(VistaEjerciciosActivity.class);


    @Test
    public void testButtonOpensInicioentrenamientoActivity() {

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), VistaEjerciciosActivity.class);
        intent.putExtra("POSICION", 1);

        ActivityScenario<VistaEjerciciosActivity> activityEscenario = ActivityScenario.launch(intent);

        activityEscenario.onActivity(new ActivityScenario.ActivityAction<VistaEjerciciosActivity>() {
            @Override
            public void perform(VistaEjerciciosActivity activity) {
                // Simulamos el click en el botón
                onView(withId(R.id.menu_insertar_ejercicio)).perform();

                // Verificamos que se abrió la segunda actividad
                onView(withId(R.id.activity_nuevo_ejercicio)).check(matches(isDisplayed()));
            }
        });
    }


}
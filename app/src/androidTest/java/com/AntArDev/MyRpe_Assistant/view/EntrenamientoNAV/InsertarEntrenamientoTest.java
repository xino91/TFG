package com.AntArDev.MyRpe_Assistant.view.EntrenamientoNAV;

import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.apprpe.R;
import com.AntArDev.MyRpe_Assistant.view.InsertarEntrenamiento;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test activity insertarEntrenamiento
 * InsertarEntrenamientoConDatosIncorrectos()
 * InsertarEntrenamientoConDatosCorrectos()
 * BotonCancelar()
 */
@RunWith(AndroidJUnit4.class)
public class InsertarEntrenamientoTest {

    @Rule
    public ActivityScenarioRule<InsertarEntrenamiento> activityRule = new ActivityScenarioRule<>(InsertarEntrenamiento.class);

    @Test
    public void testInsertarEntrenamientoConDatosIncorrectos() {
        // Introducimos datos incorrectos en las vistas
        onView(withId(R.id.editTextTitulo)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.editTextRPE)).perform(typeText("11"), closeSoftKeyboard());
        onView(withId(R.id.despegable)).perform(click());
        // Hacemos clic en el botón de guardar
        onView(withId(R.id.button_insertar)).perform(click());
        // Verificamos que se muestra el mensaje de error correcto en cada vista
        onView(withId(R.id.editTextTitulo)).check(matches(hasErrorText("Campo Obligatorio")));
        onView(withId(R.id.Constraint_insertar_sesion)).check(matches(isDisplayed()));
    }

    @Test
    public void testInsertarEntrenamientoConDatosCorrectos() {
        String titulo = "Entrenamiento de Fuerza";
        int rpe = 7;
        String tipo = "Fuerza";

        // Introducimos los datos en las vistas
        onView(withId(R.id.editTextTitulo)).perform(typeText(titulo), closeSoftKeyboard());
        onView(withId(R.id.editTextRPE)).perform(typeText(String.valueOf(rpe)), closeSoftKeyboard());
        onView(withId(R.id.despegable)).perform(click());
        onView(withText(tipo))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        // Hacemos clic en el botón de guardar
        onView(withId(R.id.button_insertar)).perform(click());
    }

    @Test
    public void testBotonCancelar() {
        onView(withId(R.id.button_Cancelar)).perform(click());
    }
}


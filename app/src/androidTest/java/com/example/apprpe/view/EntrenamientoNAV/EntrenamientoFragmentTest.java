package com.example.apprpe.view.EntrenamientoNAV;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.apprpe.MainActivity;
import com.example.apprpe.R;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EntrenamientoFragmentTest extends TestCase {

    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void clickItemRecyclerView() {
        //Comprobamos que existe el recyclerView y luego que podemos hacer click en el elemento cuya posición=3
        onView(withId(R.id.recyclerview_id)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerview_id)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
    }

    @Test
    public void clickFloatingActionButton_nuevoEntrenamiento(){
        onView(withId(R.id.floatingActionButton)).check(matches(isDisplayed()));
        onView(withId(R.id.floatingActionButton)).perform(ViewActions.click());
        onView(withId(R.id.Constraint_insertar_sesion)).check(matches(isDisplayed()));
    }

    @Test
    public void clickAllNavigationMenuBottom(){
        onView(withId(R.id.navigation_progreso)).perform(ViewActions.click());
        onView(withId(R.id.navigation_estadisticas)).perform(ViewActions.click());
        onView(withId(R.id.navigation_perfil)).perform(ViewActions.click());
    }
}
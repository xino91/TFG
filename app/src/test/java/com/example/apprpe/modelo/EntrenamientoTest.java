package com.example.apprpe.modelo;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import androidx.test.filters.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
@SmallTest
public class EntrenamientoTest {
    Entrenamiento entrenamiento;

    @Before
    public void createEntrenamiento(){
        assertThat(entrenamiento).isNull();
        entrenamiento = new Entrenamiento(0, "Prueba numero 1", 0, 5, "Fuerza");
    }
    @Test
    public void testgetId() {
        assertEquals(entrenamiento.getId(), 0);
        assertNotEquals(entrenamiento.getId(), 1);
    }
    @Test
    public void testgetNombre_Entrenamiento() {
        assertEquals(entrenamiento.getNombre_Entrenamiento(), "Prueba numero 1");
        assertNotEquals(entrenamiento.getNombre_Entrenamiento(), "Prueba numero");
    }
    @Test
    public void testgetNum_ejercicios() {
        assertEquals(entrenamiento.getNum_ejercicios(), 0);
        assertNotEquals(entrenamiento.getNum_ejercicios(), 1);
    }
    @Test
    public void testgetRpe_Objetivo() {
        assertEquals(entrenamiento.getRpe_Objetivo(), 5);
        assertNotEquals(entrenamiento.getRpe_Objetivo(), 6);
    }
    @Test
    public void testgetTipo() {
        assertEquals(entrenamiento.getTipo(), "Fuerza");
        assertNotEquals(entrenamiento.getTipo(), "Aerobico");
    }
    @Test
    public void testsetId(){
        entrenamiento.setId(1);
        assertThat(entrenamiento.getId()).isEqualTo(1);
    }
    @Test
    public void testsetNombre(){
        entrenamiento.setNombre_Entrenamiento("Entrenamiento de prueba");
        assertThat(entrenamiento.getNombre_Entrenamiento()).isNotEmpty();
        assertThat(entrenamiento.getNombre_Entrenamiento()).isEqualTo("Entrenamiento de prueba");
    }
    @Test
    public void testsetNumejercicios(){
        entrenamiento.setNum_ejercicios(3);
        assertThat(entrenamiento.getNum_ejercicios()).isEqualTo(3);
        assertThat(entrenamiento.getNum_ejercicios()).isNotNull();
    }
    @Test
    public void testsetRpe(){
        entrenamiento.setRpe_Objetivo(3);
        assertThat(entrenamiento.getRpe_Objetivo()).isEqualTo(3);
        assertThat(entrenamiento.getRpe_Objetivo()).isNotNull();
        assertNotEquals(5, entrenamiento.getRpe_Objetivo());
    }
    @Test
    public void testsetTipo(){
        entrenamiento.setTipo("Aeróbico");
        assertThat(entrenamiento.getTipo()).isEqualTo("Aeróbico");
        assertThat(entrenamiento.getTipo()).isNotNull();
        assertNotEquals("Aerobico", entrenamiento.getTipo());
    }
}
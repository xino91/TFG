package com.example.apprpe.modelo;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class daoTest {
    @Mock
    private RpeDao userDao;

    @Test
    public void testInsertUser() {
        Entrenamiento entrenamiento = new Entrenamiento(1,"Prueba", 0,5, "Fuerza");
        userDao.insert(entrenamiento);
        verify(userDao).insert(entrenamiento);
    }
}

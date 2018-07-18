package services;

import models.Equipo;
import models.Tarea;
import org.dbunit.JndiDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Environment;
import play.db.jpa.JPAApi;
import play.inject.Injector;
import play.inject.guice.GuiceApplicationBuilder;

import java.io.FileInputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EquipoServiceTest {
    static private Injector injector;

    @BeforeClass
    static public void initApplication() {
        GuiceApplicationBuilder guiceApplicationBuilder =
                new GuiceApplicationBuilder().in(Environment.simple());
        injector = guiceApplicationBuilder.injector();
        injector.instanceOf(JPAApi.class);
    }

    @Before
    public void initData() throws Exception {
        JndiDatabaseTester databaseTester = new JndiDatabaseTester("DBTodoList");
        IDataSet initialDataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("test/resources/usuarios_dataset.xml"));
        databaseTester.setDataSet(initialDataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }

    @Test(expected = EquipoServiceException.class)
    public void addEquipoNombreRepetido() {
        EquipoService equipoService = injector.instanceOf(EquipoService.class);
        equipoService.addEquipo("Equipo A");
    }

    @Test
    public void listaEquipos() {
        EquipoService equipoService = injector.instanceOf(EquipoService.class);
        List<Equipo> equipos = equipoService.allEquipos();
        assertEquals(2, equipos.size());
    }
}

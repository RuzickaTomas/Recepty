/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.dao;

import cz.project.recepty.beans.Obrazek;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author tomas
 */
@RunWith(MockitoJUnitRunner.class)
public class ObrazkyDAOTest {

    @Mock
    private ObrazkyDAOImpl dao;

    @Before
    public void setUp() {
    }
    
    
    @Test
    public void testSinglePictureRetrieval() throws SQLException {
        long id = 17L;
        //mocks
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement prepareStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        //set datasource
        doCallRealMethod().when(dao).setDataSource(dataSource);
        dao.setDataSource(dataSource);
        //conditions
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement("select * from picture where id = ?")).thenReturn(prepareStatement); 
        when(prepareStatement.execute()).thenReturn(Boolean.TRUE);
        when(prepareStatement.getResultSet()).thenReturn(resultSet);
        when(resultSet.first()).thenReturn(Boolean.TRUE);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("path")).thenReturn("path");
        when(resultSet.getString("src")).thenReturn("http://locahost:8080/paht");
        when(resultSet.getLong("recept_id")).thenReturn(2L);
        // expected result
        Obrazek expected = getObrazky().get(0);
        doCallRealMethod().when(dao).getPicture(id);
        Obrazek result = dao.getPicture(id);
        Assert.assertEquals(result, expected);
    }

    @Test
    public void testPicturesRetrieval() {
        DataSource dataSource = mock(DataSource.class);
        doCallRealMethod().when(dao).setDataSource(dataSource);
        dao.setDataSource(dataSource);
        when(dao.getPictures()).thenReturn(getObrazky());
        List<Obrazek> result = dao.getPictures();
        Assert.assertEquals(getObrazky(), result);
    }

    private List<Obrazek> getObrazky() {
        Obrazek obr1 = new Obrazek();
        obr1.setId(1L);
        obr1.setPath("path");
        obr1.setSrc("http://locahost:8080/paht");
        obr1.setRecept_id(2L);

        Obrazek obr2 = new Obrazek();
        obr2.setId(3L);
        obr2.setPath("path");
        obr2.setSrc("http://locahost:8080/paht");
        obr2.setRecept_id(5L);
        List<Obrazek> obrazky = List.of(obr1, obr2);
        return obrazky;
    }

    @Test
    public void testPictureRemoval() throws SQLException {
        long id = 11L;
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement prepareStatement = mock(PreparedStatement.class);
        doCallRealMethod().when(dao).setDataSource(dataSource);
        dao.setDataSource(dataSource);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement("delete from picture where id = ?")).thenReturn(prepareStatement);    
        doCallRealMethod().when(dao).remove(id);
        dao.remove(id);
    }

    @After
    public void tearDown() {
    }
}

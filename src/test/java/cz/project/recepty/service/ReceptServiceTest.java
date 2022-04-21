/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.service;

import cz.project.recepty.beans.Obrazek;
import cz.project.recepty.beans.Recept;
import cz.project.recepty.dao.ObrazkyDAO;
import cz.project.recepty.dao.ReceptyDAO;
import cz.project.recepty.dto.ReceptDTO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import javax.servlet.http.Part;

/**
 *
 * @author tomas
 */
@RunWith(MockitoJUnitRunner.class)
public class ReceptServiceTest {

    @Mock
    private ReceptService service;

    @Before
    public void setUp() {
    }

    @Test
    public void testSavingRecept() {
        ReceptDTO recept = new ReceptDTO();
        long expected = 3L;
        when(this.service.save(recept)).thenReturn(3L);
        long result = service.save(recept);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testRetrieveRecepts() {
        //co ocekavame 
        Recept r1 = new Recept(1L, "T1", "popis", 2L);
        Recept r2 = new Recept(2L, "T2", "popis", 3L);
        List<Recept> expected = List.of(r1, r2);

        when(this.service.getRecepty()).thenReturn(expected);
        //provedeme get a uvidime zda se nic nezmenilo
        List<Recept> result = this.service.getRecepty();
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testUploadFile() throws IOException {
        Part file = mock(Part.class);
        InputStream is = mock(InputStream.class);
        ObrazkyDAO dao = mock(ObrazkyDAO.class);
        doCallRealMethod().when(this.service).setObrazkyDao(dao);
        this.service.setObrazkyDao(dao);
        Obrazek obr = mock(Obrazek.class);
        long receptId = 7L;
        try {
            when(file.getInputStream()).thenReturn(is);
            when(is.readAllBytes()).thenReturn("TEST".getBytes());
            when(dao.save(obr)).thenReturn(receptId);
            doCallRealMethod().when(this.service).uploadFile(file, receptId);
            this.service.uploadFile(file, receptId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final String savePath = System.getProperty("user.home") + File.separator + "pictures" + File.separator + "null";
        final Path path = Path.of(savePath);
        Assert.assertTrue(Files.exists(path));
        Files.delete(path);
    }

    @Test
    public void testPictureRetrieval() {
        Recept r = new Recept(1L, "name", "description", 3L);
        String expected = "http://localhost:8080/path";
        when(this.service.getPicture(r)).thenReturn(expected);
        String result = this.service.getPicture(r);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testReceptRemoval() {
        Recept r = new Recept(1L, "name", "description", 3L);
        ReceptyDAO dao = mock(ReceptyDAO.class);
        doCallRealMethod().when(this.service).setReceptyDao(dao);
        this.service.setReceptyDao(dao);
        doCallRealMethod().when(this.service).remove(r);
        this.service.remove(r);     
    }

    @After
    public void tearDown() {
    }
}

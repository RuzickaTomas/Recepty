/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.transform;

import cz.project.recepty.beans.Recept;
import cz.project.recepty.dto.ReceptDTO;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author tomas
 */
public class TransformReceptTest {

    private Logger logger = Logger.getLogger(TransformReceptTest.class.getName());

    @Before
    public void setUp() {
    }

    @Test
    public void tranformationToEntity() {
        logger.info("test trasformace z entity");
        Recept expected = new Recept(3L, "TEST", "dobry test");
        ReceptDTO from = new ReceptDTO();
        from.setId(3L);
        from.setName("TEST");
        from.setDescription("dobry test");
        logger.info("predani k transformovani");
        Recept result = TransformRecept.transform(from);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void tranformationFromEntity() {
        logger.info("test transformace s dto");
        ReceptDTO expected = new ReceptDTO();
        expected.setId(3L);
        expected.setName("TEST");
        expected.setDescription("dobry test");
        Recept from = new Recept(3L, "TEST", "dobry test");
        logger.info("predani k transformovani");
        ReceptDTO result = TransformRecept.transform(from);
        Assert.assertEquals(expected, result);
    }

    @After
    public void tearDown() {
    }

}

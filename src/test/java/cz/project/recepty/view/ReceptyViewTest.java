/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.view;

import cz.project.recepty.beans.Recept;
import cz.project.recepty.service.IReceptService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ReceptyViewTest {

    @Mock
    private ReceptyView view;

    @Before
    public void setUp() {
    }

    @Test
    public void testRemoveRecept() {
        Recept recept = new Recept(3L, "name", "description");
        IReceptService service = mock(IReceptService.class);
        doCallRealMethod().when(view).setService(service);
        view.setService(service);
        doCallRealMethod().when(view).remove(recept);
        view.remove(recept);
    }

    @After
    public void tearDown() {
    }

}

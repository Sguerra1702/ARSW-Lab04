/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.filters.RedundancyFilter;
import edu.eci.arsw.blueprints.filters.SubsamplingFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;


import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    private BlueprintsServices blueprintsServices;
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("juanito", "prueba",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("juanito", "prueba",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("juanito", "prueba",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){
            
        }

    }

    @Test
    public void shouldSaveandGetBueprint() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("Juanito", "Prueba1", pts);
        ibpp.saveBlueprint(bp);
        Blueprint retrieved = ibpp.getBlueprint("Juanito", "Prueba1");
        Assert.assertNotNull(retrieved);
        Assert.assertEquals("Juanito", retrieved.getAuthor());
        Assert.assertEquals("Prueba1", retrieved.getName());
    }

    @Test
    public void shouldGetByAuthor() throws Exception{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Point[] pts2=new Point[]{new Point(0, 0),new Point(20, 20)};
        Blueprint bp=new Blueprint("Juanito", "Prueba1", pts);
        Blueprint bp2=new Blueprint("Juanito", "Prueba2", pts2);
        ibpp.saveBlueprint(bp);
        ibpp.saveBlueprint(bp2);
        Assert.assertEquals(2, ibpp.getBlueprintsByAuthor("Juanito").size());
    }

    @Test(expected = BlueprintNotFoundException.class)
    public void shouldThrowExceptionWhenBlueprintDoesNotExist() throws BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        ibpp.getBlueprint("Juanito", "Prueba1");
    }

    @Test
    public void getBlueprintTest() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("juanito", "prueba", pts);
        ibpp.saveBlueprint(bp);

        Blueprint retrievedBp = ibpp.getBlueprint("juanito", "prueba");

        assertNotNull("Loading a previously stored blueprint returned null.", retrievedBp);
        assertEquals("Loading a previously stored blueprint returned a different blueprint.", bp, retrievedBp);
    }

    @Test
    public void getBlueprintsByAuthorTest() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts1 = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp1 = new Blueprint("juanito", "prueba1", pts1);
        ibpp.saveBlueprint(bp1);

        Point[] pts2 = new Point[]{new Point(20, 20), new Point(30, 30)};
        Blueprint bp2 = new Blueprint("juanito", "prueba2", pts2);
        ibpp.saveBlueprint(bp2);

        Point[] pts3 = new Point[]{new Point(40, 40), new Point(50, 50)};
        Blueprint bp3 = new Blueprint("juanito", "prueba3", pts3);
        ibpp.saveBlueprint(bp3);

        assertEquals("The number of blueprints by author is incorrect.", 3, ibpp.getBlueprintsByAuthor("juanito").size());
        assertTrue("The list of blueprints by author does not contain the expected blueprint.", ibpp.getBlueprintsByAuthor("juanito").contains(bp1));
        assertTrue("The list of blueprints by author does not contain the expected blueprint.", ibpp.getBlueprintsByAuthor("juanito").contains(bp2));
        assertTrue("The list of blueprints by author contains an unexpected blueprint.", ibpp.getBlueprintsByAuthor("juanito").contains(bp3));
    }

    @Test
    public void shouldApplyRedundancyFilter() throws Exception {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        blueprintsServices = new BlueprintsServices();
        blueprintsServices.bpp = ibpp;
        blueprintsServices.blueprintFilter = new RedundancyFilter(); // Usamos el filtro de redundancia

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10), new Point(10, 10), new Point(20, 20)};
        Blueprint bp = new Blueprint("author1", "redundantBlueprint", pts);
        blueprintsServices.addNewBlueprint(bp);

        Blueprint filteredBlueprint = blueprintsServices.getBlueprint("author1", "redundantBlueprint");
        System.out.println("Puntos antes del filtrado: " + bp.getPoints());
        System.out.println("Puntos después del filtrado: " + filteredBlueprint.getPoints());
        assertEquals("El filtro de redundancia no eliminó los puntos duplicados correctamente.",
                3, filteredBlueprint.getPoints().size());
    }

    @Test
    public void shouldApplySubsamplingFilter() throws Exception {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        blueprintsServices = new BlueprintsServices();
        blueprintsServices.bpp = ibpp;
        blueprintsServices.blueprintFilter = new SubsamplingFilter(); // Usamos el filtro de submuestreo

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10), new Point(20, 20), new Point(30, 30)};
        Blueprint bp = new Blueprint("author1", "subsampledBlueprint", pts);
        blueprintsServices.addNewBlueprint(bp);

        Blueprint filteredBlueprint = blueprintsServices.getBlueprint("author1", "subsampledBlueprint");

        assertEquals("El filtro de submuestreo no redujo correctamente los puntos.",
                2, filteredBlueprint.getPoints().size());
    }

    @Test
    public void shouldFilterBlueprintsByAuthor() throws Exception {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        blueprintsServices = new BlueprintsServices();
        blueprintsServices.bpp = ibpp;
        blueprintsServices.blueprintFilter = new RedundancyFilter(); // Aplicamos un filtro

        Point[] pts1 = new Point[]{new Point(0, 0), new Point(10, 10), new Point(10, 10)};
        Blueprint bp1 = new Blueprint("author2", "bp1", pts1);

        Point[] pts2 = new Point[]{new Point(20, 20), new Point(30, 30), new Point(30, 30)};
        Blueprint bp2 = new Blueprint("author2", "bp2", pts2);

        blueprintsServices.addNewBlueprint(bp1);
        blueprintsServices.addNewBlueprint(bp2);

        Set<Blueprint> filteredBlueprints = blueprintsServices.getBlueprintsByAuthor("author2");

        assertEquals("No se aplicó correctamente el filtro a los planos del autor.", 2, filteredBlueprints.size());
        for (Blueprint bp : filteredBlueprints) {
            assertEquals("El filtro de redundancia no se aplicó correctamente.",
                    2, bp.getPoints().size());
        }
    }
}

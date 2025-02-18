package edu.eci.arsw.blueprints;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) throws BlueprintPersistenceException {
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices blueprintServices = context.getBean(BlueprintsServices.class);

        // Registrar planos
        Point[] pts1 = new Point[] { new Point(0, 0), new Point(10, 10) };
        Blueprint bp1 = new Blueprint("john", "thepaint1", pts1);
        blueprintServices.addNewBlueprint(bp1);

        Point[] pts2 = new Point[] { new Point(20, 20), new Point(30, 30) };
        Blueprint bp2 = new Blueprint("john", "thepaint2", pts2);
        blueprintServices.addNewBlueprint(bp2);

        // Consultar planos
        System.out.println("Planos registrados:");
        for (Blueprint bp : blueprintServices.getAllBlueprints()) {
            System.out.println(bp);
        }

        try {
            Blueprint retrievedBp = blueprintServices.getBlueprint("john", "thepaint1");
            System.out.println("Plano específico:");
            System.out.println(retrievedBp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

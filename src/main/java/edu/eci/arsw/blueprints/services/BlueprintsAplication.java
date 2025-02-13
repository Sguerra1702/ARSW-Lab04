/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */


@SpringBootApplication
public class BlueprintsAplication {
    public static void main(String[] args) {
        SpringApplication.run(BlueprintsAplication.class, args);
    }

    
}


@Service
class BlueprintsServices {

    @Autowired
    BlueprintsPersistence bpPersistence;

    private final Map<String , Set<String>> blueprints = new HashMap<>();

    public void addNewBlueprint(String author, String Blueprint){
        blueprints.computeIfAbsent(author,k -> new HashSet<>()).add(Blueprint);
        
    }
    
    public Set<Blueprint> getAllBlueprints(){
        return null;
    }

    /**
     * 
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Optional <String > getBlueprint(String author,String Blueprint) throws BlueprintNotFoundException{
        return blueprints.getOrDefault(author, Collections.emptySet()).stream().filter(bpPersistence -> bpPersistence.equals(Blueprint)).findFirst();
    }
    
    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<String> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException{
        return blueprints.getOrDefault(author,Collections.emptySet());
    }
    
}

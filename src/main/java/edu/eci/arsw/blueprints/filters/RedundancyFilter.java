package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.LinkedHashSet;

@Component
public class RedundancyFilter implements BlueprintFilter {
    
    @Override
    public Blueprint filterBlueprint(Blueprint bp) {
        List<Point> points = bp.getPoints();
        List<Point> filteredPoints = new ArrayList<>();
        
        if (!points.isEmpty()) {
            filteredPoints.add(points.get(0)); // Agrega el primer punto

            for (int i = 1; i < points.size(); i++) {
                if (!points.get(i).equals(points.get(i - 1))) {
                    filteredPoints.add(points.get(i));
                }
            }
        }
        
        bp.setPoints(filteredPoints); // Mantiene el mismo objeto
        return bp;
    }

    @Override
    public Set<Blueprint> filterBlueprints(Set<Blueprint> blueprints) {
        return blueprints.stream()
                .map(this::filterBlueprint)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}

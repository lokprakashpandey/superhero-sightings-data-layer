/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-10
 * purpose: Dao for Sighting entity
 */
package com.lok.superherosightings.dao;

import com.lok.superherosightings.dto.Location;
import com.lok.superherosightings.dto.Sighting;
import com.lok.superherosightings.dto.Superhero;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author root
 */
public interface SightingDao {
    Sighting getSightingById(int id);
    List<Sighting> getAllSightings();
    Sighting addSighting(Sighting sighting);
    void updateSighting(Sighting sighting);
    void deleteSightingById(int id);
    
    /**
     * Gets all superheroes sighted at a particular location
    */
    List<Superhero> getAllSuperheroesForLocation(Location location);
    
    /**
     * Returns a list of all the locations where a particular superhero has been seen
     *
     * @param Superhero superhero for whom to get all locations
     * @return List<Location> contains all locations
    */
    List<Location> getAllLocationsForSuperhero(Superhero superhero);
    
    /**
     * Gets all sightings for a particular date
    */
    List<Sighting> getAllSightingsForParticularDate(LocalDate date);
}

/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date:
 * purpose:
 */
package com.lok.superherosightings.dao;

import com.lok.superherosightings.dto.Location;
import com.lok.superherosightings.dto.Sighting;
import com.lok.superherosightings.dto.Superhero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author root
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationDaoDbTest {
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    SuperheroDao superheroDao;
    
    public LocationDaoDbTest() {
    }
    
    @Before
    public void setUp() {
        List<Sighting> sightings = sightingDao.getAllSightings();
        for(Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getId());
        }
        
        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        for(Superhero superhero : superheroes) {
            superheroDao.deleteSuperheroById(superhero.getId());
        }
        
        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getId());
        }
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAddAndGetLocation() {
        Location location = new Location();
        location.setName("Ridgeview Park");
        location.setDescription("A big park in New York downtown");
        location.setAddress("New York, NY 10002");
        location.setLatitude(new BigDecimal("40.7829"));
        location.setLongitude(new BigDecimal("-73.9654"));
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
        
    }

    @Test
    public void testGetAllLocations() {
        Location location = new Location();
        location.setName("Ridgeview Park");
        location.setDescription("A big park in New York downtown");
        location.setAddress("New York, NY 10002");
        location.setLatitude(new BigDecimal("40.7829"));
        location.setLongitude(new BigDecimal("-73.9654"));
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setName("Statue of Liberty");
        location2.setDescription("A national monument in New York Harbor");
        location2.setAddress("Liberty Island, New York, NY 10004");
        location2.setLatitude(new BigDecimal("40.6892"));
        location2.setLongitude(new BigDecimal("-74.0445"));
        location2 = locationDao.addLocation(location2);
        
        List<Location> locations = locationDao.getAllLocations();
        
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }

    @Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setName("Ridgeview Park");
        location.setDescription("A big park in New York downtown");
        location.setAddress("New York, NY 10002");
        location.setLatitude(new BigDecimal("40.7829"));
        location.setLongitude(new BigDecimal("-73.9654"));
        location = locationDao.addLocation(location);
        
        
        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
        
        location.setName("New Ridgeview Park");
        locationDao.updateLocation(location);
        
        assertNotEquals(location, fromDao);
        
        fromDao = locationDao.getLocationById(location.getId());
        
        assertEquals(location, fromDao);
    }

    @Test
    public void testDeleteLocationById() {
        Location location = new Location();
        location.setName("Ridgeview Park");
        location.setDescription("A big park in New York downtown");
        location.setAddress("New York, NY 10002");
        location.setLatitude(new BigDecimal("40.7829"));
        location.setLongitude(new BigDecimal("-73.9654"));
        location = locationDao.addLocation(location);
        
        Superhero superhero = new Superhero();
        superhero.setName("Spiderman");
        superhero.setDescription("A young man living in the city");
        superhero.setSuperpower("Can make webs, climb buildings and have great strength");
        superhero = superheroDao.addSuperhero(superhero);
        
        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sighting.setSuperhero(superhero);
        sightingDao.addSighting(sighting);
        
        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
        
        locationDao.deleteLocationById(location.getId());
        
        fromDao = locationDao.getLocationById(location.getId());
        assertNull(fromDao);
    }
    
}

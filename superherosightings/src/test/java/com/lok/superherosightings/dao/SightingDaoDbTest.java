/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date:
 * purpose:
 */
package com.lok.superherosightings.dao;

import com.lok.superherosightings.dto.Location;
import com.lok.superherosightings.dto.Organization;
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
public class SightingDaoDbTest {

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    LocationDao locationDao;

    public SightingDaoDbTest() {
    }

    @Before
    public void setUp() {

        List<Organization> organizations = organizationDao.getAllOrganizations();
        for (Organization organization : organizations) {
            organizationDao.deleteOrganizationById(organization.getId());
        }

        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getId());
        }

        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        for (Superhero superhero : superheroes) {
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
    public void testGetAndAddSighting() {
        Location location = new Location();
        location.setName("Ridgeview Park");
        location.setDescription("A big park in New York downtown");
        location.setAddress("New York, NY 10002");
        location.setLatitude(new BigDecimal("40.7829"));
        location.setLongitude(new BigDecimal("-73.9654"));
        location = locationDao.addLocation(location);

        Superhero superhero = new Superhero();
        superhero.setName("Spiderman");
        superhero.setDescription("An ordinary young man in his 20s living in the city");
        superhero.setSuperpower("Make webs, climb buildings, strong and agile");
        superhero = superheroDao.addSuperhero(superhero);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setLocation(location);
        sighting.setSuperhero(superhero);
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
    }

    @Test
    public void testGetAllSightings() {
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

        Superhero superhero = new Superhero();
        superhero.setName("Spiderman");
        superhero.setDescription("An ordinary young man in his 20s living in the city");
        superhero.setSuperpower("Make webs, climb buildings, strong and agile");
        superhero = superheroDao.addSuperhero(superhero);

        Superhero superhero2 = new Superhero();
        superhero2.setName("Hulk");
        superhero2.setDescription("A doctor who got exposed to radiation and became greenish and huge");
        superhero2.setSuperpower("Super strong, accurate and bullet-proof");
        superhero2 = superheroDao.addSuperhero(superhero2);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setLocation(location);
        sighting.setSuperhero(superhero);
        sighting = sightingDao.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDate.now());
        sighting2.setLocation(location2);
        sighting2.setSuperhero(superhero2);
        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> sightings = sightingDao.getAllSightings();
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));

    }

    @Test
    public void testUpdateSighting() {
        Location location = new Location();
        location.setName("Ridgeview Park");
        location.setDescription("A big park in New York downtown");
        location.setAddress("New York, NY 10002");
        location.setLatitude(new BigDecimal("40.7829"));
        location.setLongitude(new BigDecimal("-73.9654"));
        location = locationDao.addLocation(location);

        Superhero superhero = new Superhero();
        superhero.setName("Spiderman");
        superhero.setDescription("An ordinary young man in his 20s living in the city");
        superhero.setSuperpower("Make webs, climb buildings, strong and agile");
        superhero = superheroDao.addSuperhero(superhero);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setLocation(location);
        sighting.setSuperhero(superhero);
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);

        sighting.setDate(LocalDate.now().minusDays(1));
        sightingDao.updateSighting(sighting);
        assertNotEquals(sighting, fromDao);

        fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);

    }

    @Test
    public void testDeleteSightingById() {
        Location location = new Location();
        location.setName("Ridgeview Park");
        location.setDescription("A big park in New York downtown");
        location.setAddress("New York, NY 10002");
        location.setLatitude(new BigDecimal("40.7829"));
        location.setLongitude(new BigDecimal("-73.9654"));
        location = locationDao.addLocation(location);

        Superhero superhero = new Superhero();
        superhero.setName("Spiderman");
        superhero.setDescription("An ordinary young man in his 20s living in the city");
        superhero.setSuperpower("Make webs, climb buildings, strong and agile");
        superhero = superheroDao.addSuperhero(superhero);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setLocation(location);
        sighting.setSuperhero(superhero);
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);

        sightingDao.deleteSightingById(sighting.getId());
        fromDao = sightingDao.getSightingById(sighting.getId());
        assertNull(fromDao);

    }

    //Test cases: Two superheroes in one location | No superhero for a location
    @Test
    public void testGetSuperheroesForLocation() {
        Location location = new Location();
        location.setName("Ridgeview Park");
        location.setDescription("A big park in New York downtown");
        location.setAddress("New York, NY 10002");
        location.setLatitude(new BigDecimal("40.7829"));
        location.setLongitude(new BigDecimal("-73.9654"));
        location = locationDao.addLocation(location);

        Superhero superhero = new Superhero();
        superhero.setName("Spiderman");
        superhero.setDescription("An ordinary young man in his 20s living in the city");
        superhero.setSuperpower("Make webs, climb buildings, strong and agile");
        superhero = superheroDao.addSuperhero(superhero);

        Superhero superhero2 = new Superhero();
        superhero2.setName("Hulk");
        superhero2.setDescription("A doctor who got exposed to radiation and became greenish and huge");
        superhero2.setSuperpower("Super strong, accurate and bullet-proof");
        superhero2 = superheroDao.addSuperhero(superhero2);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setLocation(location);
        sighting.setSuperhero(superhero);
        sightingDao.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDate.now());
        sighting2.setLocation(location);
        sighting2.setSuperhero(superhero2);
        sightingDao.addSighting(sighting2);

        List<Superhero> superheroes = sightingDao.getSuperheroesForLocation(location);
        assertEquals(2, superheroes.size());
        assertTrue(superheroes.contains(superhero));
        assertTrue(superheroes.contains(superhero2));

        Location location2 = new Location();
        location2.setName("Statue of Liberty");
        location2.setDescription("A national monument in New York Harbor");
        location2.setAddress("Liberty Island, New York, NY 10004");
        location2.setLatitude(new BigDecimal("40.6892"));
        location2.setLongitude(new BigDecimal("-74.0445"));
        location2 = locationDao.addLocation(location2);

        superheroes = sightingDao.getSuperheroesForLocation(location2);
        assertEquals(0, superheroes.size());
    }

    //Test cases: 2 locations for a superhero | No location for a superhero
    @Test
    public void testGetLocationsForSuperhero() {
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

        Superhero superhero = new Superhero();
        superhero.setName("Spiderman");
        superhero.setDescription("An ordinary young man in his 20s living in the city");
        superhero.setSuperpower("Make webs, climb buildings, strong and agile");
        superhero = superheroDao.addSuperhero(superhero);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setLocation(location);
        sighting.setSuperhero(superhero);
        sightingDao.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDate.now());
        sighting2.setLocation(location2);
        sighting2.setSuperhero(superhero);
        sightingDao.addSighting(sighting2);

        List<Location> locations = sightingDao.getLocationsForSuperhero(superhero);
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));

        Superhero superhero2 = new Superhero();
        superhero2.setName("Hulk");
        superhero2.setDescription("A doctor who got exposed to radiation and became greenish and huge");
        superhero2.setSuperpower("Super strong, accurate and bullet-proof");
        superhero2 = superheroDao.addSuperhero(superhero2);

        locations = sightingDao.getLocationsForSuperhero(superhero2);
        assertEquals(0, locations.size());
    }

    //Test cases: 2 sightings for a date | No sightings for a date
    @Test
    public void testGetSightingsForParticularDate() {
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

        Superhero superhero = new Superhero();
        superhero.setName("Spiderman");
        superhero.setDescription("An ordinary young man in his 20s living in the city");
        superhero.setSuperpower("Make webs, climb buildings, strong and agile");
        superhero = superheroDao.addSuperhero(superhero);

        Superhero superhero2 = new Superhero();
        superhero2.setName("Hulk");
        superhero2.setDescription("A doctor who got exposed to radiation and became greenish and huge");
        superhero2.setSuperpower("Super strong, accurate and bullet-proof");
        superhero2 = superheroDao.addSuperhero(superhero2);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setLocation(location);
        sighting.setSuperhero(superhero);
        sighting = sightingDao.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDate.now());
        sighting2.setLocation(location2);
        sighting2.setSuperhero(superhero2);
        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> sightings = sightingDao.getSightingsForParticularDate(LocalDate.now());
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));

        sightings = sightingDao.getSightingsForParticularDate(LocalDate.now().minusDays(1));
        assertEquals(0, sightings.size());

    }

}

/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-13
 * purpose: Tests for SuperheroDaoDb
 */
package com.lok.superherosightings.dao;

import com.lok.superherosightings.dto.Location;
import com.lok.superherosightings.dto.Organization;
import com.lok.superherosightings.dto.Sighting;
import com.lok.superherosightings.dto.Superhero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
public class SuperheroDaoDbTest {

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    LocationDao locationDao;

    public SuperheroDaoDbTest() {
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
    public void testGetAndAddSuperhero() {
        Superhero superhero = new Superhero();
        superhero.setName("Spiderman");
        superhero.setDescription("An ordinary young man in his 20s living in the city");
        superhero.setSuperpower("Make webs, climb buildings, strong and agile");
        superhero = superheroDao.addSuperhero(superhero);

        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);
    }

    @Test
    public void testGetAllSuperheroes() {
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

        List<Superhero> superheroes = superheroDao.getAllSuperheroes();

        assertEquals(2, superheroes.size());
        assertTrue(superheroes.contains(superhero));
        assertTrue(superheroes.contains(superhero2));
    }

    @Test
    public void testUpdateSuperhero() {
        Superhero superhero = new Superhero();
        superhero.setName("Spiderman");
        superhero.setDescription("An ordinary young man in his 20s living in the city");
        superhero.setSuperpower("Make webs, climb buildings, strong and agile");
        superhero = superheroDao.addSuperhero(superhero);

        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);

        superhero.setName("New Spiderman");
        superheroDao.updateSuperhero(superhero);

        assertNotEquals(superhero, fromDao);

        fromDao = superheroDao.getSuperheroById(superhero.getId());

        assertEquals(superhero, fromDao);
    }

    @Test
    public void testDeleteSuperheroById() {
        Superhero superhero = new Superhero();
        superhero.setName("Spiderman");
        superhero.setDescription("An ordinary young man in his 20s living in the city");
        superhero.setSuperpower("Make webs, climb buildings, strong and agile");
        superhero = superheroDao.addSuperhero(superhero);

        Organization organization = new Organization();
        organization.setName("Avengers");
        organization.setDescription("An entertainment organization dealing with fictional superheroes");
        organization.setAddress("New York City, New York, United States, 10001");
        organization.setContactNumber("2125764000");

        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(superhero);
        organization.setSuperheroes(superheroes);
        organizationDao.addOrganization(organization);

        Location location = new Location();
        location.setName("Ridgeview Park");
        location.setDescription("A big park in New York downtown");
        location.setAddress("New York, NY 10002");
        location.setLatitude(new BigDecimal("40.7829"));
        location.setLongitude(new BigDecimal("-73.9654"));
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sighting.setSuperhero(superhero);
        sightingDao.addSighting(sighting);

        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);

        superheroDao.deleteSuperheroById(superhero.getId());
        fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertNull(fromDao);

    }

}

/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-14
 * purpose: Test for OrganizationDaoDb
 */
package com.lok.superherosightings.dao;

import com.lok.superherosightings.dto.Location;
import com.lok.superherosightings.dto.Organization;
import com.lok.superherosightings.dto.Sighting;
import com.lok.superherosightings.dto.Superhero;
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
public class OrganizationDaoDbTest {
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    SuperheroDao superheroDao;
    
    @Autowired
    LocationDao locationDao;
    
    public OrganizationDaoDbTest() {
    }
    
    @Before
    public void setUp() {
        List<Organization> organizations = organizationDao.getAllOrganizations();
        for(Organization organization : organizations) {
            organizationDao.deleteOrganizationById(organization.getId());
        }
        
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
    public void testGetAndAddOrganization() {
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
        organization = organizationDao.addOrganization(organization);
        
        Organization fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, fromDao);
        
    }

    @Test
    public void testGetAllOrganizations() {
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
        
        Organization organization = new Organization();
        organization.setName("Avengers");
        organization.setDescription("An entertainment organization dealing with fictional superheroes");
        organization.setAddress("New York City, New York, United States, 10001");
        organization.setContactNumber("2125764000");
        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(superhero);
        organization.setSuperheroes(superheroes);
        organization = organizationDao.addOrganization(organization);
        
        Organization organization2 = new Organization();
        organization2.setName("Warner Bros");
        organization2.setDescription("An entertainment conglomerate");
        organization2.setAddress("New York City, New York, United States, 10002");
        organization2.setContactNumber("8189546777");
        List<Superhero> superheroes2 = new ArrayList<>();
        superheroes2.add(superhero2);
        organization2.setSuperheroes(superheroes2);
        organization2 = organizationDao.addOrganization(organization2);
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        assertEquals(2, organizations.size());
        assertTrue(organizations.contains(organization));
        assertTrue(organizations.contains(organization2));
        
    }

    @Test
    public void testUpdateOrganization() {
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
        organization = organizationDao.addOrganization(organization);
        
        Organization fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, fromDao);
        
        organization.setName("Marvel");
        organizationDao.updateOrganization(organization);
        assertNotEquals(organization, fromDao);
        
        fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, fromDao);
    }

    @Test
    public void testDeleteOrganizationById() {
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
        organization = organizationDao.addOrganization(organization);
        
        Organization fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, fromDao);
        
        organizationDao.deleteOrganizationById(organization.getId());
        fromDao = organizationDao.getOrganizationById(organization.getId());
        assertNull(fromDao);        
    }

    @Test
    public void testGetSuperheroesForOrganization() {
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
        
        Organization organization = new Organization();
        organization.setName("Avengers");
        organization.setDescription("An entertainment organization dealing with fictional superheroes");
        organization.setAddress("New York City, New York, United States, 10001");
        organization.setContactNumber("2125764000");
        
        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(superhero);
        superheroes.add(superhero2);
        organization.setSuperheroes(superheroes);
        organization = organizationDao.addOrganization(organization);
        
        List<Superhero> fromDao = organizationDao.getSuperheroesForOrganization(organization);
        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(superhero));
        assertTrue(fromDao.contains(superhero2));
    }

    @Test
    public void testGetOrganizationsForSuperhero() {
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
        organization = organizationDao.addOrganization(organization);
        
        Organization organization2 = new Organization();
        organization2.setName("Warner Bros");
        organization2.setDescription("An entertainment conglomerate");
        organization2.setAddress("New York City, New York, United States, 10002");
        organization2.setContactNumber("8189546777");
        organization2.setSuperheroes(superheroes); // same superhero set to a different organization
        organization2 = organizationDao.addOrganization(organization2);
        
        List<Organization> fromDao = organizationDao.getOrganizationsForSuperhero(superhero);
        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(organization));
        assertTrue(fromDao.contains(organization2));
    } 
}

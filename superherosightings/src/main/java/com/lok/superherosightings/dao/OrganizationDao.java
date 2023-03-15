/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-10
 * purpose: Dao for Organization entity
 */
package com.lok.superherosightings.dao;

import com.lok.superherosightings.dto.Organization;
import com.lok.superherosightings.dto.Superhero;
import java.util.List;

/**
 *
 * @author root
 */
public interface OrganizationDao {

    Organization getOrganizationById(int id);

    List<Organization> getAllOrganizations();

    Organization addOrganization(Organization organization);

    void updateOrganization(Organization organization);

    void deleteOrganizationById(int id);

    /**
     * Gets all members(superheroes) for an organization
     */
    List<Superhero> getSuperheroesForOrganization(Organization organization);

    /**
     * Gets all organizations for Superhero
     */
    List<Organization> getOrganizationsForSuperhero(Superhero superhero);
}

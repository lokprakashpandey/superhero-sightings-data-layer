/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-10
 * purpose: SuperheroDao interface
 */
package com.lok.superherosightings.dao;

import com.lok.superherosightings.dto.Superhero;
import java.util.List;

/**
 *
 * @author root
 */
public interface SuperheroDao {
    
    Superhero getSuperheroById(int id);
    List<Superhero> getAllSuperheroes();
    Superhero addSuperhero(Superhero superhero);
    void updateSuperhero(Superhero superhero);
    void deleteSuperheroById(int id);
}

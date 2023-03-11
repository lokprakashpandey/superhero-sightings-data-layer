/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-10
 * purpose: Implementation of SuperheroDao
 */

package com.lok.superherosightings.dao;

import com.lok.superherosightings.dto.Superhero;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SuperheroDaoDb implements SuperheroDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Superhero getSuperheroById(int id) {
        try {
            final String GET_SUPERHERO_BY_ID = "SELECT * FROM superhero WHERE id = ?";
            return jdbc.queryForObject(GET_SUPERHERO_BY_ID, new SuperheroMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Superhero> getAllSuperheroes() {
        final String GET_ALL_SUPERHEROES = "SELECT * FROM superhero";
        return jdbc.query(GET_ALL_SUPERHEROES, new SuperheroMapper());
    }

    @Override
    public Superhero addSuperhero(Superhero superhero) {
        final String INSERT_SUPERHERO = "INSERT INTO superhero(name, description, superpower) " +
                "VALUES(?,?,?)";
        jdbc.update(INSERT_SUPERHERO,
                superhero.getName(),
                superhero.getDescription(),
                superhero.getSuperpower());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superhero.setId(newId);
        return superhero;
    }

    @Override
    public void updateSuperhero(Superhero superhero) {
        final String UPDATE_SUPERHERO = "UPDATE superhero SET name = ?, description = ?, " +
                "superpower = ? WHERE id = ?";
        jdbc.update(UPDATE_SUPERHERO,
                superhero.getName(),
                superhero.getDescription(),
                superhero.getSuperpower(),
                superhero.getId());
    }

    @Override
    public void deleteSuperheroById(int id) {
        final String DELETE_SUPERHERO_ORGANIZATION = "DELETE FROM superheroOrganization "
                + "WHERE superheroId = ?";
        jdbc.update(DELETE_SUPERHERO_ORGANIZATION, id);
        
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE superheroId = ?";
        jdbc.update(DELETE_SIGHTING, id);
        
        final String DELETE_SUPERHERO = "DELETE FROM superhero WHERE id = ?";
        jdbc.update(DELETE_SUPERHERO, id);
    }
    
    public static final class SuperheroMapper implements RowMapper<Superhero> {

        @Override
        public Superhero mapRow(ResultSet rs, int index) throws SQLException {
            Superhero superhero = new Superhero();
            superhero.setId(rs.getInt("id"));
            superhero.setName(rs.getString("name"));
            superhero.setDescription(rs.getString("description"));
            superhero.setSuperpower(rs.getString("superpower"));
            
            return superhero;
        }
    }

}

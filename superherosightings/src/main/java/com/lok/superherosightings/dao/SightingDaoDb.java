/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-13
 * purpose: SightingDao Implementation
 */
package com.lok.superherosightings.dao;

import com.lok.superherosightings.dao.LocationDaoDb.LocationMapper;
import com.lok.superherosightings.dao.SuperheroDaoDb.SuperheroMapper;
import com.lok.superherosightings.dto.Location;
import com.lok.superherosightings.dto.Sighting;
import com.lok.superherosightings.dto.Superhero;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SightingDaoDb implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting getSightingById(int id) {
        try {
            final String GET_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE id = ?";
            Sighting sighting = jdbc.queryForObject(GET_SIGHTING_BY_ID, new SightingMapper(), id);
            if (sighting != null) {
                sighting.setLocation(getLocationForSighting(id));
                sighting.setSuperhero(getSuperheroForSighting(id));
            }
            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM location l "
                + "JOIN sighting s ON s.locationId = l.id WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationMapper(), id);
    }

    private Superhero getSuperheroForSighting(int id) {
        final String SELECT_SUPERHERO_FOR_SIGHTING = "SELECT su.* FROM superhero su "
                + "JOIN sighting si ON si.superheroId = su.id WHERE si.id = ?";
        return jdbc.queryForObject(SELECT_SUPERHERO_FOR_SIGHTING, new SuperheroMapper(), id);
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        associateLocationAndSuperhero(sightings);
        return sightings;
    }

    private void associateLocationAndSuperhero(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setLocation(getLocationForSighting(sighting.getId()));
            sighting.setSuperhero(getSuperheroForSighting(sighting.getId()));
        }
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO sighting(date, locationId, superheroId) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                sighting.getDate(),
                sighting.getLocation().getId(),
                sighting.getSuperhero().getId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        return sighting;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE sighting SET date = ?, locationId = ?, "
                + "superheroId = ? WHERE id = ?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getDate(),
                sighting.getLocation().getId(),
                sighting.getSuperhero().getId(),
                sighting.getId());
    }

    @Override
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE id = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("id"));
            sighting.setDate(rs.getDate("date").toLocalDate());
            return sighting;
        }
    }

    @Override
    public List<Superhero> getSuperheroesForLocation(Location location) {
        final String SELECT_SUPERHEROES_FOR_LOCATION = "SELECT su.* FROM superhero su JOIN "
                + "sighting si ON si.superheroId = su.Id WHERE si.locationId = ?";
        List<Superhero> superheroes = jdbc.query(SELECT_SUPERHEROES_FOR_LOCATION,
                new SuperheroMapper(), location.getId());
        return superheroes;
    }

    @Override
    public List<Location> getLocationsForSuperhero(Superhero superhero) {
        final String SELECT_LOCATIONS_FOR_SUPERHERO = "SELECT l.* FROM location l JOIN "
                + "sighting s ON s.locationId = l.Id WHERE s.superheroId = ?";
        List<Location> locations = jdbc.query(SELECT_LOCATIONS_FOR_SUPERHERO,
                new LocationMapper(), superhero.getId());
        return locations;
    }

    @Override
    public List<Sighting> getSightingsForParticularDate(LocalDate date) {
        final String SELECT_SIGHTINGS_FOR_PARTICULAR_DATE = "SELECT * FROM sighting WHERE date = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_PARTICULAR_DATE,
                new SightingMapper(), date);
        associateLocationAndSuperhero(sightings);
        return sightings;
    }

}

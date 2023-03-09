/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-08
 * purpose: entity class for sighting table
 */
package com.lok.superherosightings.dto;

import java.time.LocalDate;
import java.util.Objects;

public class Sighting {

    private int id;
    private LocalDate date;
    private Superhero superheroId;
    private Location locationId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Superhero getSuperheroId() {
        return superheroId;
    }

    public void setSuperheroId(Superhero superheroId) {
        this.superheroId = superheroId;
    }

    public Location getLocationId() {
        return locationId;
    }

    public void setLocationId(Location locationId) {
        this.locationId = locationId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode(this.date);
        hash = 47 * hash + Objects.hashCode(this.superheroId);
        hash = 47 * hash + Objects.hashCode(this.locationId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sighting other = (Sighting) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.superheroId, other.superheroId)) {
            return false;
        }
        return Objects.equals(this.locationId, other.locationId);
    }

}

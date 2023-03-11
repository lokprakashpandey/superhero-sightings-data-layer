/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-10
 * purpose: Dao interface for Location entity
 */

package com.lok.superherosightings.dao;

import com.lok.superherosightings.dto.Location;
import java.util.List;


public interface LocationDao {

    Location getLocationById(int id);
    List<Location> getAllLocations();
    Location addLocation(Location location);
    void updateLocation(Location location);
    void deleteLocationById(int id);
}

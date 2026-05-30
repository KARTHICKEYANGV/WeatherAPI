package com.WeatherAPI.WrapperService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class WeatherRepository {
    public interface WeatherRepositoryInterface extends JpaRepository<WeatherEntity, Long> {
        // Define methods for data access, e.g., save, findById, findAll, etc.
    }

}

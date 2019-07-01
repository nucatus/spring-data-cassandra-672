package com.sepiat.spring.cassandra.datastore.repos;

import com.sepiat.spring.cassandra.model.ActivityByHour;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.Repository;

import java.time.Instant;
import java.util.UUID;

/**
 * @author alexandru.ionita
 * @since 1.0.0
 */
@Primary
public interface ActivityByHourRepository extends Repository<ActivityByHour, ActivityByHour.Key>
{
    @Query("select * from activity_by_hour where account_id = ?0 and date_to_hour = ?1 LIMIT 1000")
    Iterable<ActivityByHour> fetchActivity(UUID accountId, Instant dateToHour);

    /**
    * @param entity
    * @return the saved entity
    */
    ActivityByHour save(ActivityByHour entity);
}

package com.sepiat.spring.cassandra;

import com.sepiat.spring.cassandra.datastore.repos.ActivityByHourRepository;
import com.sepiat.spring.cassandra.model.ActivityByHour;
import com.sepiat.spring.cassandra.model.ActivityByHour.Key;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.HOURS;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CassandraApplicationTests
{
    @Autowired
    ActivityByHourRepository repo;

    @Test
    public void saveRetrieveTest()
    {
        UUID accountId = UUID.randomUUID();
        UUID actorId = UUID.randomUUID();
        UUID acteeId = UUID.randomUUID();
        Instant eventTime = Instant.now();

        //Key key = new Key( accountId, eventTime.truncatedTo( HOURS ), eventTime );
        Key key = new Key();
        key.setAccountId( accountId );
        key.setDateToHour( eventTime.truncatedTo( HOURS ) );
        key.setEventTime( eventTime );

        ActivityByHour event = new ActivityByHour( key );
        event.setActorId( actorId );
        event.setActeeId( acteeId );

        repo.save( event );

        Iterator<ActivityByHour> iterator = repo.fetchActivity( accountId, eventTime.truncatedTo( HOURS ) ).iterator();
        ActivityByHour retrievedEvent = iterator.next();

        Assertions.assertThat( retrievedEvent ).isEqualToComparingFieldByField( event );
    }

}

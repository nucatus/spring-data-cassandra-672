package com.sepiat.spring.cassandra.model;

import com.datastax.driver.core.DataType;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * @author alexandru.ionita
 * @since 1.0.0
 */
@Table("activity_by_hour")
public class ActivityByHour
{

    @PrimaryKey
    private Key key;

    @Column("site_id")
    @CassandraType(type = DataType.Name.UUID)
    private UUID siteId;

    @Column("actor_id")
    @CassandraType(type = DataType.Name.UUID)
    private UUID actorId;

    @Column("actee_id")
    @CassandraType(type = DataType.Name.UUID)
    private UUID acteeId;

    public ActivityByHour(Key key)
    {
        this.key = key;
    }

    public Key getKey()
    {
        return key;
    }

    public UUID getAccountId()
    {
        return key.getAccountId();
    }

    public Instant getEventTime()
    {
        return key.getEventTime();
    }

    public UUID getSiteId()
    {
        return siteId;
    }

    public void setSiteId(UUID siteId)
    {
        this.siteId = siteId;
    }

    public UUID getActorId()
    {
        return actorId;
    }

    public void setActorId(UUID actorId)
    {
        this.actorId = actorId;
    }

    public UUID getActeeId()
    {
        return acteeId;
    }

    public void setActeeId(UUID acteeId)
    {
        this.acteeId = acteeId;
    }

    /**
     * @author alexandru.ionita@sepiat.com
     * @since 1.0.0, created on 2019-07-01
     */
    @PrimaryKeyClass
    public static class Key implements Serializable
    {
        @CassandraType(type = DataType.Name.UUID)
        @PrimaryKeyColumn(name = "account_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
        private UUID accountId;

        @CassandraType(type = DataType.Name.TIMESTAMP)
        @PrimaryKeyColumn(name = "date_to_hour", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
        private Instant dateToHour;

        @PrimaryKeyColumn(name = "event_ts", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
        private Instant eventTime;

        public UUID getAccountId()
        {
            return accountId;
        }

        public void setAccountId(UUID accountId)
        {
            this.accountId = accountId;
        }

        public Instant getEventTime()
        {
            return eventTime;
        }

        public void setEventTime(Instant eventTime)
        {
            this.eventTime = eventTime;
        }

        public Instant getDateToHour()
        {
            return dateToHour;
        }

        public void setDateToHour(Instant dateToHour)
        {
            this.dateToHour = dateToHour;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (!(o instanceof Key)) return false;
            Key key = (Key) o;
            return Objects.equals( accountId, key.accountId ) &&
                    Objects.equals( eventTime, key.eventTime ) &&
                    Objects.equals( dateToHour, key.dateToHour );
        }

        @Override
        public int hashCode()
        {
            return Objects.hash( accountId, eventTime, dateToHour );
        }
    }
}
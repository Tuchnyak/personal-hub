package net.tuchnyak.util;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * @author tuchnyak (George Shchennikov)
 */
public record TimestampSqlNow(
        Timestamp now
) {
    public TimestampSqlNow() {
        this(Timestamp.from(Instant.now()));
    }
}

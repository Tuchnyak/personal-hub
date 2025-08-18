package net.tuchnyak.uuid;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tuchnyak (George Shchennikov)
 */
class V7TimeBasedUuidGeneratorTest {

    @Test
    void generate() {
        UuidGenerator generator = UuidGeneratorFactory.getV7TimeBasedUuidGenerator();
        var actual = generator.generate();
        assertNotNull(actual);
        assertEquals(36, actual.toString().length());
        assertNotEquals(actual, generator.generate());
    }

}
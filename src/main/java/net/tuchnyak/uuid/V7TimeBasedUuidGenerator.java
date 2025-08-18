package net.tuchnyak.uuid;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochRandomGenerator;

import java.util.UUID;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class V7TimeBasedUuidGenerator implements UuidGenerator {

    private final TimeBasedEpochRandomGenerator generator;

    V7TimeBasedUuidGenerator() {
        this.generator = Generators.timeBasedEpochRandomGenerator();
    }

    @Override
    public UUID generate() {

        return generator.generate();
    }
}

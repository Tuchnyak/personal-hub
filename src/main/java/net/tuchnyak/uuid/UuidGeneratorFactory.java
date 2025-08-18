package net.tuchnyak.uuid;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class UuidGeneratorFactory {

    private static class V7TimeBasedUuidGeneratorHolder {
        public static final V7TimeBasedUuidGenerator INSTANCE = new V7TimeBasedUuidGenerator();
    }

    public static UuidGenerator getV7TimeBasedUuidGenerator() {
        return V7TimeBasedUuidGeneratorHolder.INSTANCE;
    }

}

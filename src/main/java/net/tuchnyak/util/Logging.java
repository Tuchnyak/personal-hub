package net.tuchnyak.util;

import org.slf4j.Logger;

/**
 * @author tuchnyak (George Shchennikov)
 */
public interface Logging {

    default Logger getLogger() {
        return org.slf4j.LoggerFactory.getLogger(this.getClass());
    }

}

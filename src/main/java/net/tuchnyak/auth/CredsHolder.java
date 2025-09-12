package net.tuchnyak.auth;

import net.tuchnyak.util.Logging;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class CredsHolder implements Logging {

    private Optional<String> creds = Optional.empty();

    public CredsHolder(Optional<String> creds) {
        this.creds = creds;
    }

    public void dropCreds() {
        creds = Optional.empty();
        getLogger().info(">>> Credentials dropped");
    }

    public String getUserName() {
        return creds.map(creds -> creds.split(":")[0]).orElse("");
    }

    public String getPassword() {
        return creds.map(creds -> creds.split(":")[1]).orElse("");
    }

    public void ifPresent(Consumer<CredsHolder> action) {
        if (creds.isPresent()) {
            action.accept(this);
        }
    }

}

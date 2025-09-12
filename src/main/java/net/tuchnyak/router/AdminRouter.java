package net.tuchnyak.router;

import net.tuchnyak.config.AppConfigurations;
import net.tuchnyak.element.AdminIndexElement;
import net.tuchnyak.util.Logging;
import rife.authentication.elements.AuthConfig;
import rife.authentication.elements.Authenticated;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class AdminRouter extends AbstractRouter implements Logging {

    private final AppConfigurations appConfigurations;
    private final AuthConfig authConfig;

    public AdminRouter(AppConfigurations appConfigurations, AuthConfig authConfig) {
        super(new AdminIndexElement());
        this.appConfigurations = appConfigurations;
        this.authConfig = authConfig;
    }

    @Override
    public void setup() {
        super.setup();

        before(new Authenticated(authConfig));
        var re = getRootElement();

        getLogger().info(">>> Admin router setup");
    }

}

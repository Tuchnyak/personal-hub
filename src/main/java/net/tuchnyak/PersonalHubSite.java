package net.tuchnyak;

import net.tuchnyak.util.Logging;
import rife.engine.*;

public class PersonalHubSite extends Site implements Logging {

    public void setup() {
        var cv = get("/cv", PathInfoHandling.NONE, c -> c.print(c.template("cv")));
        get("/", c -> c.redirect(cv));

        getLogger().info(">>> PersonalHubSite setup completed");
    }

    public static void main(String[] args) {
        new Server()
            .staticResourceBase("src/main/webapp")
            .start(new PersonalHubSite());
    }
}
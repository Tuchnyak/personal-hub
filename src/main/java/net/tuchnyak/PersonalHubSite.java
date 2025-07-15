package net.tuchnyak;

import rife.engine.*;

public class PersonalHubSite extends Site {
    public void setup() {
        var cv = get("/cv", PathInfoHandling.NONE, c -> c.print(c.template("cv")));
        get("/", c -> c.redirect(cv));
    }

    public static void main(String[] args) {
        new Server()
            .staticResourceBase("src/main/webapp")
            .start(new PersonalHubSite());
    }
}
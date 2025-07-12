package net.tuchnyak;

import rife.engine.*;

public class PersonalHubSite extends Site {
    public void setup() {
        get("/cv", PathInfoHandling.NONE, c -> c.print(c.template("cv")));
        var hello = get("/hello", PathInfoHandling.NONE, c -> c.print(c.template("hello")));

        get("/", c -> c.redirect(hello));
    }

    public static void main(String[] args) {
        new Server()
            .staticResourceBase("src/main/webapp")
            .start(new PersonalHubSite());
    }
}
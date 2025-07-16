package net.tuchnyak;

import rife.engine.Server;

public class PersonalHubSiteUber extends PersonalHubSite {
    public PersonalHubSiteUber() {
        super();
    }

    public static void main(String[] args) {
        new Server()
            .staticUberJarResourceBase("webapp")
            .start(new PersonalHubSiteUber());
    }
}
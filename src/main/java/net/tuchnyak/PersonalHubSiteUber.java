package net.tuchnyak;

import rife.engine.Server;

import java.util.Optional;

public class PersonalHubSiteUber extends PersonalHubSite {
    public PersonalHubSiteUber() {
        super(Optional.empty());
    }

    public static void main(String[] args) {
        new Server()
            .staticUberJarResourceBase("webapp")
            .start(new PersonalHubSiteUber());
    }
}
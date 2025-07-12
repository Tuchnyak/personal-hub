package net.tuchnyak;

import rife.engine.Server;

public class PersonalHubSiteUber extends PersonalHubSite {
    public static void main(String[] args) {
        new Server()
            .staticUberJarResourceBase("webapp")
            .start(new PersonalHubSiteUber());
    }
}
package net.tuchnyak;

import rife.bld.WebProject;
import rife.bld.dependencies.VersionNumber;

import java.util.List;

import static rife.bld.dependencies.Repository.*;
import static rife.bld.dependencies.Scope.*;
import static rife.bld.operations.TemplateType.*;

public class PersonalHubBuild extends WebProject {

    public PersonalHubBuild() {
        pkg = "net.tuchnyak";
        name = "personal-hub";
        mainClass = "net.tuchnyak.PersonalHubSite";
        uberJarMainClass = "net.tuchnyak.PersonalHubSiteUber";
        version = version(0, 0, 2);

        downloadSources = true;
        autoDownloadPurge = true;

        VersionNumber hsqlDbVersion = version(2, 7, 4);
        repositories = List.of(MAVEN_CENTRAL, RIFE2_RELEASES);
        scope(compile)
                .include(dependency("com.uwyn.rife2", "rife2", version(1, 9, 1)))
                .include(dependency("org.hsqldb", "hsqldb", hsqlDbVersion))
                .include(dependency("com.vladsch.flexmark", "flexmark-all", version(0, 64, 0)))
                .include(dependency("com.fasterxml.uuid", "java-uuid-generator", version(5, 1, 0)))
                .include(dependency("org.slf4j", "slf4j-api", version(2, 0, 16)));
        scope(test)
                .include(dependency("org.hsqldb", "hsqldb", hsqlDbVersion))
                .include(dependency("org.jsoup", "jsoup", version(1, 18, 3)))
                .include(dependency("org.junit.jupiter", "junit-jupiter", version(5, 11, 4)))
                .include(dependency("org.junit.platform", "junit-platform-console-standalone", version(1, 11, 4)));
        scope(standalone)
                .include(dependency("org.eclipse.jetty.ee10", "jetty-ee10", version(12, 0, 16)))
                .include(dependency("org.eclipse.jetty.ee10", "jetty-ee10-servlet", version(12, 0, 16)))
                .include(dependency("org.slf4j", "slf4j-simple", version(2, 0, 16)));

        precompileOperation()
                .templateTypes(HTML);
    }

    public static void main(String[] args) {
        new PersonalHubBuild().start(args);
    }
}
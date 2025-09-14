package net.tuchnyak.router;

import net.tuchnyak.element.AdminIndexElement;
import net.tuchnyak.exception.auth.NotAuthenticatedActionException;
import net.tuchnyak.service.PostUploadService;
import net.tuchnyak.util.Logging;
import rife.authentication.credentialsmanagers.DatabaseUsers;
import rife.authentication.elements.AuthConfig;
import rife.authentication.elements.Authenticated;
import rife.authentication.sessionmanagers.DatabaseSessions;
import rife.engine.Context;

import java.util.UUID;

import static net.tuchnyak.auth.AuthDbInitializer.ADMIN;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class AdminRouter extends AbstractRouter implements Logging {

    public static final String AUTH_ID = "authId";

    private final AuthConfig authConfig;
    private final PostUploadService postService;
    private final DatabaseUsers dbUsers;
    private final DatabaseSessions dbSessions;

    public AdminRouter(AuthConfig authConfig, PostUploadService postService, DatabaseUsers dbUsers, DatabaseSessions dbSessions) {
        super(new AdminIndexElement(postService));
        this.authConfig = authConfig;
        this.postService = postService;
        this.dbUsers = dbUsers;
        this.dbSessions = dbSessions;
    }

    @Override
    public void setup() {
        super.setup();
        before(new Authenticated(authConfig));

        post("/", (c) -> {
            if (notAuthenticated(c)) {
                throw new NotAuthenticatedActionException("Not authenticated to manage posts! authId cookie is not valid (%s)!".formatted(c.cookieValue(AUTH_ID)));
            }
            var id = UUID.fromString(c.parameter("postId"));
            switch (c.parameter("actionType").toUpperCase()) {
                case "PUBLISH" -> postService.publishPost(id);
                case "UNPUBLISH" -> postService.unpublishPost(id);
                case "DELETE" -> postService.deletePost(id);
                default -> getLogger().warn("Unknown action type: {}", c.parameter("actionType"));
            }
            c.redirect(getRootRoute());
        });

        getLogger().info(">>> Admin router setup");
    }

    private boolean notAuthenticated(Context c) {
        var ret = false;
        try {
            return !dbUsers.isUserInRole(
                    dbSessions.getSessionUserId(c.cookieValue(AUTH_ID)),
                    ADMIN
            );
        } catch (Exception e) {
            getLogger().warn(">>> Error during session checks!");
        }

        return ret;
    }

}

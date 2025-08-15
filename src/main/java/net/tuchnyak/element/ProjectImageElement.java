package net.tuchnyak.element;

import net.tuchnyak.service.ProjectServiceImpl;
import rife.engine.Context;
import rife.engine.Element;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class ProjectImageElement implements Element {

    private final ProjectServiceImpl projectService;

    public ProjectImageElement(ProjectServiceImpl projectService) {
        this.projectService = projectService;
    }

    public void process(Context c) throws Exception {
        c.setContentType("image/png");
        int id = Integer.parseInt(c.parameter("image_id"));
        try (var outputStream = c.outputStream()) {
            outputStream.write(projectService.getProjectImageById(id).getImage_data());
            outputStream.flush();
        }
    }

}

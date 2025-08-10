package net.tuchnyak.element;

import net.tuchnyak.service.ProjectServiceImpl;
import rife.engine.Context;
import rife.engine.Element;

import java.io.IOException;
import java.util.Objects;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class ProjectImageElement implements Element {

    private final int projectId;
    private final ProjectServiceImpl projectService;

    public ProjectImageElement(String projectId, ProjectServiceImpl projectService) {
        Objects.requireNonNull(projectId, "projectId must not be null");
        this.projectId = Integer.parseInt(projectId);
        this.projectService = projectService;
    }

    public void process(Context c) throws Exception {
        c.setContentType("image/png");
        try (var outputStream = c.outputStream()) {
            projectService.getAllProjectsWithImages().stream()
                    .filter(dto -> dto.project().getId() == projectId)
                    .flatMap(dto -> dto.projectImageList().stream())
                    .forEach(image -> {
                        try {
                            outputStream.write(image.getImage_data());
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to write image data to output stream", e);
                        }
                    });
            outputStream.flush();
        }
    }

}

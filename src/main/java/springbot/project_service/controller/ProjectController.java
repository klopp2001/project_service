package springbot.project_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springbot.com_dto.ProjectEventDto;
import springbot.project_service.dto.CreateProjectDTO;
import springbot.project_service.kafka.producer.ProjectEventProducer;
import springbot.project_service.service.ProjectService;

@RestController()
public class ProjectController {
    private final ProjectEventProducer projectEventProducer;

    public ProjectController(ProjectEventProducer projectEventProducer) {
        this.projectEventProducer = projectEventProducer;
    }

    @PostMapping("/create")
    public String createProject(@RequestBody CreateProjectDTO createProjectDTO) {

        ProjectEventDto dto = ProjectEventDto.builder()
                .eventType("project-created")
                .name(createProjectDTO.getName())
                .key(createProjectDTO.getKey())
                .ownerId(Long.valueOf(createProjectDTO.getOwnerId()))
                .build();

        projectEventProducer.sendProjectCreated(dto);
        return "creation of project is pending";
    }

    @GetMapping("/get")
    public String getProjects(@RequestParam Long userId) {
        ProjectEventDto dto = ProjectEventDto.builder()
                .eventType("project-created")
                .name(null)
                .key(null)
                .ownerId(userId)
                .build();
        projectEventProducer.getProjects(dto);
        return "getting projects is pending";
    }

}

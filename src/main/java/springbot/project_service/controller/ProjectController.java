package springbot.project_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springbot.com_dto.ProjectEventDto;
import springbot.project_service.dto.CreateProjectDTO;
import springbot.project_service.kafka.producer.ProjectEventProducer;
import springbot.project_service.service.ProjectService;

@RestController("/project")
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

}

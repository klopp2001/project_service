package springbot.project_service.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import springbot.com_dto.NotificationEventDto;
import springbot.com_dto.ProjectEventDto;
import springbot.project_service.kafka.producer.ProjectEventProducer;
import springbot.project_service.service.ProjectService;

@Service
public class ProjectEventConsumer {
    private final ProjectService projectService;
    private final ProjectEventProducer projectEventProducer;

    public ProjectEventConsumer(ProjectService projectService, ProjectEventProducer projectEventProducer) {
        this.projectService = projectService;
        this.projectEventProducer = projectEventProducer;
    }

    @KafkaListener(topics = "project.created", groupId = "project-creator")
    public void consumeProjectCreated(ProjectEventDto projectEventDto) {
        projectService.createProject(projectEventDto.getName(), projectEventDto.getKey(), projectEventDto.getOwnerId());

        projectEventProducer.sendNotification(new NotificationEventDto(projectEventDto.getOwnerId(),
                "{\"payload\": \" created " + projectEventDto.getName() +"\"}" ));
    }

    @KafkaListener(topics = "project.user-added", groupId = "project-creator")
    public void consumeUserAdded(ProjectEventDto projectEventDto) {

    }


}

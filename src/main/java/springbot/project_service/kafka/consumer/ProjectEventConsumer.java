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

    @KafkaListener(topics = "project", groupId = "project-creator")
    public void consume(ProjectEventDto projectEventDto) {
        if (projectEventDto.getEventType().equals("created")) {
            projectService.createProject(projectEventDto.getName(), projectEventDto.getKey(), projectEventDto.getOwnerId());

            projectEventProducer.sendNotification(new NotificationEventDto(projectEventDto.getOwnerId(),
                    "project created"));
        }

    }



}

package springbot.project_service.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import springbot.com_dto.NotificationEventDto;
import springbot.com_dto.ProjectEventDto;

@Service
@RequiredArgsConstructor
public class ProjectEventProducer {
    private final KafkaTemplate<String, ProjectEventDto> projectKafkaTemplate;
    private final KafkaTemplate<String, NotificationEventDto> notificationEventDtoKafkaTemplate;

    public void sendProjectCreated(ProjectEventDto dto) {
        projectKafkaTemplate.send("project.created", dto);
    }

    public void sendProjectUserAdded(ProjectEventDto dto) {
        projectKafkaTemplate.send("project.user-added", dto);
    }

    public void sendProjectUpdated(ProjectEventDto dto) {
        projectKafkaTemplate.send("project.updated", dto);
    }

    public void sendProjectDeleted(ProjectEventDto dto) {
        projectKafkaTemplate.send("project.deleted", dto);
    }

    public void sendNotification(NotificationEventDto dto) {
        notificationEventDtoKafkaTemplate.send("notification.project", dto);
    }
}

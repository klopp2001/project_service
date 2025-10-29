package springbot.project_service.kafka.consumer;

import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import springbot.com_dto.NotificationEventDto;
import springbot.com_dto.ProjectEventDto;
import springbot.project_service.kafka.producer.ProjectEventProducer;
import springbot.project_service.model.ProjectMembers;
import springbot.project_service.model.Projects;
import springbot.project_service.service.ProjectService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", String.valueOf(projectEventDto.getOwnerId()));
        jsonObject.put("type", "eventStatus");
        jsonObject.put("data", "success");

        // Convert the JSONObject to a String
        String jsonString = jsonObject.toString();

        projectEventProducer.sendNotification(new NotificationEventDto(projectEventDto.getOwnerId(),
                jsonString ));
    }

    @KafkaListener(topics = "project.get", groupId = "project-creator")
    public void consumeProjectGet(ProjectEventDto projectEventDto) {
        Optional<List<ProjectMembers>> projects =  projectService.getAllProjectsFromUser(projectEventDto.getOwnerId());

        if (projects.isEmpty()) {

        } else {
            List<JSONObject> jsonNotification = new ArrayList<>();
            for (var project: projects.get()) {
                JSONObject object = new JSONObject();
                object.put("name", project.getProject().getName());
                object.put("description", project.getProject().getDescription());
                jsonNotification.add(object);
            }

            JSONObject res = new JSONObject();
            res.put("tasks", jsonNotification);
            projectEventProducer.sendNotification("notifications.render.tasks", new NotificationEventDto(projectEventDto.getOwnerId(),
                    res.toString() ));


//            Arrays.toString(jsonNotification);
        }

    }

    @KafkaListener(topics = "project.user-added", groupId = "project-creator")
    public void consumeUserAdded(ProjectEventDto projectEventDto) {

    }


}

package springbot.project_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbot.com_dto.ProjectEventDto;
import springbot.project_service.kafka.producer.ProjectEventProducer;
import springbot.project_service.model.ProjectMembers;
import springbot.project_service.model.Projects;
import springbot.project_service.repository.ProjectMembersRepository;
import springbot.project_service.repository.ProjectsRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectsRepository projectsRepository;
    private final ProjectMembersRepository projectMembersRepository;

    @Autowired
    private final ProjectEventProducer eventProducer;

    public Projects createProject(String name, String key, Long ownerId) {
        Projects resProj = new Projects();
        ProjectMembers owner = new ProjectMembers();

        resProj.setOwnerId(ownerId);
        resProj.setName(name);
        resProj.setKey(key);

        projectsRepository.save(resProj);

        owner.setProject(resProj);
        owner.setUserId(ownerId);
        owner.setRole("ADMIN");

        projectMembersRepository.save(owner);

        return resProj;
    }

    public ProjectMembers addUserToProject(Long projectId, Long userId, String role) {
        ProjectMembers user = new ProjectMembers();
        final Optional<Projects> project = projectsRepository.findById(projectId);


        if (project.isEmpty()) {
            String message = "TRYING ADD USER TO NON EXISTING PROJECT " +  projectId;
            log.warn(message);
            throw new RuntimeException(message);
        }

        user.setProject(project.get());
        user.setUserId(userId);
        user.setRole(role);

        return projectMembersRepository.save(user);
    }
}

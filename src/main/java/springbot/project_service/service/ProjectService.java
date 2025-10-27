package springbot.project_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbot.com_dto.ProjectEventDto;
import springbot.project_service.kafka.producer.ProjectEventProducer;
import springbot.project_service.model.ProjectMembers;
import springbot.project_service.model.Projects;
import springbot.project_service.repository.ProjectMembersRepository;
import springbot.project_service.repository.ProjectsRepository;

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

        owner.setProject(resProj);
        owner.setUserId(ownerId);
        owner.setRole("ADMIN");

        projectMembersRepository.save(owner);

        return resProj;
    }

    public void addUserToProject(Long projectId, Long userId, String role) {

    }
}

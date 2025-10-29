package springbot.project_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbot.project_service.model.ProjectMembers;
import springbot.project_service.model.Projects;

import java.util.List;
import java.util.Optional;

public interface ProjectMembersRepository extends JpaRepository<ProjectMembers, Long> {
    Optional<List<ProjectMembers>> findProjectsByUserId(Long userId);

}

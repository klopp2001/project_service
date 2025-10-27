package springbot.project_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbot.project_service.model.ProjectMembers;

public interface ProjectMembersRepository extends JpaRepository<ProjectMembers, Long> {
}

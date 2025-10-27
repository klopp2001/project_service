package springbot.project_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbot.project_service.model.Projects;

import java.util.List;

public interface ProjectsRepository extends JpaRepository<Projects, Long> {

}

package tesis.tesisnotifications.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tesis.tesisnotifications.entities.NotificationLogEntity;
@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLogEntity, Long> {
}

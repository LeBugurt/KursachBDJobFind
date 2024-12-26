package bd.hh.kursach.repository;

import bd.hh.kursach.model.Status;
import bd.hh.kursach.model.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StatusRepository extends JpaRepository<Status, UUID> {
    @Query("SELECT v.id FROM Status v WHERE v.status = :status")
    UUID findByStatus(StatusEnum status);
}

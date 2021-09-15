package smu.earthranger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smu.earthranger.domain.Carbon;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CarbonRepository extends JpaRepository<Carbon, Long> {

    Optional<Carbon> findByMemberIdAndCreatedDate(Long memberId, LocalDateTime now);
}

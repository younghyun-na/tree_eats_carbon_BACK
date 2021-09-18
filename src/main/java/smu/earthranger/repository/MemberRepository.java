package smu.earthranger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smu.earthranger.domain.Member;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
        Member findUserByEmail(String email);

        Optional<Member> findUserByName(String name);
}


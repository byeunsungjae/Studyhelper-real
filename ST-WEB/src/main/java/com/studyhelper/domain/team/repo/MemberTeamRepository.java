package com.studyhelper.domain.team.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.studyhelper.domain.team.entity.MemberTeam;

@Repository
public interface MemberTeamRepository extends JpaRepository<MemberTeam, Long> {

}

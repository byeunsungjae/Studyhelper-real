package com.studyhelper.domain.team;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.studyhelper.domain.entity.Board;
import com.studyhelper.domain.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
	List<Team> findByTeamSeq(Long teamSeq);

}

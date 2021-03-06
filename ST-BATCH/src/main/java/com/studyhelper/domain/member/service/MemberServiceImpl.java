package com.studyhelper.domain.member.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studyhelper.domain.matching.entity.Matching;
import com.studyhelper.domain.matching.repo.MatchingRepository;
import com.studyhelper.domain.member.entity.Member;
import com.studyhelper.domain.member.repo.MemberRepository;
import com.studyhelper.domain.team.entity.MemberTeam;
import com.studyhelper.domain.team.entity.Team;
import com.studyhelper.domain.team.repo.MemberTeamRepository;
import com.studyhelper.domain.team.repo.TeamRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;
	private final MemberTeamRepository memberTeamRepository;
	private final TeamRepository teamRepository;
	private final MatchingRepository matchingRepository;

	@Transactional
	@Override
	public List<Matching> findMatchingsById(String id) {

		return matchingRepository.findAll();
	}
	
	@Transactional
	@Override
	public MemberTeam matchingTeamByMembers(Member member, Team team) {
		MemberTeam memberTeam = new MemberTeam();
		memberTeam.setMember(member);
		memberTeam.setTeam(team);
		
		memberTeamRepository.save(memberTeam);
		return memberTeam;
	}

}

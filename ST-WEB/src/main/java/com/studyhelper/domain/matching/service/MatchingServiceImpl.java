package com.studyhelper.domain.matching.service;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.studyhelper.domain.matching.entity.Matching;
import com.studyhelper.domain.matching.repo.MatchingRepository;
import com.studyhelper.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchingServiceImpl implements MatchingService {
	private final MatchingRepository matchingRepository;
	private final ProduceMatching produceMatcing;
	
	@Override
	@Transactional
	public void saveAndSendMatching(Member member, Matching matching) {
		matching.setMemberId(member.getId());
		matching.setRequestMatchingDate(LocalDate.now().toString());

		log.info("매칭요청 -->  " + "요청아이디: " + member.getId() + " 지역: " + matching.getRegion() + " 주제: "
				+ matching.getSubject() + " 인원: " + matching.getSize());
		matchingRepository.save(matching);
		
		//매칭하는 도메인에 전송
		produceMatcing.sendMatching(matching);
	}

}

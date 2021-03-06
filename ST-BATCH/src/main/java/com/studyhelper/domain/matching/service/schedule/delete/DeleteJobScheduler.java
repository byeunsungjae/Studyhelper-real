package com.studyhelper.domain.matching.service.schedule.delete;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import org.hibernate.annotations.Synchronize;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.studyhelper.domain.matching.entity.Matching;
import com.studyhelper.domain.matching.entity.enums.Region;
import com.studyhelper.domain.matching.entity.enums.Subject;
import com.studyhelper.domain.matching.repo.MatchingRepository;
import com.studyhelper.domain.matching.service.MatchingService;
import com.studyhelper.domain.matching.service.schedule.match.algorithm.Matcher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteJobScheduler {
	private final MatchingRepository matchingRepository;
	private final Matcher matchTrie;

	private final int REGION_LENGTH = Region.values().length;
	private final int SUBJECT_LENGTH = Subject.values().length;
	private final int MAX_SIZE = 6;

	// 로그사용하기 위해서 expire 사용안함
	@Scheduled(fixedDelay = 60000) // 하루에 한번 실행
	public void runDeleteJob() {
		log.info("매칭 삭제 시작!!");
		synchronized (this) {
			for (int i = 0; i < REGION_LENGTH; i++) {
				for (int j = 0; j < SUBJECT_LENGTH; j++) {
					for (int k = 0; k < MAX_SIZE; k++) {
						HashSet<Matching> matchings = matchTrie.matchs[i][j][k];
						HashSet<Matching> removeMatchs = new HashSet<Matching>();
						for (Matching matching : matchings) {
							log.info("들어있는 매칭들" + matching.getMemberId() + " " + matching.getRegion().toString() + " "
									+ matching.getSeq());
							LocalDate localDate = LocalDate.parse(matching.getRequestMatchingDate(),
									DateTimeFormatter.ISO_DATE);
							LocalDate leastDay = LocalDate.now();
							leastDay = leastDay.minusDays(3);

							if (localDate.isBefore(leastDay)) {
								log.info("매칭 삭제 --> 매칭 정보: " + matching.getMemberId() + "의 매칭 삭제");
								removeMatchs.add(matching);
								matchingRepository.delete(matching);
							}
						}
						for (Matching matching : removeMatchs) {
							matchTrie.matchs[i][j][k].remove(matching);
						}
					}
				}
			}
		}
	}
}

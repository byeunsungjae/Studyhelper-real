package com.studyhelper.domain.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studyhelper.domain.board.entity.Board;
import com.studyhelper.domain.board.exception.BoardNotSatisfiyInsert;
import com.studyhelper.domain.board.repo.BoardRepository;
import com.studyhelper.domain.matching.entity.Matching;
import com.studyhelper.domain.matching.entity.enums.Gender;
import com.studyhelper.domain.matching.entity.enums.Region;
import com.studyhelper.domain.matching.entity.enums.Subject;
import com.studyhelper.domain.matching.repo.MatchingRepository;
import com.studyhelper.domain.member.entity.Member;
import com.studyhelper.domain.member.entity.enums.Role;
import com.studyhelper.domain.member.repo.MemberRepository;
import com.studyhelper.domain.member.security.SecurityUser;
import com.studyhelper.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestRestController {
	private final MatchingRepository matchingRepository;
	private final MemberRepository memberRepository;
	private final MemberService memberService;
	private final BoardRepository boardRepository;
	
	@GetMapping("/board/test")
	public Board getBoardTest(@RequestParam("seq") long seq) {
		Board board = boardRepository.findBoardByIdFetchTeam(seq);

		return null;
	}
	
	@GetMapping("/matching/all")
	public List<Matching> getMatchingAll() {
		return matchingRepository.findAll();
	}

	@GetMapping("/board/all")
	public String getBoardAll(@AuthenticationPrincipal SecurityUser securityUser) {

		Member member = memberRepository.findById(securityUser.getMember().getId()).get();
		List<Board> boards = member.getBoards();

		for (Board board : boards) {
			System.out.println(board.getTitle());
		}

		return "1";
	}

	@GetMapping("/matching/make")
	public void makeMatchingTest() {

		for (int i = 0; i < 100; i++) {
			Member member = memberRepository.findById("king" + i).get();
			Matching matching = new Matching();
			matching.setRegion(Region.values()[(i + 3) % 4]);
			matching.setSubject(Subject.values()[(i + 3) % 4]);
			matching.setSize(4);
			matching.setMemberId(member.getId());
			matching.setRequestMatchingDate(LocalDate.now().toString());

			matchingRepository.save(matching);
		}
	}

	@GetMapping("/matching/teams")
	public void matchingTeams() {
		List<Member> members = memberRepository.findAll();
		for (Member member : members) {
			System.out.println(member.getId() + "  " + member.getMemberTeams().size());
		}
	}

	@GetMapping("/member/make")
	public void makeMember() {
		for (int i = 0; i < 100; i++) {
			Member member = new Member();
			member.setAge(26);
			member.setGender(Gender.MAN);
			member.setId("king" + i);
			member.setName("king" + i);
			member.setNickName("king" + i);
			member.setPassword("1234");
			member.setRole(Role.ROLE_MEMBER);

			memberService.saveMember(member);
		}
	}

	@GetMapping("/exception/test")
	public void makeException() {

		throw new BoardNotSatisfiyInsert("");
	}
}

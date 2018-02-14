package name.pusz.library.controller;

import name.pusz.library.controller.exception.MemberNotFoundException;
import name.pusz.library.domain.dto.incoming.MemberDtoIn;
import name.pusz.library.domain.dto.outgoing.MemberDtoOut;
import name.pusz.library.mapper.MemberMapper;
import name.pusz.library.repository.MemberRepository;
import name.pusz.library.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/member")
@CrossOrigin(origins = "*")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private DbService dbService;

    @GetMapping(value = "getById")
    public MemberDtoOut getBook(@RequestParam Long memberId) throws MemberNotFoundException {
        return memberMapper.mapToMemberDtoOut(memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new));
    }

    @GetMapping(value = "getByLastName")
    public List<MemberDtoOut> getMembersByLastName(@RequestParam String lastName) {
        return memberMapper.mapToMemberDtoOutList(memberRepository.findByLastName(lastName));
    }

    @PostMapping(value = "create", consumes = APPLICATION_JSON_VALUE)
    public MemberDtoOut createMember(@RequestBody MemberDtoIn memberDtoIn) {
        return memberMapper.mapToMemberDtoOut(dbService.createMember(memberDtoIn.getFirstName(), memberDtoIn.getLastName()));
    }

    @PutMapping(value = "update", consumes = APPLICATION_JSON_VALUE)
    public MemberDtoOut updateMember(@RequestBody MemberDtoIn memberDtoIn) {
        return memberMapper.mapToMemberDtoOut(memberRepository.save(memberMapper.mapToMember(memberDtoIn)));
    }

    @DeleteMapping(value = "delete")
    public void deleteMember(@RequestParam Long memberId) {
        memberRepository.delete(memberId);
    }
}

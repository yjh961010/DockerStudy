package com.example.neoheulge.acount.web;

import com.example.neoheulge.dto.MemberDTO;
import com.example.neoheulge.dto.NeAcountDTO;
import com.example.neoheulge.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;

import com.example.neoheulge.acount.service.AcountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/acount")
public class AcountController {

	@Autowired
	private AcountService acountService;
	
	@Autowired
	private MemberService memberService;

	@PostMapping("/insertNeacount.do")
	public String addAccount(NeAcountDTO dto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		// 주계좌 설정 전 모든 계좌의 상태를 'N'으로 변경
		acountService.addAccount(dto);

		return "redirect:/member/myPage.do?user=" + username;
	}

	@GetMapping("/insertNeacountform.do")
	public String addAccountForm() {
		return "member/acount";
	}

	@PostMapping("/deleteNeacount.do")
	public String deleteAccount(@RequestParam("acount_id") String acountId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		NeAcountDTO dto = new NeAcountDTO();
		dto.setAcount_id(acountId);

		acountService.removeAccount(dto);

		return "redirect:/member/myPage.do?user=" + username;
	}

	@GetMapping("/add.do")
	public String addAccount(HttpServletRequest req,@RequestParam String member) {
		String email = memberService.IdEmail(member);
		System.out.println("id :"+member);
		System.out.println("메일 :"+email);
		req.setAttribute("id", member);
		req.setAttribute("mail", email);
		return "checkMe";
	}

}

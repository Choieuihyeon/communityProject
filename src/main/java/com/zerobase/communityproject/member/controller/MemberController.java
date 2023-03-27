package com.zerobase.communityproject.member.controller;

import com.zerobase.communityproject.exception.CustomException;
import com.zerobase.communityproject.member.dto.MemberDto;
import com.zerobase.communityproject.member.model.MemberInput;
import com.zerobase.communityproject.member.model.ResetPasswordInput;
import com.zerobase.communityproject.member.service.MemberService;
import com.zerobase.communityproject.post.model.ServiceResult;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class MemberController {

	private final MemberService memberService;

	// 구현 완료
	@RequestMapping("/member/login")
	public String login() {

		return "member/login";
	}

	// 구현 완료
	@GetMapping("/member/register")
	public String register() {

		return "member/register";
	}

	// 구현 완료
	@PostMapping("/member/register")
	public String registerSubmit(Model model, MemberInput parameter) {

		boolean result = memberService.register(parameter);
		model.addAttribute("result", result);

		return "member/register_complete";
	}

	// 구현 완료
	@GetMapping("/member/info")
	public String memberInfo(Model model, Principal principal) {

		String userId = principal.getName();
		MemberDto detail = memberService.detail(userId);

		model.addAttribute("detail", detail);

		return "member/info";
	}

	// 구현 완료
	@PostMapping("/member/info")
	public String memberInfoSubmit(Model model
		, MemberInput parameter
		, Principal principal) {

		String userId = principal.getName();
		parameter.setUserId(userId);

		CustomException result = memberService.updateMember(parameter);

		return "redirect:/member/info";
	}

	// 구현 완료
	@GetMapping("/member/find/password")
	public String findPassword() {

		return "member/find_password";
	}

	// 구현 완료
	@PostMapping("/member/find/password")
	public String findPasswordSubmit(Model model, ResetPasswordInput parameter) {

		boolean result = false;
		try {
			result = memberService.sendResetPassword(parameter);
		} catch (Exception e) {
		}
		model.addAttribute("result", result);

		return "/member/find_password_result";
	}

	// 구현 완료
	@GetMapping("/member/password")
	public String memberPassword(Model model, Principal principal) {

		String userId = principal.getName();
		MemberDto detail = memberService.detail(userId);

		model.addAttribute("detail", detail);

		return "member/password";
	}

	// 구현 완료
	@PostMapping("/member/password")
	public String memberPasswordSubmit(Model model
		, MemberInput parameter
		, Principal principal) {

		String userId = principal.getName();
		parameter.setUserId(userId);

		CustomException result = memberService.updateMemberPassword(parameter);

		return "redirect:/member/info";
	}

	// 구현 완료
	@GetMapping("/member/reset/password")
	public String resetPassword(Model model, HttpServletRequest request) {

		String uuid = request.getParameter("id");

		boolean result = memberService.checkResetPassword(uuid);
		model.addAttribute("result", result);

		return "member/reset_password";
	}

	// 구현 완료
	@PostMapping("/member/reset/password")
	public String resetPasswordSubmit(Model model, ResetPasswordInput parameter) {

		boolean result = false;
		try {
			result = memberService.resetPassword(parameter.getId(), parameter.getPassword());
		} catch (Exception e) {
		}
		model.addAttribute("result", result);

		return "member/reset_password_result";
	}

	// 구현 완료
	@GetMapping("/member/withdraw")
	public String memberWithdraw() {

		return "member/withdraw";
	}

	// 구현 완료
	@PostMapping("/member/withdraw")
	public String memberWithdrawSubmit(Model model
		, MemberInput parameter
		, Principal principal) {

		String userId = principal.getName();

		CustomException result = memberService.withdraw(userId, parameter.getPassword());

		return "redirect:/member/logout";
	}
}

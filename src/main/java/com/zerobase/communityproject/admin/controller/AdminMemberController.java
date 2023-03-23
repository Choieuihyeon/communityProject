package com.zerobase.communityproject.admin.controller;

import com.zerobase.communityproject.member.dto.MemberDto;
import com.zerobase.communityproject.member.model.MemberParam;
import com.zerobase.communityproject.member.service.MemberService;
import com.zerobase.communityproject.post.controller.BaseController;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class AdminMemberController extends BaseController {

	private final MemberService memberService;

	// 구현 완료
	@GetMapping("/admin/member/list")
	public String list(Model model, MemberParam parameter) {

		parameter.init();

		List<MemberDto> members = memberService.list(parameter);

		long totalCount = 0;
		if (members != null && members.size() > 0) {
			totalCount = members.get(0).getTotalCount();
		}

		String queryString = parameter.getQueryString();
		String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(),
			parameter.getPageIndex(), queryString);

		model.addAttribute("list", members);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pager", pagerHtml);

		return "/admin/member/list";
	}

	// 구현 완료
	@GetMapping("/admin/member/detail/{userId}")
	public String memberDetail(Model model, MemberParam parameter) {

		parameter.init();

		MemberDto detail = memberService.frontDetail(parameter.getUserId());

		model.addAttribute("member", detail);

		return "admin/member/detail";
	}

}

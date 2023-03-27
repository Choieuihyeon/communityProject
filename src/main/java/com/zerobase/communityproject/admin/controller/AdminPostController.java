package com.zerobase.communityproject.admin.controller;

import com.zerobase.communityproject.post.controller.BaseController;
import com.zerobase.communityproject.post.dto.PostDto;
import com.zerobase.communityproject.post.model.PostInput;
import com.zerobase.communityproject.post.model.PostParam;
import com.zerobase.communityproject.post.service.PostService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class AdminPostController extends BaseController {

	private final PostService postService;

	// 구현 완료
	@GetMapping("/admin/post/list")
	public String list(Model model, PostParam parameter) {

		parameter.init();

		List<PostDto> postList = postService.list(parameter);

		long totalCount = 0;
		if (!CollectionUtils.isEmpty(postList)) {
			totalCount = postList.get(0).getTotalCount();
		}

		String queryString = parameter.getQueryString();
		String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(),
			parameter.getPageIndex(), queryString);

		model.addAttribute("list", postList);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pager", pagerHtml);

		return "admin/post/list";
	}

	// 구현 완료
	@GetMapping("/admin/post/detail/{id}")
	public String detail(Model model, PostParam parameter) {

		parameter.init();

		PostDto detail = postService.frontDetail(parameter.getId());

		model.addAttribute("post", detail);

		return "/admin/post/detail";
	}

	// 구현 완료
	@PostMapping("/admin/post/delete")
	public String delete(PostInput parameter) {

		postService.delete(parameter.getIdList());

		return "redirect:/admin/post/list";
	}
}




package com.zerobase.communityproject.post.controller;

import com.zerobase.communityproject.member.service.MemberService;
import com.zerobase.communityproject.post.dto.PostDto;
import com.zerobase.communityproject.post.model.PostInput;
import com.zerobase.communityproject.post.model.PostParam;
import com.zerobase.communityproject.post.service.PostService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PostController extends BaseController {

	private final PostService postService;
	private final MemberService memberService;

	@GetMapping ("/post/list")
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

		return "post/list";
	}

	// 수정은 아직 구현 x
	@GetMapping (value = {"/post/add", "/post/edit"})
	public String add(Model model, HttpServletRequest request, PostInput parameter) {

		boolean editMode = request.getRequestURI().contains("/edit.do");
		PostDto detail = new PostDto();

		if (editMode) {
			long id = parameter.getId();
			PostDto exitPost = postService.getById(id);
			if (exitPost == null) {
				// error 처리
				model.addAttribute("message", "게시물정보가 존재하지 않습니다.");
				return "common/error";
			}
			detail = exitPost;
		}

		model.addAttribute("editMode", editMode);
		model.addAttribute("detail", detail);
		return "post/add";
	}

	@PostMapping (value = {"/post/add", "/post/edit"})
	public String addSubmit(Model model, HttpServletRequest request
		, PostInput parameter) {

		boolean editMode = request.getRequestURI().contains("/edit.do");

		if (editMode) {
			long id = parameter.getId();
			PostDto existPost = postService.getById(id);
			if (existPost == null) {
				// error 처리
				model.addAttribute("message", "게시물정보가 존재하지 않습니다.");
				return "common/error";
			}
			boolean result = postService.set(parameter);
		} else {
			boolean result = postService.add(parameter);
		}

		return "redirect:/post/list";
	}

	// 구현중
	@PostMapping ("/post/delete")
	public String delete(Model model, HttpServletRequest request, PostInput parameter) {

		boolean result = postService.delete(parameter.getIdList());

		return "redirect:/post/list";
	}

	@GetMapping ("/post/detail")
	public String detail(Model model, PostParam parameter) {

		parameter.init();

		PostDto post = postService.detail(parameter.getTitle());
		model.addAttribute("post", post);

		return "/post/detail";
	}

}

package com.sns.timeline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sns.post.bo.PostBO;
import com.sns.timeline.bo.TimelineBO;
import com.sns.timeline.domain.CardView;

import jakarta.servlet.http.HttpSession;

@Controller
public class TimelineController {
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private TimelineBO timelineBO;

	@GetMapping("/timeline/timeline-view")
	// session에서 꺼내서 bo로 넘긴다
	public String timelineView(Model model, HttpSession session) {
		
		// Integer -> 로그인 안되도 타임라인 화면에 접근가능
		// Integer -> 비로그인도 접근 가능하게 한다 (null 가능)
		// int는 null 불가능
		// userId 이므로 int타입 -> Integer
		Integer userId = (Integer) session.getAttribute("userId");
		
		List<CardView> cardViewList = timelineBO.generateCardViewList(userId);
		
		model.addAttribute("cardViewList", cardViewList);
		
		return "timeline/timeline";
	}
}


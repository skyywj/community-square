package com.carryjey.social.freemakeDdemo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloFreeMarkerController {
	/**
	 * 可以对请求参数addAttribute（），作用域为当前调用页面
	 * 也可以使用session.setAttribute(),作用域为整个程序所有页面
	 * 对比见indexApiControoller.longin()以及login.ftl
	 */
	@RequestMapping("/helloFreeMarker")
	public String helloFreeMarker(Model model) {
		model.addAttribute("name","hello-ttt");
		return "helloFreeMarker";
	}

}

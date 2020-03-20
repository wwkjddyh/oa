/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oa.platform.web.controller;

import com.baidu.ueditor.ActionEnter;
import com.oa.platform.common.Constants;
//import com.oa.platform.ueditor.ActionEnter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 主页控制器
 * @author Feng
 * @date 2019/03/01
 */
@Controller
public class MainController {

	protected final static Logger LOGGER = LoggerFactory.getLogger("MainController");

	/*@RequestMapping("/")
	public String root() {
		return "redirect:/index";
	}*/

	@RequestMapping(value = {"/index", "/"}, method = {RequestMethod.POST, RequestMethod.GET})
	public String index() {
		return "login";
	}

	@RequestMapping(value = "/user/index", method = {RequestMethod.POST, RequestMethod.GET})
	public String userIndex() {
		return "redirect:/admin/main/";
	}

	@RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
	public String login() {
		return "login";
	}

	/**
	 * 特别配置，因为Spring Security会自动产生这个路径:/logout
	 * @return
	 */
	@RequestMapping(value = "/logout", method = {RequestMethod.POST, RequestMethod.GET})
	public String logout() {
		return "redirect:/login";
	}

	@RequestMapping(value = "/login-error", method = {RequestMethod.POST, RequestMethod.GET})
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}

	@RequestMapping(value = "/401", method = {RequestMethod.POST, RequestMethod.GET})
	public String accesssDenied() {
//		return "401";
		return "login";
	}

	@GetMapping("/test")
	public String test() {
		return "test";
	}

	@RequestMapping("/test-pager")
	public String testPager() {
		return "test-pager";
	}

	@RequestMapping("/test-menu")
	public String testMenu() {
		return "test-menu";
	}

	@RequestMapping("/pager")
	public String pager() {
		return "test-pager2";
	}

	@RequestMapping("/test-editor")
	public String testEditor() {
		return "test-editor";
	}

	@RequestMapping("/test-editor2")
	public String testEditor2() {
		return "test-editor2";
	}

	@RequestMapping("/test-load")
	public String testLoad() {
		return "test-load";
	}

	@RequestMapping("/test/ueditor")
	public String testUeditor() {
		return "ueditor";
	}

//	@RequestMapping("/ueditor/config")
//	public void config(HttpServletRequest request, HttpServletResponse response) {
//		response.setContentType("application/json");
//		String rootPath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/js/ueditor/jsp";
//		try {
//			response.setCharacterEncoding("UTF-8");
//			String exec = new ActionEnter(request, rootPath).exec();
//			LOGGER.debug("exec => " + exec);
//			PrintWriter printWriter = response.getWriter();
//			printWriter.write(exec);
//			printWriter.flush();
//			printWriter.close();
//		}
//		catch (IOException e) {
//			LOGGER.error(e.getMessage(), e);
////			e.printStackTrace();
//		}
//	}

	@RequestMapping("/test/upload")
	public String upload() {
		return "test-upload";
	}

	@RequestMapping("/test/socket")
	public String socket(@RequestParam(defaultValue = "test") String name, Model model, HttpServletRequest request) {
		model.addAttribute("name", name);
		// 向会话中写入信息
		HttpSession httpSession = request.getSession(true);
		httpSession.setAttribute(Constants.SESSION_USERNAME, name);
		model.addAttribute("JSESSIONID", httpSession.getId());
		return "test-websocket";
	}

	@RequestMapping("/test/socket2")
	public String socket2(@RequestParam(defaultValue = "test") String name, Model model, HttpServletRequest request) {
		model.addAttribute("name", name);
		// 向会话中写入信息
		HttpSession httpSession = request.getSession(true);
		httpSession.setAttribute(Constants.SESSION_USERNAME, name);
		model.addAttribute("JSESSIONID", httpSession.getId());
		return "test-websocket2";
	}

	@RequestMapping("/test/html2canvas")
	public String html2canvas() {
		return "html2canvas";
	}

	@RequestMapping("/test/html5canvas")
	public String html5canvas() {
		return "html5-canvas";
	}

	@GetMapping("/test/cron")
	public String cron() {
		return "cron";
	}

	@RequestMapping(value = "/ueditor/config", method = {RequestMethod.POST, RequestMethod.GET})
	public void config(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		String rootPath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/js/ueditor/jsp";
		try {
			response.setCharacterEncoding("UTF-8");
			String exec = new ActionEnter(request, rootPath).exec();
			LOGGER.debug("exec => " + exec);
			PrintWriter printWriter = response.getWriter();
			printWriter.write(exec);
			printWriter.flush();
			printWriter.close();
		}
		catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
//			e.printStackTrace();
		}
	}
}

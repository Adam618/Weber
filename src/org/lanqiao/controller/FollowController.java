package org.lanqiao.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.lanqiao.bean.RegisterUser;
import org.lanqiao.service.FollowService;
import org.lanqiao.service.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("prototype")
public class FollowController {
	FollowService followService;
	RegisterUserService registerUserService;

	public FollowService getFollowService() {
		return followService;
	}

	@Autowired
	public void setFollowService(FollowService followService) {
		this.followService = followService;
	}

	public RegisterUserService getRegisterUserService() {
		return registerUserService;
	}

	@Autowired
	public void setRegisterUserService(RegisterUserService registerUserService) {
		this.registerUserService = registerUserService;
	}

	// ��ע
	@RequestMapping("addFollowUser")
	@ResponseBody
	public String addFollowUser(HttpServletRequest request, HttpSession session) {
		RegisterUser registerUser = (RegisterUser) session
				.getAttribute("registerUser");
		if(registerUser==null){
			return "LoginRegisterUser";
		}
		int RUId = registerUser.getRUId();
		int EUId = Integer.parseInt(request.getParameter("EUId"));
		int flag = followService.addFollowUser(RUId, EUId);
		String home = request.getParameter("home");
		if (home != null && !home.equals("")) {
			return "redirect:selectMyFans.do";
		}
		if (flag != 0) {
			return "1";
		} else {
			return "0";
		}
	}

	// ȡ��
	@RequestMapping("deleteFollowUser")
	@ResponseBody
	public String deleteFollowUser(HttpServletRequest request, HttpSession session) {
		RegisterUser registerUser = (RegisterUser) session
				.getAttribute("registerUser");
		if(registerUser==null){
			return "LoginRegisterUser";
		}
		int EUId = Integer.parseInt(request.getParameter("EUId"));
		int flag = followService.deleteFollowUser(EUId);
		String home = request.getParameter("home");
		if (home != null && !home.equals("")) {
			return "redirect:selectFollowUser.do";
		}
		if (flag != 0) {
			return "1";
		} else {
			return "0";
		}
	}

	// ��ȡ�ҹ�ע����
	@RequestMapping("selectFollowUser")
	public String selectFollowUser(HttpServletRequest request,
			HttpSession session) {
		RegisterUser registerUser = (RegisterUser) session
				.getAttribute("registerUser");
		if(registerUser==null){
			return "LoginRegisterUser";
		}
		int ruid = registerUser.getRUId();
		// ���÷�ҳ
		int totalsize = 0; // ��¼������
		int totalpage = 1; // ��ҳ��
		int currentPage = 1; // ��ǰҳ��
		String page_now = request.getParameter("currentPage");
		if (page_now != null) {
			currentPage = Integer.parseInt(page_now);
		}
		totalsize = registerUserService.selectAllFollow(ruid).size();
		// ��ȡȫ���û�
		List<RegisterUser> list = registerUserService.selectAllFollow(ruid);
		List<RegisterUser> ilist = new ArrayList<RegisterUser>();
		// ׼����ҳ��һҳ������¼
		// ���ܼ�¼����С��5��ʱ��ֱ����ʾ�����ҳ
		if (list.size() < 5) {
			request.setAttribute("List", list);
		} else {
			// ���ܼ�¼��������5��ʱ�����ݵ�ǰҳ�ó�Ӧ��ʾ��������ʾ
			// ���Ǽ�¼����ҳ���Ĺ�ϵ����ֹ�±�Խ��
			if (currentPage * 5 > list.size()) {
				for (int i = (currentPage - 1) * 5; i < list.size(); i++) {
					ilist.add(list.get(i));
				}
				request.setAttribute("List", ilist);
			} else {
				for (int i = (currentPage - 1) * 5; i < currentPage * 5; i++) {
					ilist.add(list.get(i));
				}
				request.setAttribute("List", ilist);
			}
		}
		totalpage = (totalsize % 5 == 0) ? (totalsize / 5)
				: (totalsize / 5 + 1);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("totalsize", totalsize);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("listPage", 0);
		return "FollowUserList";
	}

	// ��ȡ��ע�ҵ���
	@RequestMapping("selectMyFans")
	public String selectMyFans(HttpServletRequest request, HttpSession session) {
		RegisterUser registerUser = (RegisterUser) session
				.getAttribute("registerUser");
		if(registerUser==null){
			return "LoginRegisterUser";
		}
		int ruid = registerUser.getRUId();
		// ���÷�ҳ
		int totalsize = 0; // ��¼������
		int totalpage = 1; // ��ҳ��
		int currentPage = 1; // ��ǰҳ��
		String page_now = request.getParameter("currentPage");
		if (page_now != null) {
			currentPage = Integer.parseInt(page_now);
		}
		totalsize = registerUserService.selectMyFans(ruid).size();
		// ��ȡȫ���û�
		List<RegisterUser> list = registerUserService.selectMyFans(ruid);
		List<RegisterUser> ilist = new ArrayList<RegisterUser>();
		// ׼����ҳ��һҳ������¼
		// ���ܼ�¼����С��5��ʱ��ֱ����ʾ�����ҳ
		if (list.size() < 5) {
			request.setAttribute("List", list);
		} else {
			// ���ܼ�¼��������5��ʱ�����ݵ�ǰҳ�ó�Ӧ��ʾ��������ʾ
			// ���Ǽ�¼����ҳ���Ĺ�ϵ����ֹ�±�Խ��
			if (currentPage * 5 > list.size()) {
				for (int i = (currentPage - 1) * 5; i < list.size(); i++) {
					ilist.add(list.get(i));
				}
				request.setAttribute("List", ilist);
			} else {
				for (int i = (currentPage - 1) * 5; i < currentPage * 5; i++) {
					ilist.add(list.get(i));
				}
				request.setAttribute("List", ilist);
			}
		}
		totalpage = (totalsize % 5 == 0) ? (totalsize / 5)
				: (totalsize / 5 + 1);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("totalsize", totalsize);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("listPage", 1);
		return "FollowUserList";
	}
}

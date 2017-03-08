package org.lanqiao.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.lanqiao.bean.Manager;
import org.lanqiao.bean.RegisterUser;
import org.lanqiao.service.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("prototype")
public class RegisterUserController {
	RegisterUserService registerUserService;

	public RegisterUserService getRegisterUserService() {
		return registerUserService;
	}

	@Autowired
	public void setRegisterUserService(RegisterUserService registerUserService) {
		this.registerUserService = registerUserService;
	}

	// ��ȡȫ���û�
	@RequestMapping("selectAllRegisterUser")
	public String selectAllRegisterUser(HttpServletRequest request,
			HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			return "ManagerLogin";
		}
		// ����ҳ������0��ʾΪ�����û�ҳ
		request.setAttribute("indexNum", 0);
		// ���÷�ҳ
		int totalsize = 0; // ��¼������
		int totalpage = 1; // ��ҳ��
		int currentPage = 1; // ��ǰҳ��
		String page_now = request.getParameter("currentPage");
		if (page_now != null) {
			currentPage = Integer.parseInt(page_now);
		}
		totalsize = registerUserService.selectAllRegisterUser().size();
		// ��ȡȫ���û�
		List<RegisterUser> list = registerUserService.selectAllRegisterUser();
		List<RegisterUser> rlist = new ArrayList<RegisterUser>();
		// ׼����ҳ��һҳ������¼
		// ���ܼ�¼����С��5��ʱ��ֱ����ʾ�����ҳ
		if (list.size() < 5) {
			request.setAttribute("List", list);
		} else {
			// ���ܼ�¼��������5��ʱ�����ݵ�ǰҳ�ó�Ӧ��ʾ��������ʾ
			// ���Ǽ�¼����ҳ���Ĺ�ϵ����ֹ�±�Խ��
			if (currentPage * 5 > list.size()) {
				for (int i = (currentPage - 1) * 5; i < list.size(); i++) {
					rlist.add(list.get(i));
				}
				request.setAttribute("List", rlist);
			} else {
				for (int i = (currentPage - 1) * 5; i < currentPage * 5; i++) {
					rlist.add(list.get(i));
				}
				request.setAttribute("List", rlist);
			}
		}
		totalpage = (totalsize % 5 == 0) ? (totalsize / 5)
				: (totalsize / 5 + 1);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("totalsize", totalsize);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("msg", request.getParameter("msg"));
		return "ManagerIndex";
	}

	// ɾ��ָ���û�
	@RequestMapping("deleteRegisterUser")
	public String deleteRegisterUser(HttpServletRequest request,
			HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			return "ManagerLogin";
		}
		int RUId = Integer.parseInt(request.getParameter("RUId"));
		RegisterUser registerUser = new RegisterUser(RUId);
		int flag = registerUserService.deleteRegisterUser(registerUser);
		if (flag != 0) {
			return "redirect:selectAllRegisterUser.do?msg=Delete Successed";
		} else {
			return "redirect:selectAllRegisterUser.do?msg=Delete Failed";
		}
	}

	// ����Ա��ѯָ���û�
	@RequestMapping("searchRegisterUser")
	public String searchRegisterUser(HttpServletRequest request,
			HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			return "ManagerLogin";
		}
		// ��ȡsession�еĲ�ѯ�ؼ���
		String session_search = (String) session.getAttribute("word");
		// ��ȡ�����Ĳ�ѯ�ؼ���
		String searchword = request.getParameter("searchword");
		// ������ʽƥ�����������жϲ�ѯ�ؼ����Ƿ�Ϊ����
		String regex = "^[0-9]*[1-9][0-9]*$";
		int num = 0;
		if (session_search != null) {
			if (searchword != null && !searchword.equals("")
					&& !session_search.equals(searchword)) {
				session.setAttribute("word", searchword);
				session_search = searchword;
			}
			searchword = session_search;
		} else {
			session.setAttribute("word", searchword);
		}
		// ��ҳ����
		int totalsize = 0;
		int totalpage = 1;
		int currentPage = 1;
		String page_now = request.getParameter("currentPage");
		if (page_now != null) {
			currentPage = Integer.parseInt(page_now);
		}
		// ���ݹؼ��ֲ�ѯ
		List<RegisterUser> list = new ArrayList<RegisterUser>();
		List<RegisterUser> rlist = new ArrayList<RegisterUser>();
		RegisterUser registerUser = new RegisterUser();
		if (searchword != null && !searchword.equals("")) {
			if (searchword.matches(regex)) {
				num = Integer.parseInt(searchword);
				registerUser.setRUId(num);
			} else {
				registerUser.setRUName(searchword);
			}
			list = registerUserService.searchRegisterUser(registerUser);
		}
		// ׼����ҳ��һҳ5��
		if (list.size() < 5) {
			request.setAttribute("List", list);
		} else {
			if (currentPage * 5 > list.size()) {
				for (int i = (currentPage - 1) * 5; i < list.size(); i++) {
					rlist.add(list.get(i));
				}
				request.setAttribute("List", rlist);
			} else {
				for (int i = (currentPage - 1) * 5; i < currentPage * 5; i++) {
					rlist.add(list.get(i));
				}
				request.setAttribute("List", rlist);
			}
		}
		totalsize = list.size();
		totalpage = (totalsize % 5 == 0) ? (totalsize / 5)
				: (totalsize / 5 + 1);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("totalsize", totalsize);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("indexNum", 0);
		return "ManagerSearchResult";
	}

	// �û���Ϣ�޸ķ���
	@RequestMapping("updatePersonalInformation")
	public String updatePersonalInformation(HttpServletRequest request,
			HttpSession session) {
		RegisterUser registerUser = (RegisterUser) session
				.getAttribute("registerUser");
		if (registerUser == null) {
			return "LoginRegisterUser";
		}
		// ��ȡ�û���������
		int rusex = Integer.parseInt(request.getParameter("user_sex"));
		String ruintroduction = request.getParameter("user_introduce");
		registerUser.setRUSex(rusex);
		registerUser.setRUIntroduction(ruintroduction);
		int flag = registerUserService.updatePersonalInformation(registerUser);
		if (flag != 0) {
			request.getSession().removeAttribute("registerUser");
			session.setAttribute("registerUser", registerUser);
			request.setAttribute("msg", "�޸ĳɹ�");
		} else {
			request.setAttribute("msg", "�޸�ʧ��");
		}
		return "UpdatePersonalInformation";
	}

	@RequestMapping("updatePassword")
	public String updatePassword(HttpServletRequest request, HttpSession session) {
		RegisterUser registerUser = (RegisterUser) session
				.getAttribute("registerUser");
		if (registerUser == null) {
			return "LoginRegisterUser";
		}
		// ��ȡ������
		String old_pwd = request.getParameter("old_pwd");
		// ��ȡ������
		String new_pwd = request.getParameter("new_pwd").trim();
		// �жϾ������Ƿ�һ��������ƥ��
		if (registerUser.getRUPassWord().equals(old_pwd)) {
			// �ж��������Ƿ�Ϊ�գ���Ϊ��ֱ���޸�
			if (new_pwd != null && !new_pwd.equals("")) {
				if (new_pwd.length() < 6) {
					request.setAttribute("pwd_error", "���볤�Ȳ�С��6λ");
				} else {
					registerUser.setRUPassWord(new_pwd);
					int flag = registerUserService.updatePassword(registerUser);
					if (flag != 0) {
						request.getSession().removeAttribute("registerUser");
						session.setAttribute("registerUser", registerUser);
						request.setAttribute("pwd_error", "�޸ĳɹ�");
					} else {
						request.setAttribute("pwd_error", "�޸�ʧ��");
					}
				}
			} else {
				request.setAttribute("pwd_error", "�����벻��Ϊ��");
			}
		} else {
			request.setAttribute("pwd_error", "ԭ�����������");
		}
		return "RegisterPasswordIndex";
	}

	// ���ע���û�
	@RequestMapping("addRegisterUser")
	@ResponseBody
	public String addRegisterUser(HttpServletRequest request) {
		String RUName = request.getParameter("RUName");
		String RUPassWord = request.getParameter("RUPassWord");
		RegisterUser registerUser = new RegisterUser();
		registerUser.setRUName(RUName);
		registerUser.setRUPassWord(RUPassWord);
		int flag = registerUserService.addRegisterUser(registerUser);
		if (flag != 0) {
			return "1";
		} else {
			return "0";
		}
	}

	// ע�᣺�����û�����ʾ���û��Ƿ����
	@RequestMapping("selectRegisterUserByRUName")
	@ResponseBody
	public String selectRegisterUserByRUName(RegisterUser registerUser,
			HttpServletResponse response) {
		int RUId = registerUserService.selectRegisterUserByRUName(registerUser);
		if (RUId == 0) {
			// �û�������
			return "1";
		} else {
			// �û�����
			return "0";
		}
	}

	// �û���¼
	@RequestMapping("loginRegisterUser")
	public String loginRegisterUser(HttpServletRequest request,
			HttpSession session) {
		// ��ȡ�û���������
		String rname = request.getParameter("rName");
		String rpassword = request.getParameter("rPassword");
		RegisterUser registerUser = new RegisterUser();
		RegisterUser loginRegister = new RegisterUser(rname, rpassword);
		// ��ȡSession�е��û���¼��Ϣ
		RegisterUser sessionRegisterUser = (RegisterUser) session
				.getAttribute("registerUser");
		if (sessionRegisterUser != null) {
			if (rname != null && !sessionRegisterUser.getRUName().equals(rname)) {
				sessionRegisterUser = registerUserService
						.loginRegisterUser(loginRegister);
			}
			registerUser = sessionRegisterUser;
		} else { // û��sessionʱ
			registerUser = registerUserService.loginRegisterUser(loginRegister);
		}

		if (registerUser != null) { // ���Ե�¼ʱ
			session.setAttribute("registerUser", registerUser);
			return "UserIndex";
		} else { // �����Ե�¼ʱ
			return "LoginRegisterUser";
		}
	}

	// �˳���¼�ķ���
	@RequestMapping("registerLoginOut")
	public String registerLoginOut(HttpServletRequest request) {
		// �Ƴ�Session�е��û���¼��Ϣ
		request.getSession().removeAttribute("registerUser");
		return "LoginRegisterUser";
	}
}

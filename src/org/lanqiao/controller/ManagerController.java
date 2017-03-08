package org.lanqiao.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.lanqiao.bean.Manager;
import org.lanqiao.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope("prototype")
public class ManagerController {
	ManagerService managerService;

	public ManagerService getManagerService() {
		return managerService;
	}

	@Autowired
	public void setManagerService(ManagerService managerService) {
		this.managerService = managerService;
	}

	// ����Ա��¼����
	@RequestMapping("managerLogin")
	public String managerLogin(HttpServletRequest request, HttpSession session) {
		// ��ȡ�û���������
		String name = request.getParameter("managerName");
		String pwd = request.getParameter("managerPassword");
		Manager manager = new Manager();
		Manager loginManager = new Manager(name, pwd);
		// ��ȡSession�еĹ���Ա��¼��Ϣ
		Manager sessionManager = (Manager) session.getAttribute("manager");
		if (sessionManager != null) {
			manager = sessionManager;
		} else {
			manager = managerService.managerLogin(loginManager);
		}
		if (manager != null) {
			session.setAttribute("manager", manager);
			// ��ȡҳ��ֵindexNum�����������û��������
			int indexNum = 0;
			String s_num = request.getParameter("indexNum");
			if (s_num != null && !s_num.equals("")) {
				indexNum = Integer.parseInt(s_num);
			}
			if (indexNum == 0) {
				return "redirect:selectAllRegisterUser.do";
			} else if (indexNum == 1) {
				return "redirect:selectAllKind.do";
			} else {
				return "redirect:selectAllInfo.do";
			}
		} else {
			request.setAttribute("msg", "�û������������");
			return "ManagerLogin";
		}
	}

	// �޸Ĺ���Ա��¼����
	@RequestMapping("updateManagerPwd")
	public String updateManagerPwd(HttpServletRequest request,
			HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			return "ManagerLogin";
		}
		// ��ȡ������
		String MPassword = request.getParameter("old_pwd");
		// ��ȡ������
		String MPassword1 = request.getParameter("new_pwd");
		// �жϾ������Ƿ�һ��������ƥ��
		if (MPassword.equals(manager.getMPassword())) {
			// �ж��������Ƿ�Ϊ�գ���Ϊ��ֱ���޸�
			if (MPassword1 != null && !MPassword1.equals("")) {
				manager.setMPassword(MPassword1);
				int flag = managerService.updateManagerPwd(manager);
				if (flag != 0) {
					return "redirect:managerLoginOut.do";
				} else {
					System.out.println("�޸�ʧ��");
					request.setAttribute("msg", "�޸�ʧ��");
				}
			} else {
				request.setAttribute("msg", "������Ϊ��");
			}
		} else {
			request.setAttribute("msg", "ԭ�����������");
		}
		return "ManagerPassword";
	}

	// ����Ա�Ĳ�ѯ��������
	@RequestMapping("managerSearch")
	public String managerSearch(HttpServletRequest request, HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			return "ManagerLogin";
		}
		int indexNum = Integer.parseInt(request.getParameter("indexNum"));
		String searchword = request.getParameter("searchword");
		if (indexNum == 0) {
			return "redirect:searchRegisterUser.do?searchword=" + searchword;
		} else if (indexNum == 1) {
			return "redirect:searchKind.do?searchword=" + searchword;
		} else {
			return "redirect:searchInfo.do?searchword=" + searchword;
		}
	}

	// �˳���¼�ķ���
	@RequestMapping("managerLoginOut")
	public String managerLoginOut(HttpServletRequest request) {
		// �Ƴ�Session�еĹ���Ա��¼��Ϣ
		request.getSession().removeAttribute("manager");
		return "ManagerLogin";
	}
}

package org.lanqiao.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.lanqiao.bean.Kind;
import org.lanqiao.bean.Manager;
import org.lanqiao.service.KindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope("prototype")
public class KindController {
	KindService kindService;

	public KindService getKindService() {
		return kindService;
	}

	@Autowired
	public void setKindService(KindService kindService) {
		this.kindService = kindService;
	}

	// ��ȡ���������Ϣ
	@RequestMapping("selectAllKind")
	public String selectAllKind(HttpServletRequest request, HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			return "ManagerLogin";
		}
		// ����ҳ������1��ʾΪ�������ҳ
		request.setAttribute("indexNum", 1);
		// ���÷�ҳ
		int totalsize = 0; // ��¼������
		int totalpage = 1; // ��ҳ��
		int currentPage = 1; // ��ǰҳ��
		String page_now = request.getParameter("currentPage");
		if (page_now != null) {
			currentPage = Integer.parseInt(page_now);
		}
		totalsize = kindService.selectAllKind().size();
		// ��ȡȫ���û�
		List<Kind> list = kindService.selectAllKind();
		List<Kind> klist = new ArrayList<Kind>();
		// ׼����ҳ��һҳ������¼
		// ���ܼ�¼����С��5��ʱ��ֱ����ʾ�����ҳ
		if (list.size() < 5) {
			request.setAttribute("List", list);
		} else {
			// ���ܼ�¼��������5��ʱ�����ݵ�ǰҳ�ó�Ӧ��ʾ��������ʾ
			// ���Ǽ�¼����ҳ���Ĺ�ϵ����ֹ�±�Խ��
			if (currentPage * 5 > list.size()) {
				for (int i = (currentPage - 1) * 5; i < list.size(); i++) {
					klist.add(list.get(i));
				}
				request.setAttribute("List", klist);
			} else {
				for (int i = (currentPage - 1) * 5; i < currentPage * 5; i++) {
					klist.add(list.get(i));
				}
				request.setAttribute("List", klist);
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

	// �������
	@RequestMapping("addKind")
	public String addKind(HttpServletRequest request, HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			return "ManagerLogin";
		}
		String kname = request.getParameter("kname");
		if (kname != null && !kname.equals("")) {
			Kind kind = new Kind(kname);
			int flag = kindService.addKind(kind);
			if (flag != 0) {
				request.setAttribute("msg", "��ӳɹ�");
			} else {
				request.setAttribute("msg", "���ʧ��");
			}
		} else {
			request.setAttribute("msg", "��������Ϊ��");
		}
		return "AddKind";
	}

	// ɾ��ָ�����
	@RequestMapping("deleteKind")
	public String deleteKind(HttpServletRequest request, HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			return "ManagerLogin";
		}
		int kid = Integer.parseInt(request.getParameter("KId"));
		Kind kind = new Kind(kid);
		int flag = kindService.deleteKind(kind);
		if (flag != 0) {
			return "redirect:selectAllKind.do?msg=Delete Successed";
		} else {
			return "redirect:selectAllKind.do?msg=Delete Failed";
		}
	}

	// �޸�ָ�����
	@SuppressWarnings("null")
	@RequestMapping("updateKind")
	public String updateKind(HttpServletRequest request, HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			return "ManagerLogin";
		}
		int kid = Integer.parseInt(request.getParameter("kid"));
		String kname = request.getParameter("kname");
		if (kname == null && kname.equals("")) {
			request.setAttribute("msg", "��������Ϊ��");
			return "UpdateKind";
		}
		Kind kind = new Kind(kid, kname);
		int flag = kindService.updateKind(kind);
		if (flag != 0) {
			request.setAttribute("msg", "�޸ĳɹ�");
		} else {
			request.setAttribute("msg", "�޸�ʧ��");
		}
		return "UpdateKind";
	}

	// ��ѯָ�����
	@RequestMapping("searchKind")
	public String searchKind(HttpServletRequest request, HttpSession session) {
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
		List<Kind> list = new ArrayList<Kind>();
		List<Kind> klist = new ArrayList<Kind>();
		Kind kind = new Kind();
		if (searchword != null && !searchword.equals("")) {
			if (searchword.matches(regex)) {
				num = Integer.parseInt(searchword);
				kind.setKId(num);
			} else {
				kind.setKName(searchword);
			}
			list = kindService.searchKind(kind);
		}
		// ׼����ҳ��һҳ5��
		if (list.size() < 5) {
			request.setAttribute("List", list);
		} else {
			if (currentPage * 5 > list.size()) {
				for (int i = (currentPage - 1) * 5; i < list.size(); i++) {
					klist.add(list.get(i));
				}
				request.setAttribute("List", klist);
			} else {
				for (int i = (currentPage - 1) * 5; i < currentPage * 5; i++) {
					klist.add(list.get(i));
				}
				request.setAttribute("List", klist);
			}
		}
		totalsize = list.size();
		totalpage = (totalsize % 5 == 0) ? (totalsize / 5)
				: (totalsize / 5 + 1);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("totalsize", totalsize);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("indexNum", 1);
		return "ManagerSearchResult";
	}
}

package org.lanqiao.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.lanqiao.bean.CollectionInfo;
import org.lanqiao.bean.Information;
import org.lanqiao.bean.RegisterUser;
import org.lanqiao.service.CollectionInfoService;
import org.lanqiao.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("prototype")
public class CollectionInfoController {
	CollectionInfoService collectionInfoService;

	public CollectionInfoService getCollectionInfoService() {
		return collectionInfoService;
	}

	@Autowired
	public void setCollectionInfoService(
			CollectionInfoService collectionInfoService) {
		this.collectionInfoService = collectionInfoService;
	}

	InformationService informationService;

	public InformationService getInformationService() {
		return informationService;
	}

	@Autowired
	public void setInformationService(InformationService informationService) {
		this.informationService = informationService;
	}

	// �ղ�
	@RequestMapping("addCollectionInformation")
	@ResponseBody
	public String addCollectionInformation(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		RegisterUser registerUser = (RegisterUser) session
				.getAttribute("registerUser");
		if (registerUser == null) {
			return "LoginRegisterUser";
		}
		int RUId = registerUser.getRUId();
		int IId = Integer.parseInt(request.getParameter("IId"));
		int flag = collectionInfoService.addCollectionInformation(RUId, IId);
		if (flag != 0) {
			return "1";
		} else {
			return "0";
		}
	}

	// ȡ���ղ�
	@RequestMapping("deleteCollectionInformation")
	public String deleteCollectionInformation(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		RegisterUser registerUser = (RegisterUser) session
				.getAttribute("registerUser");
		if (registerUser == null) {
			return "LoginRegisterUser";
		}
		int IId = Integer.parseInt(request.getParameter("IId"));
		CollectionInfo collectionInfo = new CollectionInfo(registerUser.getRUId(),IId);
		collectionInfoService.deleteCollectionInformation(collectionInfo);
		return "redirect:selectInfoByCollect.do";
	}

	// ��ѯ����Ϣ�Ƿ��Ѿ����ղ�
	@RequestMapping("selectCollectionInfoById")
	@ResponseBody
	public String selectCollectionInfoById(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		RegisterUser registerUser = (RegisterUser) session
				.getAttribute("registerUser");
		if (registerUser == null) {
			return "LoginRegisterUser";
		}
		int ruid = registerUser.getRUId();
		int IId = Integer.parseInt(request.getParameter("IId"));
		CollectionInfo collectionInfo = collectionInfoService
				.selectCollectionInfoById(new CollectionInfo(ruid, IId));
		if (collectionInfo == null) {
			String id = String.valueOf(IId);
			return id;
		} else {
			return "0";
		}
	}

	// ��ʾ�û��ղ��б�
	@RequestMapping("selectInfoByCollect")
	public String selectInfoByCollect(HttpServletRequest request,
			HttpSession session) {
		RegisterUser registerUser = (RegisterUser) session
				.getAttribute("registerUser");
		if (registerUser == null) {
			return "LoginRegisterUser";
		}
		// ���÷�ҳ
		int totalsize = 0; // ��¼������
		int totalpage = 1; // ��ҳ��
		int currentPage = 1; // ��ǰҳ��
		String page_now = request.getParameter("currentPage");
		if (page_now != null) {
			currentPage = Integer.parseInt(page_now);
		}
		totalsize = informationService.selectInfoByCollect(
				registerUser.getRUId()).size();
		// ��ȡȫ���û�
		List<Information> list = informationService
				.selectInfoByCollect(registerUser.getRUId());
		List<Information> ilist = new ArrayList<Information>();
		// ׼����ҳ��һҳ������¼
		// ���ܼ�¼����С��5��ʱ��ֱ����ʾ�����ҳ
		if (list.size() < 5) {
			request.setAttribute("InfoList", list);
		} else {
			// ���ܼ�¼��������5��ʱ�����ݵ�ǰҳ�ó�Ӧ��ʾ��������ʾ
			// ���Ǽ�¼����ҳ���Ĺ�ϵ����ֹ�±�Խ��
			if (currentPage * 5 > list.size()) {
				for (int i = (currentPage - 1) * 5; i < list.size(); i++) {
					ilist.add(list.get(i));
				}
				request.setAttribute("InfoList", ilist);
			} else {
				for (int i = (currentPage - 1) * 5; i < currentPage * 5; i++) {
					ilist.add(list.get(i));
				}
				request.setAttribute("InfoList", ilist);
			}
		}
		totalpage = (totalsize % 5 == 0) ? (totalsize / 5)
				: (totalsize / 5 + 1);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("totalsize", totalsize);
		request.setAttribute("totalpage", totalpage);
		return "CollectionIndex";
	}
}

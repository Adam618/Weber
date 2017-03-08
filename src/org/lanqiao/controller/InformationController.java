package org.lanqiao.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.lanqiao.bean.Follow;
import org.lanqiao.bean.GoodAndBad;
import org.lanqiao.bean.Information;
import org.lanqiao.bean.Kind;
import org.lanqiao.bean.Manager;
import org.lanqiao.bean.RegisterUser;
import org.lanqiao.service.FollowService;
import org.lanqiao.service.GoodAndBadService;
import org.lanqiao.service.InformationService;
import org.lanqiao.service.KindService;
import org.lanqiao.service.RegisterUserService;
import org.lanqiao.util.ImgCompress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("prototype")
public class InformationController {
	InformationService informationService;

	public InformationService getInformationService() {
		return informationService;
	}

	// �Զ����
	@Autowired
	public void setInformationService(InformationService informationService) {
		this.informationService = informationService;
	}

	private KindService kindService;

	KindService getKindService() {
		return kindService;
	}

	@Autowired
	public void setKindService(KindService kindService) {
		this.kindService = kindService;
	}

	RegisterUserService registerUserService;

	public RegisterUserService getRegisterUserService() {
		return registerUserService;
	}

	@Autowired
	public void setRegisterUserService(RegisterUserService registerUserService) {
		this.registerUserService = registerUserService;
	}

	FollowService followService;

	public FollowService getFollowService() {
		return followService;
	}

	@Autowired
	public void setFollowService(FollowService followService) {
		this.followService = followService;
	}

	GoodAndBadService goodAndBadService;

	public GoodAndBadService getGoodAndBadService() {
		return goodAndBadService;
	}

	@Autowired
	public void setGoodAndBadService(GoodAndBadService goodAndBadService) {
		this.goodAndBadService = goodAndBadService;
	}

	// ������Ϣ
	@SuppressWarnings("unchecked")
	@RequestMapping("addpublishInformation")
	public String addpublishInformation(HttpServletRequest request,
			HttpSession session) {
		RegisterUser registerUser = (RegisterUser) session
				.getAttribute("registerUser");
		if (registerUser == null) {
			return "LoginRegisterUser";
		}
		String IContent = "";
		String kid = "";
		String impic = "";
		String savePath = request.getServletContext().getRealPath("upload");
		File file = new File(savePath);
		// �ж��ϴ��ļ��ı���Ŀ¼�Ƿ����
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		DiskFileItemFactory dfi = new DiskFileItemFactory();
		// �����ļ���С����20*1024*1024��д��disk��
		dfi.setSizeThreshold(1024 * 1024 * 5);
		// ���ô洢�Ĳֿ�
		dfi.setRepository(new File(savePath));
		// ʵ����һ��servletFileUpload����
		ServletFileUpload sfu = new ServletFileUpload(dfi);
		// ����ϴ��ļ���������
		sfu.setHeaderEncoding("utf-8");
		// �����ļ�����ֽ���
		sfu.setSizeMax(1024 * 1024 * 5);
		// ����涨�ϴ��ļ�������
		String[] arr = { ".jpg", ".png", ".bmp" };
		// �����ͷŵ�List��
		List<String> fileStandType = Arrays.asList(arr);
		// ��������н���,�м���������ͻ��м���FileItem����
		List<FileItem> items;
		try {
			items = sfu.parseRequest(request);
			for (FileItem item : items) {
				// �ж�����Ԫ�ص����ͣ�
				if (item.isFormField()) {
					// ��ͨ������
					if ("IContent".equals(item.getFieldName())) {
						IContent = item.getString("UTF-8").trim();
					}
					if ("KId".equals(item.getFieldName())) {
						kid = item.getString();
					}
				} else {
					// ���ϴ��ļ�������
					// ��ȡ�ϴ��ļ�����
					String fileName = item.getName();
					// �ж�fileName�Ƿ�Ϊ�ռ��Ƿ����ѡ�����ϴ��ļ�,��Ϊ�ռ���
					if (!fileName.trim().equals("")) {
						// ���ļ������д���õ��ļ���
						fileName = fileName.substring(fileName
								.lastIndexOf("\\") + 1);
						// �õ��ļ���׺�ж��ļ�����
						String fileType = fileName.substring(fileName
								.lastIndexOf("."));
						// �ж��Ƿ����ƶ����ļ�����
						if (!fileStandType.contains(fileType)) {
							// ��������ƶ����͵��ļ���תҳ��
							return "redirect:selectInfoForRegister.do?msg=Filetype Not Match";
						}
						long size = item.getSize();
						if (size > (5 * 1024 * 1024)) {
							// ��������ƶ����͵��ļ���תҳ��
							return "redirect:selectInfoForRegister.do?msg=File Size Must Be Less than 5M";
						}
						// ���ͼ
						FileOutputStream fos;
						StringBuffer buffer = new StringBuffer();
						// ��Сͼ
						StringBuffer buffer2 = new StringBuffer();
						String name = "" + System.currentTimeMillis();
						String path = savePath
								+ "\\"
								+ name
								+ fileName.subSequence(fileName.indexOf("."),
										fileName.length());
						impic = name + ".png";
						fos = new FileOutputStream(path);
						if (item.isInMemory()) {
							fos.write(item.get());
							fos.close();
						} else {
							InputStream is = item.getInputStream();
							byte[] bytes = new byte[1024];
							int len;
							while ((len = is.read(bytes)) > 1) {
								fos.write(bytes, 0, len);
							}
							is.close();
							fos.close();
						}
						buffer.append(savePath).append("\\B").append(impic);
						buffer2.append(savePath).append("\\S").append(impic);
						ImgCompress imgCompress = new ImgCompress(path);
						if (imgCompress.getWidth() <= 300) {
							if (imgCompress.getHeight() > 500) {
								imgCompress.resizeFix(imgCompress.getWidth(),
										500, buffer.toString());
								imgCompress.resizeFix(imgCompress.getWidth(),
										300, buffer2.toString());
							} else {
								imgCompress.resizeFix(imgCompress.getWidth(),
										imgCompress.getHeight(),
										buffer.toString());
								imgCompress.resizeFix(imgCompress.getWidth(),
										imgCompress.getHeight(),
										buffer2.toString());
							}
						} else if (imgCompress.getWidth() > 300
								&& imgCompress.getWidth() <= 750) {
							if (imgCompress.getHeight() > 500) {
								imgCompress.resizeFix(imgCompress.getWidth(),
										500, buffer.toString());
								imgCompress.resizeFix(imgCompress.getWidth(),
										300, buffer2.toString());
							} else {
								imgCompress.resizeFix(imgCompress.getWidth(),
										imgCompress.getHeight(),
										buffer.toString());
								imgCompress.resizeFix(imgCompress.getWidth(),
										imgCompress.getHeight() / 2,
										buffer2.toString());
							}
						} else {
							if (imgCompress.getHeight() > 500) {
								imgCompress.resizeFix(imgCompress.getWidth(),
										400, buffer.toString());
								imgCompress.resizeFix(imgCompress.getWidth(),
										300, buffer2.toString());
							} else {
								imgCompress.resizeFix(
										imgCompress.getHeight() / 2,
										imgCompress.getHeight() / 2,
										buffer.toString());
								imgCompress.resizeFix(
										imgCompress.getHeight() / 4,
										imgCompress.getHeight() / 4,
										buffer2.toString());
							}
						}
						File file2 = new File(path);
						file2.delete();// ɾ����ʱԭͼ
						item.delete();// �ڹر���֮��ɾ����ʱ�����ļ�
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int RUId = registerUser.getRUId();
		Date date = new Date();
		if (IContent != null && !IContent.equals("")) {
			Information information = new Information();
			information.setIContent(IContent);
			information.setRUId(RUId);
			if (kid != null && !kid.equals("")) {
				information.setKId(Integer.parseInt(kid));
			}
			information.setIPicpath(impic);
			information.setITime(date);
			int flag = informationService.addpublishInformation(information);
			if (flag != 0) {
				return "redirect:selectInfoForRegister.do?msg=publish successed";
			} else {
				return "redirect:selectInfoForRegister.do?msg=publish failed";
			}
		} else {
			return "redirect:selectInfoForRegister.do?msg=content is null";
		}
	}

	// ɾ��ָ����Ϣ
	@RequestMapping("deleteInfo")
	public String deleteInfo(HttpServletRequest request, HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			return "ManagerLogin";
		}
		// ����ID����ɾ������ȡID
		int IID = Integer.parseInt(request.getParameter("IId"));
		Information information = new Information(IID);
		int flag = informationService.deleteInfo(information);
		// ������һ��flag����ȡӰ���������ж�����Ϊ����ɾ���ɹ�������ʧ��
		if (flag != 0) {
			return "redirect:selectAllInfo.do?msg=Delete Successed";
		} else {
			return "redirect:selectAllInfo.do?msg=Delete Failed";
		}
	}

	// ����ID������Ϣ
	@RequestMapping("searchInfo")
	public String searchInfo(HttpServletRequest request, HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			return "ManagerLogin";
		}
		// ��ȡsession�еĲ�ѯ�ؼ���
		String session_search = (String) session.getAttribute("word");
		// ��ȡ�����Ĳ�ѯ�ؼ���
		String searchInfo = request.getParameter("searchword");
		// ������ʽƥ�����������жϲ�ѯ�ؼ����Ƿ�Ϊ����
		String regex = "^[0-9]*[1-9][0-9]*$";
		int num = 0;
		if (session_search != null) {
			if (searchInfo != null && !searchInfo.equals("")
					&& !session_search.equals(searchInfo)) {
				session.setAttribute("word", searchInfo);
				session_search = searchInfo;
			}
			searchInfo = session_search;
		} else {
			session.setAttribute("word", searchInfo);
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
		List<Information> list = new ArrayList<Information>();
		List<Information> Ilist = new ArrayList<Information>();
		Information Info = new Information();
		if (searchInfo != null && !searchInfo.equals("")) {
			if (searchInfo.matches(regex)) {
				num = Integer.parseInt(searchInfo);
				Info.setIId(num);
			} else {
				Info.setIContent(searchInfo);
			}
			list = informationService.searchInfo(Info);
		}
		// ׼����ҳ��һҳ5��
		if (list.size() < 5) {
			request.setAttribute("List", list);
		} else {
			if (currentPage * 5 > list.size()) {
				for (int i = (currentPage - 1) * 5; i < list.size(); i++) {
					Ilist.add(list.get(i));
				}
				request.setAttribute("List", Ilist);
			} else {
				for (int i = (currentPage - 1) * 5; i < currentPage * 5; i++) {
					Ilist.add(list.get(i));
				}
				request.setAttribute("List", Ilist);
			}
		}
		totalsize = list.size();
		totalpage = (totalsize % 5 == 0) ? (totalsize / 5)
				: (totalsize / 5 + 1);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("totalsize", totalsize);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("indexNum", 2);
		return "ManagerSearchResult";
	}

	// ��ȡȫ��������Ϣ
	@RequestMapping("selectAllInfo")
	public String selectAllInfo(HttpServletRequest request, HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			return "ManagerLogin";
		}
		// ����ҳ������1��ʾΪ�������ҳ
		request.setAttribute("indexNum", 2);
		// ���÷�ҳ
		int totalsize = 0; // ��¼������
		int totalpage = 1; // ��ҳ��
		int currentPage = 1; // ��ǰҳ��
		String page_now = request.getParameter("currentPage");
		if (page_now != null) {
			currentPage = Integer.parseInt(page_now);
		}
		totalsize = informationService.selectAllInfo().size();
		// ��ȡȫ���û�
		List<Information> list = informationService.selectAllInfo();
		List<Information> ilist = new ArrayList<Information>();
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
		request.setAttribute("msg", request.getParameter("msg"));
		return "ManagerIndex";
	}

	// ɾ����������Ϣ
	@RequestMapping("deleteInformation")
	public String deleteInformation(HttpServletRequest request,
			HttpSession session) {
		RegisterUser registerUser = (RegisterUser) session
				.getAttribute("registerUser");
		if (registerUser == null) {
			return "LoginRegisterUser";
		}
		String path = request.getServletContext().getRealPath("upload");
		String path1 = path + "\\B" + request.getParameter("path");
		String path2 = path + "\\S" + request.getParameter("path");
		File file = new File(path1);
		File file2 = new File(path2);
		if (file.exists()) {
			file.delete();
		}
		if (file2.exists()) {
			file2.delete();
		}
		int iid = Integer.parseInt(request.getParameter("IId"));
		Information information = new Information(iid);
		informationService.deleteInfo(information);
		return "redirect:selectInfoForSelf.do";
	}

	// ���Ź㳡��Ϣչʾ
	@RequestMapping("selectInfoForUser")
	public String selectInfoForUser(HttpServletRequest request) {
		// 0��ʾ������ͨ��Ϣ
		int pagingMode = 0;
		// ���÷�ҳ
		int totalsize = 0; // ��¼������
		int totalpage = 1; // ��ҳ��
		int currentPage = 1; // ��ǰҳ��
		String page_now = request.getParameter("currentPage");
		if (page_now != null) {
			currentPage = Integer.parseInt(page_now);
		}
		totalsize = informationService.selectInfoForUser().size();
		List<Information> ilist = new ArrayList<Information>();
		// ��ȡȫ��������Ϣ
		List<Information> list = informationService.selectInfoForUser();
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
		request.setAttribute("pagingMode", pagingMode);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("totalsize", totalsize);
		request.setAttribute("totalpage", totalpage);
		return "DisplayInformation";
	}

	// ���Ź㳡����չʾ
	@RequestMapping("selectKindForUser")
	public String selectKindForUser(HttpServletRequest request,
			HttpSession session) {
		// ��ѯ���б�ǩ
		List<Kind> kind = new ArrayList<Kind>();
		kind = kindService.selectAllKind();
		request.setAttribute("KindList", kind);
		return "KindPage";
	}

	// ���ݷ���������Ϣ
	@RequestMapping("selectInfoByKind")
	public String selectInfoByKind(HttpServletRequest request) {
		// 1��ʾ���Ǹ��ݷ���������Ϣ
		int pagingMode = 1;
		// ���÷�ҳ
		int totalsize = 0; // ��¼������
		int totalpage = 1; // ��ҳ��
		int currentPage = 1; // ��ǰҳ��
		String page_now = request.getParameter("currentPage");
		if (page_now != null) {
			currentPage = Integer.parseInt(page_now);
		}
		Information information = new Information(Integer.parseInt(request
				.getParameter("KId")));
		totalsize = informationService.selectInfoByKind(information).size();
		List<Information> ilist = new ArrayList<Information>();
		// ��ȡȫ��������Ϣ
		List<Information> list = informationService
				.selectInfoByKind(information);
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
		request.setAttribute("pagingMode", pagingMode);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("totalsize", totalsize);
		request.setAttribute("totalpage", totalpage);
		return "DisplayInformation";
	}

	// Ϊ�û���ѯ������Ϣ
	@RequestMapping("searchInfoForUser")
	public String searchInfoForUser(HttpServletRequest request,
			HttpSession session) {
		// 2��ʾ���Ǹ����û�������ѯ������Ϣ
		int pagingMode = 2;
		// ��ȡsession�еĲ�ѯ�ؼ���
		String session_search = (String) session.getAttribute("keyword");
		// ��ȡ�����Ĳ�ѯ�ؼ���
		String searchInfo = request.getParameter("word");
		if (session_search != null) {
			if (searchInfo != null && !searchInfo.equals("")
					&& !session_search.equals(searchInfo)) {
				session.setAttribute("keyword", searchInfo);
				session_search = searchInfo;
			}
			searchInfo = session_search;
		} else {
			session.setAttribute("keyword", searchInfo);
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
		List<Information> list = new ArrayList<Information>();
		List<Information> Ilist = new ArrayList<Information>();
		Information info = new Information();
		if (searchInfo != null && !searchInfo.equals("")) {
			info.setRUName(searchInfo.trim());
			info.setIContent(searchInfo.trim());
			list = informationService.searchInfoForUser(info);
		}
		// ׼����ҳ��һҳ5��
		if (list.size() < 5) {
			request.setAttribute("InfoList", list);
		} else {
			if (currentPage * 5 > list.size()) {
				for (int i = (currentPage - 1) * 5; i < list.size(); i++) {
					Ilist.add(list.get(i));
				}
				request.setAttribute("InfoList", Ilist);
			} else {
				for (int i = (currentPage - 1) * 5; i < currentPage * 5; i++) {
					Ilist.add(list.get(i));
				}
				request.setAttribute("InfoList", Ilist);
			}
		}
		totalsize = list.size();
		totalpage = (totalsize % 5 == 0) ? (totalsize / 5)
				: (totalsize / 5 + 1);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("totalsize", totalsize);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("pagingMode", pagingMode);
		return "DisplayInformation";
	}

	// �û���ҳ��Ϣչʾ
	@RequestMapping("selectInfoForRegister")
	public String selectInfoForRegister(HttpServletRequest request,
			HttpSession session) {
		// ��ȡSession�е��û���¼��Ϣ
		RegisterUser registerUser = (RegisterUser) session
				.getAttribute("registerUser");
		if (registerUser == null) {
			return "LoginRegisterUser";
		}
		// 3��ʾ�����û���ط�����Ϣ
		int pagingMode = 3;
		// ���÷�ҳ
		int totalsize = 0; // ��¼������
		int totalpage = 1; // ��ҳ��
		int currentPage = 1; // ��ǰҳ��
		String page_now = request.getParameter("currentPage");
		if (page_now != null) {
			currentPage = Integer.parseInt(page_now);
		}
		Information information = new Information(registerUser.getRUId());
		totalsize = informationService.selectInfoForRegister(information)
				.size();
		List<Information> ilist = new ArrayList<Information>();
		// ��ȡȫ��������Ϣ
		List<Information> list = informationService
				.selectInfoForRegister(information);
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
		// ��ѯ���б�ǩ
		List<Kind> kind = new ArrayList<Kind>();
		kind = kindService.selectAllKind();
		totalpage = (totalsize % 5 == 0) ? (totalsize / 5)
				: (totalsize / 5 + 1);
		request.setAttribute("kind", kind);
		request.setAttribute("pagingMode", pagingMode);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("totalsize", totalsize);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("msg", request.getParameter("msg"));
		return "DisplayInformation";
	}

	// ����
	@RequestMapping("addGood")
	@ResponseBody
	public String addGood(HttpServletRequest request, HttpSession session) {
		// ��ȡSession�е��û���¼��Ϣ
		RegisterUser registerUser = (RegisterUser) session
				.getAttribute("registerUser");
		if (registerUser == null) {
			return "LoginRegisterUser";
		}
		int iid = Integer.parseInt(request.getParameter("IId"));
		Information information = new Information(iid);
		int flag = informationService.addGood(information);
		if (flag != 0) {
			int flag2 = goodAndBadService.addGBInfo(new GoodAndBad(registerUser
					.getRUId(), iid));
			if (flag2 != 0) {
				return request.getParameter("IId");
			} else {
				return "0";
			}
		} else {
			return "0";
		}
	}

	// ��
	@RequestMapping("addBad")
	@ResponseBody
	public String addBad(HttpServletRequest request, HttpSession session) {
		// ��ȡSession�е��û���¼��Ϣ
		RegisterUser registerUser = (RegisterUser) session
				.getAttribute("registerUser");
		if (registerUser == null) {
			return "LoginRegisterUser";
		}
		int iid = Integer.parseInt(request.getParameter("IId"));
		Information information = new Information(iid);
		int flag = informationService.addBad(information);
		if (flag != 0) {
			int flag2 = goodAndBadService.addGBInfo(new GoodAndBad(registerUser
					.getRUId(), iid));
			if (flag2 != 0) {
				return request.getParameter("IId");
			} else {
				return "0";
			}
		} else {
			return "0";
		}
	}

	// ͨ���û�Id��ȡ��Ϣ
	@RequestMapping("selectInfoById")
	public String selectInfoById(HttpServletRequest request, HttpSession session) {
		String ruid = request.getParameter("RUId");
		// ��ȡsession�еĲ�ѯ�ؼ���
		String Rid = (String) session.getAttribute("ruid");
		if (Rid != null) {
			if (ruid != null && !ruid.equals("") && !Rid.equals(ruid)) {
				session.setAttribute("ruid", ruid);
				Rid = ruid;
			}
			ruid = Rid;
		} else {
			session.setAttribute("ruid", ruid);
		}
		int RUid = Integer.parseInt(ruid);
		Information information = new Information(RUid);
		RegisterUser registerUser = registerUserService
				.selectOneRegisterUser(RUid);
		// ��ȡSession�е��û���¼��Ϣ
		RegisterUser user = (RegisterUser) session.getAttribute("registerUser");
		if (user == null) {
			request.setAttribute("msg", "�����ע");
		} else {
			Follow follow = followService.selectFollowById(new Follow(user
					.getRUId(), RUid));
			if (follow != null) {
				request.setAttribute("msg", "ȡ����ע");
			} else {
				request.setAttribute("msg", "�����ע");
			}
		}
		// ���÷�ҳ
		int totalsize = 0; // ��¼������
		int totalpage = 1; // ��ҳ��
		int currentPage = 1; // ��ǰҳ��
		String page_now = request.getParameter("currentPage");
		if (page_now != null) {
			currentPage = Integer.parseInt(page_now);
		}
		totalsize = informationService.selectInfoById(information).size();
		List<Information> ilist = new ArrayList<Information>();
		// ��ȡȫ��������Ϣ
		List<Information> list = informationService.selectInfoById(information);
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
		request.setAttribute("user", registerUser);
		return "PersonalIndex";
	}

	// ��ѯ��¼�û����˷�����Ϣ
	@RequestMapping("selectInfoForSelf")
	public String selectInfoForSelf(HttpServletRequest request,
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
		Information information = new Information(registerUser.getRUId());
		totalsize = informationService.selectInfoForSelf(information).size();
		List<Information> ilist = new ArrayList<Information>();
		// ��ȡȫ��������Ϣ
		List<Information> list = informationService
				.selectInfoForSelf(information);
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
		return "RegisterInformation";
	}

}

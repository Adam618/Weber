package org.lanqiao.service;

import java.util.List;

import org.lanqiao.bean.UserView;

public interface ViewService {
	public int addView(UserView userView);// �������
	
	// ��ѯ����
	public List<UserView> selectViewById(int IId);
	
	// ɾ������
	public int deleteView(int VId);
}

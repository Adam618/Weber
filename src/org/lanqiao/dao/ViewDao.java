package org.lanqiao.dao;

import java.util.List;

import org.lanqiao.bean.UserView;

public interface ViewDao {
	public int addView(UserView userView); // �������
	
	// ��ѯ����
	public List<UserView> selectViewById(int IId);

	// ɾ������
	public int deleteView(int VId);
}

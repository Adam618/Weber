package org.lanqiao.dao;

import org.lanqiao.bean.Manager;

public interface ManagerDao {
	//����Ա��¼����
	public Manager managerLogin(Manager manager);
	//�޸Ĺ���Ա����
	public int updateManagerPwd(Manager manger);
}

package org.lanqiao.service;

import org.lanqiao.bean.Manager;

public interface ManagerService {
	// ����Ա��¼����
	public Manager managerLogin(Manager manager);
	// �޸Ĺ���Ա����
	public int updateManagerPwd(Manager manger);
}

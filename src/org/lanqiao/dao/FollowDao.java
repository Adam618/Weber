package org.lanqiao.dao;

import org.lanqiao.bean.Follow;

public interface FollowDao {
	//��ע
	public int addFollowUser(int RUId,int EUId);
	//ȡ��
	public int deleteFollowUser(int EUId);
	//��ȡ��ע��Ϣ
	public Follow selectFollowById(Follow follow);
}

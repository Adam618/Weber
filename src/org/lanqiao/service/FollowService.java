package org.lanqiao.service;

import org.lanqiao.bean.Follow;

public interface FollowService {
	// ��ע
	public int addFollowUser(int RUId, int EUId);

	// ȡ��
	public int deleteFollowUser(int EUId);

	// ��ȡ��ע��Ϣ
	public Follow selectFollowById(Follow follow);
}

package org.lanqiao.dao;

import org.lanqiao.bean.GoodAndBad;

public interface GoodAndBadDao {
	// ��ѯ�Ƿ����޻��
	public GoodAndBad selectGBInfoById(GoodAndBad goodAndBad);

	// ����û��Ȼ��޵���Ϣ
	public int addGBInfo(GoodAndBad goodAndBad);
}

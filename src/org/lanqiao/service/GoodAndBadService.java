package org.lanqiao.service;

import org.lanqiao.bean.GoodAndBad;

public interface GoodAndBadService {
	// ��ѯ�Ƿ����޻��
	public GoodAndBad selectGBInfoById(GoodAndBad goodAndBad);

	// ����û��Ȼ��޵���Ϣ
	public int addGBInfo(GoodAndBad goodAndBad);
}

package org.lanqiao.service;

import org.lanqiao.bean.CollectionInfo;

public interface CollectionInfoService {
	//�ղ�
	public int addCollectionInformation(int RUId,int IId);
	//ȡ���ղ�
	public int deleteCollectionInformation(CollectionInfo collectionInfo);
	// ͨ���û�Id����ϢId��ѯ�ղ���Ϣ
	public CollectionInfo selectCollectionInfoById(CollectionInfo collectionInfo);
}

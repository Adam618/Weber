package org.lanqiao.dao;

import org.lanqiao.bean.CollectionInfo;

public interface CollectionInfoDao {
	// �ղ�
	public int addCollectionInformation(int RUId, int IId);

	// ȡ���ղ�
	public int deleteCollectionInformation(CollectionInfo collectionInfo);

	// ͨ���û�Id����ϢId��ѯ�ղ���Ϣ
	public CollectionInfo selectCollectionInfoById(CollectionInfo collectionInfo);
}

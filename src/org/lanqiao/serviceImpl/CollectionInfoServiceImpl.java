package org.lanqiao.serviceImpl;

import org.lanqiao.bean.CollectionInfo;
import org.lanqiao.dao.CollectionInfoDao;
import org.lanqiao.service.CollectionInfoService;
import org.springframework.beans.factory.annotation.Autowired;

public class CollectionInfoServiceImpl implements CollectionInfoService {
	CollectionInfoDao collectionInfoDao;
	
	public CollectionInfoDao getCollectionInfoDao() {
		return collectionInfoDao;
	}
	@Autowired
	public void setCollectionInfoDao(CollectionInfoDao collectionInfoDao) {
		this.collectionInfoDao = collectionInfoDao;
	}
	//�ղ�
	@Override
	public int addCollectionInformation(int RUId,int IId){
		return collectionInfoDao.addCollectionInformation(RUId, IId);
	}
	//ȡ���ղ�
	@Override
	public int deleteCollectionInformation(CollectionInfo collectionInfo){
		return collectionInfoDao.deleteCollectionInformation(collectionInfo);
	}
	@Override
	public CollectionInfo selectCollectionInfoById(CollectionInfo collectionInfo) {
		return collectionInfoDao.selectCollectionInfoById(collectionInfo);
	}
}

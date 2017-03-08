package org.lanqiao.service;

import java.util.List;

import org.lanqiao.bean.Information;

public interface InformationService {
	// ������Ϣ
	public int addpublishInformation(Information information);

	// ɾ��ָ����Ϣ
	public int deleteInfo(Information information);

	// ����ָ����Ϣ
	public List<Information> searchInfo(Information information);

	// ��ȡȫ��������Ϣ
	public List<Information> selectAllInfo();

	// ��ȡ���˷�������Ϣ�͹�ע�˵���Ϣ
	public List<Information> selectInfoById(Information information);

	// ɾ����������Ϣ
	public int deletepublishInformation(int IId);

	// ��ȡ���˷�����Ϣ
	public List<Information> selectOneInformation(int RUId);

	// ��ȡ��Ϣչʾ���ο�
	public List<Information> selectInfoForUser();

	// ��������ȡ��Ϣչʾ���û�
	public List<Information> selectInfoForRegister(Information information);

	// ��ȡ���˷�����Ϣչʾ���û�
	public List<Information> selectInfoForSelf(Information information);

	// ��������ȡ��Ϣչʾ���û����ο�
	public List<Information> selectInfoByKind(Information information);

	// �����û�������ȡ��Ϣչʾ���û����ο�
	public List<Information> searchInfoForUser(Information information);

	public int addGood(Information information); // ����

	public int addBad(Information information); // ��

	// �����û��ղػ�ȡ��Ϣ
	public List<Information> selectInfoByCollect(int RUId);

	// ��ѯ��Ϣ��������
	public Information selectOneInfoById(Information information);
}

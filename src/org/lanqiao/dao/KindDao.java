package org.lanqiao.dao;

import java.util.List;

import org.lanqiao.bean.Kind;

public interface KindDao {
	//������
	public int addKind(Kind kind);
	//ɾ��ָ�����
	public int deleteKind(Kind kind);
	//�޸�ָ�����
	public int updateKind(Kind kind);
	//��ȡȫ���������Ϣ
	public List<Kind> selectAllKind();
	//��ѯָ�������Ϣ
	public List<Kind> searchKind(Kind kind);
}

package org.lanqiao.dao;

import java.util.List;

import org.lanqiao.bean.RegisterUser;

public interface RegisterUserDao{
	//��ȡȫ���û�
	public List<RegisterUser> selectAllRegisterUser();
	//ɾ��ָ���û�
	public int deleteRegisterUser(RegisterUser registerUser);
	//����Ա��ѯָ���û�
	public List<RegisterUser> searchRegisterUser(RegisterUser registerUser);
	//��ȡ�����û���Ϣ
	public RegisterUser selectOneRegisterUser(int RUId);
	//�޸��û���Ϣ
	public int updatePersonalInformation(RegisterUser registerUser);
	//�޸��û���¼����
	public int updatePassword(RegisterUser registerUser);
	//���ע���û�
	public int addRegisterUser(RegisterUser registerUser);
	//�����û�����ѯ�û��Ƿ����
	public RegisterUser selectRegisterUserByRUName(RegisterUser registerUser);
	//�û���¼
	public RegisterUser loginRegisterUser(RegisterUser registerUser);
	//��ȡ�û���ע��������
	public List<RegisterUser> selectAllFollow(int RUId);
	//��ȡ�û������з�˿
	public List<RegisterUser> selectMyFans(int RUId);
}

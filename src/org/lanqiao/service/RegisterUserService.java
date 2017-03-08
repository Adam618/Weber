package org.lanqiao.service;

import java.util.List;

import org.lanqiao.bean.RegisterUser;

public interface RegisterUserService {
	// ��ȡȫ���û�
	public List<RegisterUser> selectAllRegisterUser();
	// ɾ��ָ���û�
	public int deleteRegisterUser(RegisterUser registerUser);
	//����Ա��ѯָ���û�
	public List<RegisterUser> searchRegisterUser(RegisterUser registerUser);
	//�޸��û���Ϣ
	public int updatePersonalInformation(RegisterUser registerUser); 
	//�޸��û���¼����
	public int updatePassword(RegisterUser registerUser);
	//��ȡ�����û���Ϣ
	public RegisterUser selectOneRegisterUser(int RUId);
	//���ע���û�
	public int addRegisterUser(RegisterUser registerUser);
	//�����û�����ʾ���û��Ƿ����
	public int selectRegisterUserByRUName(RegisterUser registerUser);
	//�û���¼
	public RegisterUser loginRegisterUser(RegisterUser registerUser);
	//��ȡ�û���ע��������
	public List<RegisterUser> selectAllFollow(int ruId);
	//��ȡ�û������з�˿
	public List<RegisterUser> selectMyFans(int RUId);
}

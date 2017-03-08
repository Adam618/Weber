package org.lanqiao.serviceImpl;

import java.util.List;
import org.lanqiao.bean.RegisterUser;
import org.lanqiao.dao.RegisterUserDao;
import org.lanqiao.service.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("RegisterUserService")
public class RegisterUserServiceImpl implements RegisterUserService {
	RegisterUserDao registerUserDao;

	public RegisterUserDao getRegisterUserDao() {
		return registerUserDao;
	}

	@Autowired
	public void setRegisterUserDao(RegisterUserDao registerUserDao) {
		this.registerUserDao = registerUserDao;
	}

	// ��ȡȫ���û�
	@Override
	public List<RegisterUser> selectAllRegisterUser() {
		return registerUserDao.selectAllRegisterUser();
	}

	// ɾ��ָ���û�
	@Override
	public int deleteRegisterUser(RegisterUser registerUser) {
		return registerUserDao.deleteRegisterUser(registerUser);
	}

	@Override
	public List<RegisterUser> searchRegisterUser(RegisterUser registerUser) {
		return registerUserDao.searchRegisterUser(registerUser);
	}

	// ��ȡ�����û���Ϣ
	@Override
	public RegisterUser selectOneRegisterUser(int RUId) {
		return registerUserDao.selectOneRegisterUser(RUId);
	}

	// �޸��û���Ϣ
	@Override
	public int updatePersonalInformation(RegisterUser registerUser) {
		return registerUserDao.updatePersonalInformation(registerUser);
	}

	@Override
	// ���ע���û�
	public int addRegisterUser(RegisterUser registerUser) {
		return registerUserDao.addRegisterUser(registerUser);
	}

	@Override
	// �����û�����ʾ���û��Ƿ����
	public int selectRegisterUserByRUName(RegisterUser registerUser) {
		if (registerUserDao.selectRegisterUserByRUName(registerUser) == null) {
			return 0;
		}
		return registerUserDao.selectRegisterUserByRUName(registerUser)
				.getRUId();
	}

	// �û���¼
	@Override
	public RegisterUser loginRegisterUser(RegisterUser registerUser) {
		return registerUserDao.loginRegisterUser(registerUser);

	}

	@Override
	public List<RegisterUser> selectAllFollow(int ruId) {
		return registerUserDao.selectAllFollow(ruId);
	}

	@Override
	public List<RegisterUser> selectMyFans(int RUId) {
		return registerUserDao.selectMyFans(RUId);
	}

	@Override
	public int updatePassword(RegisterUser registerUser) {
		return registerUserDao.updatePassword(registerUser);
	}

}

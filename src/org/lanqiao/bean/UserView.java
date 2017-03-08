package org.lanqiao.bean;

import java.util.Date;

/**
 * 
 * @author Administrator
 * @����ʵ����
 */

public class UserView {
	private int VId;// ����Id
	private int RUId;// �û�Id
	private String RUName;// �û�����
	private int IId;// ��ϢId
	private String VContent;// ��������
	private Date VTime;// ����ʱ��

	public int getVId() {
		return VId;
	}

	public void setVId(int vId) {
		VId = vId;
	}

	public int getRUId() {
		return RUId;
	}

	public void setRUId(int rUId) {
		RUId = rUId;
	}

	public String getRUName() {
		return RUName;
	}

	public void setRUName(String rUName) {
		RUName = rUName;
	}

	public int getIId() {
		return IId;
	}

	public void setIId(int iId) {
		IId = iId;
	}

	public String getVContent() {
		return VContent;
	}

	public void setVContent(String vContent) {
		VContent = vContent;
	}

	public Date getVTime() {
		return VTime;
	}

	public void setVTime(Date vTime) {
		VTime = vTime;
	}

	public UserView(int vId, int rUId, String rUName, int iId, String vContent,
			Date vTime) {
		super();
		VId = vId;
		RUId = rUId;
		RUName = rUName;
		IId = iId;
		VContent = vContent;
		VTime = vTime;
	}

	public UserView(int rUId, int iId, String vContent, Date vTime) {
		super();
		RUId = rUId;
		IId = iId;
		VContent = vContent;
		VTime = vTime;
	}

	public UserView() {
		super();
	}

	@Override
	public String toString() {
		return "UserView [VId=" + VId + ", RUId=" + RUId + ", IId=" + IId
				+ ", VContent=" + VContent + ", VTime=" + VTime + "]";
	}

}

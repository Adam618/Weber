package org.lanqiao.bean;

public class Manager {
	private int MID;   //����ԱID
	private String MName;   //����Ա�û���
	private String MPassword;    //����Ա����

	public int getMID() {
		return MID;
	}

	public void setMID(int mID) {
		MID = mID;
	}

	public String getMName() {
		return MName;
	}

	public void setMName(String mName) {
		MName = mName;
	}

	public String getMPassword() {
		return MPassword;
	}

	public void setMPassword(String mPassword) {
		MPassword = mPassword;
	}

	public Manager(int mID, String mName, String mPassword) {
		MID = mID;
		MName = mName;
		MPassword = mPassword;
	}

	public Manager(String mName, String mPassword) {
		MName = mName;
		MPassword = mPassword;
	}

	public Manager() {
	}

}

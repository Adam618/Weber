package org.lanqiao.bean;

public class Follow {
	private int RUId;//��ע��
	private int EUId;//����ע��
	public int getRUId() {
		return RUId;
	}
	public void setRUId(int rUId) {
		RUId = rUId;
	}
	public int getEUId() {
		return EUId;
	}
	public void setEUId(int eUId) {
		EUId = eUId;
	}
	public Follow(int rUId, int eUId) {
		super();
		RUId = rUId;
		EUId = eUId;
	}
	
	public Follow(int eUId) {
		super();
		EUId = eUId;
	}
	public Follow() {
		super();
	}
	@Override
	public String toString() {
		return "Follow [RUId=" + RUId + ", EUId=" + EUId + "]";
	}
	
}

package org.lanqiao.bean;

/**
 * 
 * @author Administrator
 * @���ࣨ����ǩ��ʵ����
 */

public class Kind {
	private int KId; // ���Id
	private String KName; // �����

	public int getKId() {
		return KId;
	}

	public void setKId(int kId) {
		KId = kId;
	}

	public String getKName() {
		return KName;
	}

	public void setKName(String kName) {
		KName = kName;
	}

	public Kind(int kId, String kName) {
		KId = kId;
		KName = kName;
	}

	public Kind(int kId) {
		KId = kId;
	}

	public Kind(String kName) {
		super();
		KName = kName;
	}

	public Kind() {
	}

	@Override
	public String toString() {
		return "Kind [KId=" + KId + ", KName=" + KName + "]";
	}

}

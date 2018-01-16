package transition;

import java.util.Set;

/**
 * https://github.com/Ricesmf/graduationProject
 * 
 */
public class TransItem {

	private String relationStr; // ǰ���ϵ��
	private String duration; // ��ʾת����ɵ�ʱ�����䣻����[1,5] (1,5]��
	public boolean guard; // ��relationStr��guard��ͬ����ת���Ƿ���Է�����·���������
	public int times;
	public double probability;
	public Set<Integer> itemToBeCleared;// ������Ԫ����ʱ��Ԫ�أ���ʾ��Ҫ�������¼�ʱ��ʱ��Ԫ��

	public String getRelationStr() {
		return relationStr;
	}

	public void setRelationStr(String relationStr) {
		this.relationStr = relationStr;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public boolean isGuard() {
		return guard;
	}

	public void setGuard(boolean guard) {
		this.guard = guard;
	}

	public Set<Integer> getItemToBeCleared() {
		return itemToBeCleared;
	}

	public void setItemToBeCleared(Set<Integer> itemToBeCleared) {
		this.itemToBeCleared = itemToBeCleared;
	}

	public TransItem(String relationStr, boolean guard) {
		super();
		this.relationStr = relationStr;
		this.guard = guard;
	}

}

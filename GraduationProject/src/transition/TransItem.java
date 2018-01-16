package transition;

import java.util.Set;

/**
 * https://github.com/Ricesmf/graduationProject
 * 
 */
public class TransItem {

	private String relationStr; // 前序关系等
	private String duration; // 表示转换完成的时间区间；例如[1,5] (1,5]等
	public boolean guard; // 以relationStr和guard共同决定转换是否可以发生，路径可行与否
	public int times;
	public double probability;
	public Set<Integer> itemToBeCleared;// 集合中元素是时钟元素；表示需要清零重新计时的时钟元素

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

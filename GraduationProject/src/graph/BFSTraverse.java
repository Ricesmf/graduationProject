package graph;

import java.util.LinkedList;
import java.util.Queue;

public class BFSTraverse {

	// ����ͼ�ı�
	private int[][] edges = { { 0, 1, 0, 0, 0, 1, 0, 0, 0 },
			{ 1, 0, 1, 0, 0, 0, 1, 0, 1 }, { 0, 1, 0, 1, 0, 0, 0, 0, 1 },
			{ 0, 0, 1, 0, 1, 0, 1, 1, 1 }, { 0, 0, 0, 1, 0, 1, 0, 1, 0 },
			{ 1, 0, 0, 0, 1, 0, 1, 0, 0 }, { 0, 1, 0, 1, 0, 1, 0, 1, 0 },
			{ 0, 0, 0, 1, 1, 0, 1, 0, 0 }, { 0, 1, 1, 1, 0, 0, 0, 0, 0 } };
	// ����ͼ�Ķ���
	private String[] vertexs = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
	// ��¼�����ʶ���
	private boolean[] verStatus;
	// �������
	private int vertexsNum = vertexs.length;

	// ����
	private void BFS() {
		verStatus = new boolean[vertexsNum];
		Queue<Integer> temp = new LinkedList<Integer>();
		for (int i = 0; i < vertexsNum; i++) {
			if (!verStatus[i]) {
				System.out.print(vertexs[i] + " ");
				verStatus[i] = true;
				temp.offer(i);
				while (!temp.isEmpty()) {
					int j = temp.poll();
					for (int k = firstAdjvex(j); k >= 0; k = nextAdjvex(j, k)) {
						if (!verStatus[k]) {
							System.out.print(vertexs[k] + " ");
							verStatus[k] = true;
							temp.offer(k);
						}
					}
				}
			}
		}
	}

	// ������i�����ĵ�һ������
	private int firstAdjvex(int i) {
		for (int j = 0; j < vertexsNum; j++) {
			if (edges[i][j] > 0) {
				return j;
			}
		}
		return -1;
	}

	// ������i��������һ������
	private int nextAdjvex(int i, int k) {
		for (int j = (k + 1); j < vertexsNum; j++) {
			if (edges[i][j] > 0) {
				return j;
			}
		}
		return -1;
	}

	// ����
	public static void main(String args[]) {
		new BFSTraverse().BFS();
	}
}

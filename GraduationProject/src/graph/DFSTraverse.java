package graph;

public class DFSTraverse {

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

	public void DFSTra() {
		verStatus = new boolean[vertexsNum];
		for (int i = 0; i < vertexsNum; i++) {
			if (verStatus[i] == false) {
				DFS(i);
			}
		}
	}

	// �ݹ�����
	private void DFS(int i) {
		System.out.print(vertexs[i] + " ");
		verStatus[i] = true;
		for (int j = firstAdjVex(i); j >= 0; j = nextAdjvex(i, j)) {
			if (!verStatus[j]) {
				DFS(j);
			}
		}
	}

	// ������i�����ĵ�һ������
	private int firstAdjVex(int i) {
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
			if (edges[i][j] == 1) {
				return j;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		new DFSTraverse().DFSTra();

	}

}

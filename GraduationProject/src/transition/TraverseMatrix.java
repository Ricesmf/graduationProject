package transition;

import java.util.ArrayList;
import java.util.List;

public class TraverseMatrix {

	public TraverseMatrix(int x) {
		super();
		this.x = x;
	}

	int x = 6;
	boolean[] verStatus = new boolean[x];

	public List<ArrayList<Integer>> matrixTraverse(TransItem[][] matrix) {
		List<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
		// 记录被访问顶点
		return res;
	}

	public void DFSTra(TransItem[][] matrix) {
		verStatus = new boolean[x];
		for (int i = 0; i < x; i++) {
			if (verStatus[i] == false) {
				DFS(matrix, i);
			}
		}
	}

	private void DFS(TransItem[][] matrix, int i) {
		System.out.println(i + " ");
		verStatus[i] = true;
		for (int j = firstAdjVex(matrix, i); j >= 0; j = nextAdjvex(matrix, i, j)) {
			if (!verStatus[j]) {
				DFS(matrix, j);
			}
		}
	}

	// 返回与i相连的第一个顶点
	private int firstAdjVex(TransItem[][] matrix, int i) {
		for (int j = 0; j < x; j++) {
			if (matrix[i][j].getRelationStr().equals("<<") && matrix[i][j].getProbability() > 0 && matrix[i][j].isGuard()) {
				return j;
			}
		}
		return -1;
	}

	// 返回与i相连的下一个顶点
	private int nextAdjvex(TransItem[][] matrix, int i, int k) {
		for (int j = (k + 1); j < x; j++) {
			if (matrix[i][j].getRelationStr().equals("<<") && matrix[i][j].isGuard()) {
				return j;
			}
		}
		return -1;
	}
}

package transition;

public class TestJson {

	public static void main(String[] args) {
		TransItem transItem = new TransItem();
		TransItem[][] tis = transItem.BulidMatrixsFromExcel();
		for (int i = 0; i < tis.length; i++) {
			for (int j = 0; j < tis.length; j++) {
				System.out.println("i = " + i + " j = " + j
						+ " guard : " + tis[i][j].isGuard()
						+ " Probability : " + tis[i][j].getProbability()
						+ " RelationStr : " + tis[i][j].getRelationStr()
						+ " Times : " + tis[i][j].getTimes()
						+ " ItemToBeCleared : " + tis[i][j].getItemToBeCleared());
			}
		}
		TraverseMatrix tm = new TraverseMatrix(tis.length);
		tm.DFSTra(tis);

	}
}

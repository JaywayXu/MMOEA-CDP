import java.util.*;

public class Point {

    public int[][] points;
    public HashMap<Integer, List<SubPath>> distanceMap;
    public int[][] map;
    public Set<Integer> pointSet;
    public List<int[][]> objectives;

    public Point(InitData initData) {
        pointSet = new HashSet<>();
        this.map = initData.map;
        this.objectives = initData.objectives;

        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {

                if (map[i][j] != 1
                        && (map[i - 1][j] != 1 || map[i + 1][j] != 1)
                        && (map[i][j - 1] != 1 || map[i][j + 1] != 1)) {
                    pointSet.add(i * 1000 + j);
                    list.add(new int[] { i, j });
                }
            }
        }
        this.points = new int[list.size()][];
        list.toArray(this.points);
    }

    public void genDistanceMatrix2() {
        distanceMap = new HashMap<>();

        for (int i = 0; i < points.length; i++) {
            int key = points[i][0] * 1000 + points[i][1];
            List<SubPath> list = findJoin(points[i][0], points[i][1]);
            if (list.size() >= 2) {
                distanceMap.put(key, list);
            }
        }
    }

    private List<SubPath> findJoin(int i, int j) {
        List<SubPath> list = new ArrayList<>();

        int x = i + 1, y = j;
        int block = 0;

        int[] values1 = new int[objectives.size()];
        while (map[x][y] != 1) {
            for (int k = 0; k < values1.length; k++) {
                values1[k] += objectives.get(k)[x][y];
            }
            if (map[x][y] == 5)
                block++;
            if (pointSet.contains(x * 1000 + y)) {

                list.add(new SubPath(x * 1000 + y, x - i, block, values1));
                break;
            }
            x++;
        }

        x = i - 1;
        y = j;
        block = 0;
        int[] values2 = new int[objectives.size()];
        while (map[x][y] != 1) {
            for (int k = 0; k < values2.length; k++) {
                values2[k] += objectives.get(k)[x][y];
            }
            if (map[x][y] == 5)
                block++;
            if (pointSet.contains(x * 1000 + y)) {
                list.add(new SubPath(x * 1000 + y, i - x, block, values2));
                break;
            }
            x--;
        }

        x = i;
        y = j + 1;
        block = 0;
        int[] values3 = new int[objectives.size()];

        while (map[x][y] != 1) {
            for (int k = 0; k < values3.length; k++) {
                values3[k] += objectives.get(k)[x][y];
            }
            if (map[x][y] == 5)
                block++;
            if (pointSet.contains(x * 1000 + y)) {
                list.add(new SubPath(x * 1000 + y, y - j, block, values3));
                break;
            }
            y++;
        }

        x = i;
        y = j - 1;
        block = 0;
        int[] values4 = new int[objectives.size()];

        while (map[x][y] != 1) {
            for (int k = 0; k < values4.length; k++) {
                values4[k] += objectives.get(k)[x][y];
            }
            if (map[x][y] == 5)
                block++;
            if (pointSet.contains(x * 1000 + y)) {
                list.add(new SubPath(x * 1000 + y, j - y, block, values4));
                break;
            }
            y--;
        }
        return list;
    }

}

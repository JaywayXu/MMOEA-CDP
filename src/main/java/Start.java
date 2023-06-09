import java.io.IOException;
import java.util.*;

public class Start {

    public static int[] startValue;
    public static int objectsNum;

    public static int INITSIZE = 100;
    public static int LOOP = 100;
    public static int question_num = 12;
    public static int pic_num = 0;

    public static void main(String[] args) throws IOException, InterruptedException {
        int QUESTION_NUM = question_num;
        if (QUESTION_NUM == 5) {
            INITSIZE = 100;
            LOOP = 800;
        } else if (QUESTION_NUM == 10) {
            INITSIZE = 200;
            LOOP = 200;
        } else if (QUESTION_NUM == 9) {
            INITSIZE = 100;
            LOOP = 200;
        } else if (QUESTION_NUM == 12) {
            INITSIZE = 2000;
            LOOP = 100;
        }

        long count = 0;

        for (int i = 0; i < 10; i++) {
            System.out.println("################第" + (i + 1) + "轮结果##################");

            long cur = Start.start(QUESTION_NUM, true, true);
            System.out.println("####耗时：" + cur);
            count += cur;
        }
        System.out.println("------------算法复杂度结果如下--------------");
        System.out.println("----T0:58ms");
        System.out.println("----T" + QUESTION_NUM + "/T0:" + (double) count / 58);
    }

    public static long start(int QUESTION_NUM, boolean isPaint, boolean isPrint)
            throws IOException, InterruptedException {

        long startMs = System.currentTimeMillis();

        InitData initData = new InitData("src/main/resources/data/Problem_" + QUESTION_NUM + ".mat");
        int[][] map = MapInfo.genMapInfo(initData);

        int start_x = initData.START_x;
        int start_y = initData.START_y;
        int goal_x = initData.GOAL_x;
        int goal_y = initData.GOAL_y;

        Point pointSet = new Point(initData);

        objectsNum = pointSet.objectives.size();
        pointSet.genDistanceMatrix2();

        HashMap<Integer, List<SubPath>> distanceMap = pointSet.distanceMap;

        for (Map.Entry<Integer, List<SubPath>> entry : distanceMap.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
            }
        }

        List<Path> pathList = new ArrayList<>();
        Set<Path> set = new HashSet<>();
        startValue = new int[objectsNum];

        if (objectsNum > 0) {
            startValue = initData.startValue;
        }

        for (int i = 0; i < INITSIZE; i++) {

            Path path = new Path(start_x, start_y, goal_x, goal_y, distanceMap);
            if (!set.contains(path)) {
                pathList.add(path);
                set.add(path);
            }
        }

        List<Path> paths = pathList;

        Random random = new Random();

        for (int i = 0; i < LOOP; i++) {
            if (i % 100 == 0)
                System.out.println("=================>" + i + "/" + LOOP + "===============>");
            List<Path> temp = new ArrayList<>();
            while (temp.size() < INITSIZE) {
                float v = random.nextFloat();
                if (v < 0.3) {

                    int rand = random.nextInt(paths.size());

                    List<Path> mutation = Mutation.mutation(QUESTION_NUM, distanceMap, paths.get(rand));

                    for (Path path : mutation) {
                        if (!set.contains(path)) {
                            set.add(path);
                            temp.add(path);
                        }
                    }
                } else {

                    int rand1 = random.nextInt(paths.size());
                    int rand2 = random.nextInt(paths.size());
                    while (rand1 == rand2) {
                        rand1 = random.nextInt(paths.size());
                    }

                    List<Path> crossover = Crossover.crossover(QUESTION_NUM, paths.get(rand1).path,
                            paths.get(rand2).path, distanceMap);

                    for (int k = 0; crossover != null && k < crossover.size(); k++) {
                        if (!set.contains(crossover.get(k))) {
                            set.add(crossover.get(k));
                            temp.add(crossover.get(k));
                        }
                    }
                }
            }
            paths.addAll(temp);

            paths = NSort.sort(QUESTION_NUM, paths, INITSIZE);

        }

        paths = NSort.sort(QUESTION_NUM, paths, -1);

        long endMs = System.currentTimeMillis();

        System.out.println(paths.size());
        for (int i = 0; i < paths.size(); i++) {
            for (int j = i + 1; j < paths.size(); j++) {
                if (check(paths.get(i).path, paths.get(j).path)) {
                    System.out.println("有重复路径！！！！");
                }
            }
        }
        System.out.println("无重复路径！！！！");
        System.out.println(paths.size());

        List<List<Integer>> lists = new ArrayList<>();
        for (Path path : paths) {
            lists.add(path.path);
        }

        Excel.test(QUESTION_NUM, lists);

        if (isPaint) {

            for (int i = 0; i < pic_num; i++) {

                DrawSee drawSee = new DrawSee(map);
                if (question_num == 11 || question_num == 12) {

                    drawSee.paintAreas(map, paths.get(i).path, false, question_num);
                } else
                    drawSee.paintAreas(map, paths.get(i).path, false);

            }
        }
        return endMs - startMs;
    }

    public static boolean check(List<Integer> path1, List<Integer> path2) {
        if (path1.size() != path2.size())
            return false;
        for (int i = 0; i < path1.size(); i++) {
            if (!path1.get(i).equals(path2.get(i))) {
                return false;
            }
        }
        return true;
    }
}

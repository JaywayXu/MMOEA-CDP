import java.util.*;

public class NSort {

    public static List<Path> sort(int questionNum, List<Path> pathList, int num) {
        List<Path> res = new ArrayList<>();
        int n = pathList.size();
        int[] np = new int[n];
        List<List<Integer>> sp = new ArrayList<>();
        for (int i = 0; i < pathList.size(); i++) {
            sp.add(new ArrayList<>());
        }
        List<Integer> font = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (is_dominated(questionNum, pathList.get(i), pathList.get(j))) {
                    sp.get(i).add(j);
                    np[j]++;
                }
                if (is_dominated(questionNum, pathList.get(j), pathList.get(i))) {
                    sp.get(j).add(i);
                    np[i]++;
                }
            }
            if (np[i] == 0 && res.size() < num) {
                res.add(pathList.get(i));
                font.add(i);
            }
            if (np[i] == 0 && num == -1) {
                res.add(pathList.get(i));
                font.add(i);
            }
        }
        if (num == -1) {
            return res;
        }
        while (font.size() != 0) {
            List<Integer> nextLevel = new ArrayList<>();
            for (int i = 0; i < font.size(); i++) {
                for (int j = 0; j < sp.get(font.get(i)).size(); j++) {
                    int k = sp.get(font.get(i)).get(j);
                    np[k]--;
                    if (np[k] == 0) {
                        nextLevel.add(k);
                    }
                }
            }
            font.clear();
            font.addAll(nextLevel);
            if (res.size() + nextLevel.size() <= num) {
                for (Integer integer : nextLevel) {
                    res.add(pathList.get(integer));
                }
            } else {

                List<Path> paths = new ArrayList<>();
                for (Integer integer : nextLevel) {
                    paths.add(pathList.get(integer));
                }
                similaritySort(paths);
                for (int i = 0; i < paths.size() && res.size() < num; i++) {
                    res.add(paths.get(i));
                }

            }
            nextLevel.clear();
        }
        return res;
    }

    public static void similaritySort(List<Path> paths) {

        Object[][] pathArray = new Object[paths.size()][2];
        int n = paths.size();
        for (int i = 0; i < n; i++) {
            pathArray[i][0] = paths.get(i);
            pathArray[i][1] = 0;
        }

        for (int i = 0; i < n; i++) {
            Set<String> set = new HashSet<>();
            for (int j = 0; j < paths.get(i).path.size() - 1; j++) {
                set.add("" + paths.get(i).path.get(j) + paths.get(i).path.get(j + 1));
            }

            for (int ipass = i + 1; ipass < n; ipass++) {
                for (int j = 0; j < paths.get(ipass).path.size() - 1; j++) {
                    String cur = "" + paths.get(ipass).path.get(j) + paths.get(ipass).path.get(j + 1);
                    if (set.contains(cur)) {
                        pathArray[i][1] = (int) pathArray[i][1] + 1;
                        pathArray[ipass][1] = (int) pathArray[ipass][1] + 1;
                    }
                }
            }
            pathArray[i][1] = (float) ((int) pathArray[i][1] / (float) paths.get(i).path.size());
        }
        Arrays.sort(pathArray, Comparator.comparingDouble(o -> (float) o[1]));
        paths.clear();
        for (int i = 0; i < pathArray.length; i++) {
            paths.add((Path) pathArray[i][0]);
        }
    }
    private static boolean is_dominated(int questionNum, Path path1, Path path2) {
        if (questionNum == 1)
            return is_dominated1(path1, path2);
        else if (questionNum <= 5)
            return is_dominated2(path1, path2);
        else if (questionNum <= 10)
            return is_dominated3(path1, path2);
        else if (questionNum == 11)
            return is_dominated4(path1, path2);
        else
            return is_dominated5(path1, path2);
    }

    private static boolean is_dominated1(Path path1, Path path2) {
        int small_and_equal = 0;
        int small = 0;
        if (path1.distance < path2.distance) {
            small = small + 1;
        }
        if (path1.block < path2.block) {
            small = small + 1;
        }
        if (path1.distance <= path2.distance) {
            small_and_equal = small_and_equal + 1;
        }
        if (path1.block <= path2.block) {
            small_and_equal = small_and_equal + 1;
        }
        if (small_and_equal == 2 && small >= 1) {
            return true;
        } else
            return false;
    }

    private static boolean is_dominated2(Path path1, Path path2) {
        int small_and_equal = 0;
        int small = 0;
        if (path1.distance < path2.distance) {
            small = small + 1;
        }
        if (path1.block < path2.block) {
            small = small + 1;
        }
        if (path1.cross < path2.cross) {
            small = small + 1;
        }
        if (path1.distance <= path2.distance) {
            small_and_equal = small_and_equal + 1;
        }
        if (path1.block <= path2.block) {
            small_and_equal = small_and_equal + 1;
        }
        if (path1.cross <= path2.cross) {
            small_and_equal = small_and_equal + 1;
        }
        if (small_and_equal == 3 && small >= 1) {
            return true;
        } else
            return false;
    }

    private static boolean is_dominated3(Path path1, Path path2) {
        int small_and_equal = 0;
        int small = 0;
        if (path1.distance < path2.distance) {
            small = small + 1;
        }
        if (path1.distance <= path2.distance) {
            small_and_equal = small_and_equal + 1;
        }
        for (int i = 0; i < path1.objectives.length; i++) {
            if (path1.objectives[i] < path2.objectives[i]) {
                small = small + 1;
            }
            if (path1.objectives[i] <= path2.objectives[i]) {
                small_and_equal = small_and_equal + 1;
            }
        }
        if (small_and_equal == path1.objectives.length + 1 && small >= 1) {
            return true;
        } else
            return false;
    }

    private static boolean is_dominated4(Path path1, Path path2) {
        int yellowpoint1Key = 10021;
        int small_and_equal = 0;
        int small = 0;
        if (path1.path.contains(yellowpoint1Key) && !path2.path.contains(yellowpoint1Key)) {
            return true;
        } else if (path1.path.contains(yellowpoint1Key) && path2.path.contains(yellowpoint1Key)) {

            if (path1.distance < path2.distance) {
                small = small + 1;
            }
            if (path1.distance <= path2.distance) {
                small_and_equal = small_and_equal + 1;
            }
            for (int i = 0; i < path1.objectives.length; i++) {
                if (path1.objectives[i] < path2.objectives[i]) {
                    small = small + 1;
                }
                if (path1.objectives[i] <= path2.objectives[i]) {
                    small_and_equal = small_and_equal + 1;
                }
            }
            if (small_and_equal == path1.objectives.length + 1 && small >= 1) {
                return true;
            } else
                return false;
        } else {
            return false;
        }
    }

    private static boolean is_dominated5(Path path1, Path path2) {
        int yellowpoint1Key = 18010;
        int yellowpoint2Key = 10017;
        int small_and_equal = 0;
        int small = 0;
        int path1contains = 0;
        int path2contains = 0;
        if (path1.path.contains(yellowpoint1Key))
            path1contains = path1contains + 1;
        if (path1.path.contains(yellowpoint2Key))
            path1contains = path1contains + 1;
        if (path2.path.contains(yellowpoint1Key))
            path2contains = path2contains + 1;
        if (path2.path.contains(yellowpoint2Key))
            path2contains = path2contains + 1;
        if (path1contains == 2 && path2contains != 2)
            return true;
        else if (path1contains == 2 && path2contains == 2) {
            if (path1.distance < path2.distance)
                small += 1;
            if (path1.distance <= path2.distance)
                small_and_equal += 1;
            for (int i = 0; i < path1.objectives.length; i++) {
                if (path1.objectives[i] < path2.objectives[i])
                    small += 1;
                if (path1.objectives[i] <= path2.objectives[i])
                    small_and_equal += 1;
            }
            if (small_and_equal == path1.objectives.length + 1 && small >= 1)
                return true;
            else
                return false;
        }

        else if (path1contains != 2 && path2contains != 2) {
            if (path1contains > path2contains)
                return true;
            else
                return false;
        } else
            return false;
    }
}

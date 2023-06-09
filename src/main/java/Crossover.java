import java.util.*;

public class Crossover {

  public static List<Path> crossover(
    int QUESTION_NUM,
    List<Integer> path1,
    List<Integer> path2,
    HashMap<Integer, List<SubPath>> distanceMap
  ) {
    List<Path> pathList = new ArrayList<>(2);
    Map<Integer, Integer> map = new HashMap<>();

    for (int i = 2; i < path1.size() - 2; i++) {
      map.put(path1.get(i), i);
    }

    List<Integer> crossPoint1 = new ArrayList<>();
    List<Integer> crossPoint2 = new ArrayList<>();

    for (int i = 2; i < path2.size() - 2; i++) {
      int key = path2.get(i);
      if (map.containsKey(key) && !map.containsKey(path2.get(i - 1))) {
        crossPoint1.add(map.get(key));
        crossPoint2.add(i);
      }
    }

    if (crossPoint1.isEmpty()) {
      return null;
    }

    for (int i = 0; i < crossPoint1.size(); i++) {
      List<Integer> list1 = new ArrayList<>(
        path1.subList(0, crossPoint1.get(i))
      );
      list1.addAll(path2.subList(crossPoint2.get(i), path2.size()));
      if (QUESTION_NUM == 12) {
        pathList.add(Path.calculatePath(list1, distanceMap));
      } else if (check(list1)) pathList.add(
        Path.calculatePath(list1, distanceMap)
      );
      List<Integer> list2 = new ArrayList<>(
        path2.subList(0, crossPoint2.get(i))
      );
      list2.addAll(path1.subList(crossPoint1.get(i), path1.size()));
      if (QUESTION_NUM == 12) {
        pathList.add(Path.calculatePath(list2, distanceMap));
      } else if (check(list2)) pathList.add(
        Path.calculatePath(list2, distanceMap)
      );
    }

    return pathList;
  }

  public static boolean check(List<Integer> list) {
    Set<Integer> set = new HashSet<>();
    for (Integer integer : list) {
      if (set.contains(integer)) {
        return false;
      } else {
        set.add(integer);
      }
    }
    return true;
  }
}

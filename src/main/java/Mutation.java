import java.util.*;
import java.util.stream.Collectors;

public class Mutation {

    public static List<Path> mutation(int QUESTION_NUM, HashMap<Integer, List<SubPath>> distanceMap, Path path) {
        Random random = new Random();
        List<Path> pathList = new ArrayList<>(2);
        int mutationPoint = random.nextInt(path.path.size() - 2);
        int goal = path.path.get(path.path.size() - 1);
        Path before = new Path();
        while (before.path.size() == 0 || before.path.get(before.path.size() - 1) != goal) {
            before = doMutation(QUESTION_NUM, "before", goal, mutationPoint, distanceMap, path.path);
        }
        pathList.add(before);

        int mutationPointAfter = random.nextInt(path.path.size() - 2) + 2;
        int goalAfter = path.path.get(0);
        Path after = new Path();
        while (after.path.size() == 0 || after.path.get(0) != goalAfter) {
            after = doMutation(QUESTION_NUM, "after", goalAfter, mutationPointAfter, distanceMap, path.path);
        }
        pathList.add(after);
        return pathList;
    }
    public static Path doMutation(int QUESTION_NUM, String method, int goal, int randomPoint,
            HashMap<Integer, List<SubPath>> distanceMap, List<Integer> path) {
        Random r = new Random();
        List<Integer> newPath = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        if (method.equals("before")) {
            for (int i = 0; i <= randomPoint; i++) {
                set.add(path.get(i));
                newPath.add(path.get(i));
            }
        } else {
            for (int i = path.size() - 1; i >= randomPoint; i--) {
                set.add(path.get(i));
                newPath.add(path.get(i));
            }
        }
        int preKey = newPath.get(newPath.size() - 1);

        while (preKey != goal) {
            List<SubPath> subPaths;
            if (QUESTION_NUM == 12) {
                subPaths = distanceMap.get(preKey).stream().collect(Collectors.toList());
            } else {
                subPaths = distanceMap.get(preKey).stream().filter(subPath -> !set.contains(subPath.getKey()))
                        .collect(Collectors.toList());
            }
            if (subPaths.size() == 0) {
                break;
            }
            SubPath subPath = subPaths.get(r.nextInt(subPaths.size()));
            preKey = subPath.getKey();
            newPath.add(preKey);
            set.add(preKey);
        }
        if (method.equals("after")) {
            Collections.reverse(newPath);
        }
        return Path.calculatePath(newPath, distanceMap);
    }
}

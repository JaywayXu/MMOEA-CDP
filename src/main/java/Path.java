import java.util.*;
import java.util.stream.Collectors;

public class Path {

    List<Integer> path;
    int distance;
    int block;
    int cross;
    int[] objectives;

    public Path() {
        path = new ArrayList<>();
        distance = 0;
        block = 0;
        cross = 0;
    }

    public Path(int startX, int startY, int goalX, int goalY, HashMap<Integer, List<SubPath>> distanceMap) {
        List<Integer> path = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        int preKey = startY * 1000 + startX;
        path.add(preKey);
        set.add(preKey);
        objectives = new int[Start.objectsNum];
        Arrays.fill(objectives, 0);
        int goalKey = goalY * 1000 + goalX;
        Random r = new Random();
        boolean flag = true;

        while (flag) {
            while (preKey != goalKey) {

                List<SubPath> test1 = distanceMap.get(preKey);

                List<SubPath> subPaths = distanceMap.get(preKey).stream()
                        .filter(subPath -> !set.contains(subPath.getKey())).collect(Collectors.toList());
                if (subPaths.size() == 0) {
                    break;
                }

                SubPath subPath = subPaths.get(r.nextInt(subPaths.size()));
                preKey = subPath.getKey();
                path.add(preKey);
                set.add(preKey);
            }

            if (preKey != goalKey) {
                preKey = startY * 1000 + startX;
                path.clear();
                path.add(preKey);
                set.clear();
                set.add(preKey);
            } else {
                break;
            }
        }

        Path newPath = Path.calculatePath(path, distanceMap);
        this.path = newPath.path;
        this.cross = newPath.cross;
        this.distance = newPath.distance;
        this.block = newPath.block;
        this.objectives = newPath.objectives;
    }

    public static Path calculatePath(List<Integer> path, HashMap<Integer, List<SubPath>> distanceMap) {
        Path newPath = new Path();
        newPath.path = path;
        newPath.objectives = new int[Start.objectsNum];
        newPath.distance = 1;

        newPath.cross = distanceMap.get(path.get(path.size() - 1)).size() > 2 ? 1 : 0;
        for (int i = 0; i < newPath.objectives.length; i++) {
            newPath.objectives[i] = Start.startValue[i];
        }

        for (int i = 0; i < path.size() - 1; i++) {

            if (distanceMap.get(path.get(i)).size() > 2) {
                newPath.cross++;
            }

            int finalI = i;

            distanceMap.get(path.get(i)).forEach(item -> {
                if (item.getKey() == path.get(finalI + 1)) {
                    newPath.distance += item.getDistance();
                    newPath.block += item.getBlock();
                    for (int j = 0; j < item.getObjectives().length; j++) {
                        newPath.objectives[j] += item.getObjectives()[j];
                    }
                }
            });
        }

        return newPath;
    }

    @Override
    public String toString() {
        return "Path{" +
                "path=" + path +
                ", distance=" + distance +
                ", block=" + block +
                ", cross=" + cross +
                ", objectives=" + Arrays.toString(objectives) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Path))
            return false;
        Path path1 = (Path) o;
        if (path1.path.size() != this.path.size()) {
            return false;
        }
        for (int i = 0; i < this.path.size(); i++) {
            if (!path1.path.get(i).equals(this.path.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int code = 0;
        for (int i = 0; i < this.path.size(); i++) {
            code += (this.path.get(i) * i) % 10007;
        }
        return code;
    }
}

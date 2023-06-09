import java.util.Arrays;

public class SubPath {
    private int key;
    private int distance;
    private int block;

    private int[] objectives;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int[] getObjectives() {
        return objectives;
    }

    public void setObjectives(int[] objectives) {
        this.objectives = objectives;
    }

    public SubPath() {
    }

    public SubPath(int key, int distance, int block, int cross) {
        this.key = key;
        this.distance = distance;
        this.block = block;
    }

    public SubPath(int key, int distance, int block, int[] objectives) {
        this.key = key;
        this.distance = distance;
        this.block = block;
        this.objectives = objectives;
    }

    @Override
    public String toString() {
        return "SubPath{" +
                "key=" + key +
                ", distance=" + distance +
                ", block=" + block +
                ", objectives=" + Arrays.toString(objectives) +
                '}';
    }
}

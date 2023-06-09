import com.jmatio.io.MatFileReader;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLDouble;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitData {
    private MatFileReader read;
    public int[][] map;
    public int[][] red_areas;
    public List<int[][]> objectives;
    public int questionNum;
    public int[] startValue;

    @Override
    public String toString() {
        return "InitData{" +
                "GOAL_x=" + GOAL_x +
                ", GOAL_y=" + GOAL_y +
                ", START_x=" + START_x +
                ", START_y=" + START_y +
                '}';
    }

    public int GOAL_x;
    public int GOAL_y;
    public int START_x;
    public int START_y;

    public InitData(String string) throws IOException {
        this.read = new MatFileReader(string);
        this.GOAL_x = getInt("GOAL_x", this.read);
        this.GOAL_y = getInt("GOAL_y", this.read);
        this.START_x = getInt("START_x", this.read);
        this.START_y = getInt("START_y", this.read);

        MLArray mapArray = read.getMLArray("Map");
        MLDouble d = (MLDouble) mapArray;
        double[][] matrix = d.getArray();

        this.map = new int[matrix.length + 1][matrix[0].length + 1];
        Arrays.fill(map[0], 1);

        for (int i = 0; i < map.length; i++) {
            map[i][0] = 1;
        }
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                this.map[i + 1][j + 1] = (int) matrix[i][j];
            }
        }

        objectives = new ArrayList<>();

        this.questionNum = Integer.parseInt(string.split("_")[1].split("\\.")[0]);

        if (questionNum <= 5) {
            MLArray redArray = read.getMLArray("Red_areas");
            MLDouble red = (MLDouble) redArray;
            double[][] redAreas = red.getArray();
            this.red_areas = new int[redAreas.length][redAreas[0].length];
            for (int i = 0; i < redAreas.length; ++i) {
                for (int j = 0; j < redAreas[0].length; ++j) {
                    this.red_areas[i][j] = (int) redAreas[i][j];
                }
            }
        }

        if (questionNum > 5 && questionNum <= 12) {
            MLArray fArray = read.getMLArray("F");
            MLDouble f = (MLDouble) fArray;
            double[][] fValues = f.getArray();

            for (int i = 2; i < fValues[0].length; i++) {
                int[][] objective = new int[matrix.length + 1][matrix[0].length + 1];
                for (int j = 0; j < fValues.length; j++) {
                    int x = (int) fValues[j][0];
                    int y = (int) fValues[j][1];
                    objective[y][x] = (int) (fValues[j][i] * 10);
                }
                objectives.add(objective);
            }
            startValue = new int[objectives.size()];
            for (int i = 0; i < objectives.size(); i++) {
                startValue[i] = objectives.get(i)[START_y][START_x];
            }
        }
    }

    private int getInt(String string, MatFileReader read) {
        MLArray value = read.getMLArray(string);
        MLDouble doubleVale = (MLDouble) value;
        return (int) (double) doubleVale.get(0);
    }
}

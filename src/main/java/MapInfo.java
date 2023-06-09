import java.io.IOException;

public class MapInfo {

    public static int[][] genMapInfo(InitData initData) throws IOException {
        initData.map[initData.START_y][initData.START_x] = 2;
        initData.map[initData.GOAL_y][initData.GOAL_x] = 9;
        if (initData.red_areas != null) {
            for (int[] area : initData.red_areas) {
                initData.map[area[1]][area[0]] = 5;
            }
        }
        return initData.map;
    }

}

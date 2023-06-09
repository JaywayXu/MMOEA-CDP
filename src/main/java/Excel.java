import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Excel {

    public static void test(int num, List<List<Integer>> lists) throws IOException {

        String[] filenames = {

                "src/main/resources/result/MOASTAR/Table II.xlsx",
                "src/main/resources/result/MOASTAR/Table III.xlsx",
                "src/main/resources/result/MOASTAR/Table IV.xlsx",
                "src/main/resources/result/MOASTAR/Table V.xlsx",
                "src/main/resources/result/MOASTAR/Table VI.xlsx",
                "src/main/resources/result/MOASTAR/Table VII.xlsx",
                "src/main/resources/result/MOASTAR/Table VIII.xlsx",
                "src/main/resources/result/MOASTAR/Table IX.xlsx",
                "src/main/resources/result/MOASTAR/Table X.xlsx",
                "src/main/resources/result/MOASTAR/Table XI.xlsx",
                "src/main/resources/result/MOASTAR/Table XII.xlsx",
                "src/main/resources/result/MOASTAR/Table XIII.xlsx",
                "src/main/resources/result/MOASTAR/Table XIV.xlsx",
        };
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            List<Integer> list = new ArrayList<>();
            list.add(lists.get(i).get(0));
            for (int j = 2; j < lists.get(i).size(); j++) {
                int num1 = lists.get(i).get(j);
                int num2 = lists.get(i).get(j - 2);
                if (num1 / 1000 != num2 / 1000 && num1 % 1000 != num2 % 1000) {
                    list.add(lists.get(i).get(j - 1));
                }
            }
            list.add(lists.get(i).get(lists.get(i).size() - 1));
            res.add(list);
        }

        String filename = filenames[num - 1];

        FileInputStream input = new FileInputStream(filename);

        Workbook workbook = WorkbookFactory.create(input);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);
        int RowNum = sheet.getLastRowNum();
        int columnNum = row.getPhysicalNumberOfCells();

        int[][] data = new int[RowNum + 1][columnNum];
        for (int i = 0; i < data.length - 1; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = (int) sheet.getRow(i + 1).getCell(j).getNumericCellValue();
            }
        }
        Set<List<Integer>> set = new HashSet<>();
        for (int i = 0; i < data[0].length; i = i + 2) {
            List<Integer> cur = new ArrayList<>();
            cur.add(data[0][i] + data[0][i + 1] * 1000);
            for (int j = 2; j < data.length; j++) {
                if (data[j][i] != 0) {
                    if (data[j][i] != data[j - 2][i] && data[j][i + 1] != data[j - 2][i + 1]) {
                        cur.add(data[j - 1][i] + data[j - 1][i + 1] * 1000);
                    }
                } else {
                    int key = data[j - 1][i] + data[j - 1][i + 1] * 1000;
                    if (key != cur.get(cur.size() - 1)) {
                        cur.add(key);
                    }
                    break;
                }
            }
            set.add(cur);
        }
        int count = 0;
        for (List<Integer> item : res) {
            if (set.contains(item)) {
                count++;
            }
        }
        System.out.println("++++++++++++++++++++++++++++");
        System.out.println(count + "/" + set.size());
        System.out.println("++++++++++++++++++++++++++++");
        input.close();
    }

}

import java.awt.*;
import java.io.IOException;
import java.util.List;
import javax.swing.*;

class DrawSee extends JFrame {

  private static final int sx = 50;
  private static final int sy = 50;
  private static final int w = 10;
  private static final int rw = 400;

  private Graphics jg;

  private Color rectColor = new Color(0xf5f5f5);

  public DrawSee(int[][] map) throws IOException {
    Container p = getContentPane();
    setBounds(100, 100, 1000, 1000);
    setVisible(true);
    p.setBackground(rectColor);
    setLayout(null);
    setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    try {
      Thread.sleep(500);
    } catch (Exception e) {
      e.printStackTrace();
    }

    jg = this.getGraphics();
  }

  public void paintAreas(
    int[][] cmap,
    List<Integer> cpath,
    boolean visibleCrossPoint,
    int pronum
  ) {
    Graphics g = this.jg;
    int[][] map = cmap;
    try {
      g.setColor(Color.black);
      for (int i = 0; i < map.length; i++) {
        for (int j = 0; j < map[i].length; j++) {
          if (map[i][j] == 0) {
            paintAreaOne(g, Color.LIGHT_GRAY, i, j);
          }
        }
      }

      paintPath(cpath, Color.cyan);
      for (int i = 0; i < map.length; i++) {
        for (int j = 0; j < map[i].length; j++) {
          if (map[i][j] == 2) {
            paintAreaOne(g, Color.blue, i, j);
          } else if (map[i][j] == 5) {
            paintAreaOne(g, Color.red, i, j);
          } else if (map[i][j] == 9) {
            paintAreaOne(g, Color.green, i, j);
          }
          if (visibleCrossPoint && map[i][j] == 3) {
            paintAreaOne(g, Color.yellow, i, j);
          }
        }
      }
      if (pronum == 11) paintAreaOne(g, Color.yellow, 10, 21);
      if (pronum == 12) {
        paintAreaOne(g, Color.yellow, 18, 10);
        paintAreaOne(g, Color.yellow, 10, 17);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void paintAreas(
    int[][] cmap,
    List<Integer> cpath,
    boolean visibleCrossPoint
  ) {
    Graphics g = this.jg;
    int[][] map = cmap;
    try {
      g.setColor(Color.black);
      for (int i = 0; i < map.length; i++) {
        for (int j = 0; j < map[i].length; j++) {
          if (map[i][j] == 0) {
            paintAreaOne(g, Color.LIGHT_GRAY, i, j);
          }
        }
      }

      paintPath(cpath, Color.cyan);
      for (int i = 0; i < map.length; i++) {
        for (int j = 0; j < map[i].length; j++) {
          if (map[i][j] == 2) {
            paintAreaOne(g, Color.blue, i, j);
          } else if (map[i][j] == 5) {
            paintAreaOne(g, Color.red, i, j);
          } else if (map[i][j] == 9) {
            paintAreaOne(g, Color.green, i, j);
          }
          if (visibleCrossPoint && map[i][j] == 3) {
            paintAreaOne(g, Color.yellow, i, j);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void paintAreaOne(Graphics g, Color color, int i, int j) {
    g.setColor(color);
    g.fillRect(50 + w * j, 50 + w * i, w, w);
  }

  public void paintAreas2(
    int[][] cmap,
    int[][] keys,
    List<Integer> cpath1,
    List<Integer> cpath2,
    boolean visibleCrossPoint
  ) {
    Graphics g = this.jg;
    int[][] map = cmap;
    try {
      g.setColor(Color.black);
      for (int i = 0; i < map.length; i++) {
        for (int j = 0; j < map[i].length; j++) {
          if (map[i][j] == 0) {
            paintAreaOne(g, Color.LIGHT_GRAY, i, j);
          }
        }
      }

      for (int i = 0; i < keys.length; i++) {
        paintAreaOne(g, Color.black, keys[i][0], keys[i][1]);
      }
      paintPath(cpath1, Color.yellow);
      paintPath(cpath2, Color.blue);
      for (int i = 0; i < map.length; i++) {
        for (int j = 0; j < map[i].length; j++) {
          if (map[i][j] == 2) {
            paintAreaOne(g, Color.blue, i, j);
          } else if (map[i][j] == 5) {
            paintAreaOne(g, Color.red, i, j);
          } else if (map[i][j] == 9) {
            paintAreaOne(g, Color.green, i, j);
          }
          if (visibleCrossPoint && map[i][j] == 3) {
            paintAreaOne(g, Color.yellow, i, j);
          }
        }
      }

      paintAreaOne(g, Color.black, 7, 23);
      paintAreaOne(g, Color.black, 15, 37);
      paintAreaOne(g, Color.black, 20, 17);
      paintAreaOne(g, Color.black, 27, 24);
      paintAreaOne(g, Color.black, 32, 8);
      paintAreaOne(g, Color.black, 32, 21);
      paintAreaOne(g, Color.black, 33, 25);
      paintAreaOne(g, Color.black, 33, 29);
      paintAreaOne(g, Color.black, 35, 37);
      paintAreaOne(g, Color.black, 38, 23);
      paintAreaOne(g, Color.black, 42, 10);
      paintAreaOne(g, Color.black, 42, 40);

      paintAreaOne(g, Color.red, 38, 32);
      paintAreaOne(g, Color.red, 15, 20);
      paintAreaOne(g, Color.red, 10, 28);
      paintAreaOne(g, Color.red, 30, 10);
      paintAreaOne(g, Color.yellow, 27, 12);
      paintAreaOne(g, Color.yellow, 20, 28);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void paintAreaC(Graphics g, Color color, int i, int j) {
    g.setColor(color);
    g.fillRect(50 + w * j, 50 + w * i, w, w);
  }

  public void test(List<SubPath> subPathList) {
    for (SubPath subPath : subPathList) {
      int key = subPath.getKey();
      int i = key / 1000;
      int j = key % 1000;
      paintAreaOne(jg, Color.yellow, i, j);
    }
  }

  public void paintPath(List<Integer> cpath, Color color) {
    int[][] path = new int[cpath.size()][2];
    for (int i = 0; i < cpath.size(); i++) {
      path[i][0] = cpath.get(i) / 1000;
      path[i][1] = cpath.get(i) % 1000;
    }

    for (int i = 0; i < path.length - 1; i++) {
      if (path[i][1] == path[i + 1][1]) {
        int min = Math.min(path[i][0], path[i + 1][0]);
        int max = Math.max(path[i][0], path[i + 1][0]);
        for (int j = min; j <= max; j++) {
          paintAreaOne(jg, color, j, path[i][1]);
        }
      } else {
        int min = Math.min(path[i][1], path[i + 1][1]);
        int max = Math.max(path[i][1], path[i + 1][1]);
        for (int j = min; j <= max; j++) {
          paintAreaOne(jg, color, path[i][0], j);
        }
      }
    }
  }

  public void paintComponents(Graphics g) {
    try {
      g.setColor(Color.black);

      g.drawRect(sx, sy, rw, rw);

      for (int i = 1; i < 40; i++) {
        g.drawLine(sx + (i * w), sy, sx + (i * w), sy + rw);

        g.drawLine(sx, sy + (i * w), sx + rw, sy + (i * w));

        for (int j = 0; j < 40; j++) {}
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

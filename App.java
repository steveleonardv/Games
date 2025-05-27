import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
      int boardWidth=600;
      int boardHeight=boardWidth;
      JFrame frame = new JFrame("Snake");
      frame.setVisible(true);
      frame.setSize(boardWidth,boardHeight);
      frame.setResizable(false);
      frame.setLocationRelativeTo(null);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Snakegame snakeGame= new Snakegame(boardWidth,boardHeight);
      frame.add(snakeGame);
      frame.pack();
      snakeGame.requestFocus();
    }
}

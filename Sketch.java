import processing.core.PApplet;
import processing.core.PImage; 
import processing.core.PFont;
import java.util.*;

public class Sketch extends PApplet {
	
	int intWindowWidth = 600;
  int intWindowHeight = 600;

  int intCellWidth = 80; 
  int intCellHeight = 80;
  int intRowCount = 4;
  int intColCount = 4;
  int intMargin = 10;

  int intGameWidth = intCellWidth * intColCount + intMargin * (intColCount + 1);
  int intGameHeight = intCellHeight * intRowCount + intMargin * (intColCount + 1);

  boolean[][] blnGrid = new boolean[4][4]; 
  
  String[] dice1 = {"R", "I", "F", "O", "B", "X"};
  String[] dice2 = {"I", "F", "E", "H", "E", "Y"};
  String[] dice3 = {"D", "E", "N", "O", "W", "S"};
  String[] dice4 = {"U", "T", "O", "K", "N", "D"};
  String[] dice5 = {"H", "M", "S", "R", "A", "O"};
  String[] dice6 = {"L", "U", "P", "E", "T", "S"};
  String[] dice7 = {"A", "C", "I", "T", "O", "A"};
  String[] dice8 = {"Y", "L", "G", "K", "U", "E"};
  String[] dice9 = {"Q", "B", "M", "J", "O", "A"};
  String[] dice10 = {"E", "H", "I", "S", "P", "N"};
  String[] dice11 = {"V", "E", "T", "I", "G", "N"};
  String[] dice12 = {"B", "A", "L", "I", "Y", "T"};
  String[] dice13 = {"E", "Z", "A", "V", "N", "D"};
  String[] dice14 = {"R", "A", "L", "E", "S", "C"};
  String[] dice15 = {"U", "W", "I", "L", "R", "G"};
  String[] dice16 = {"P", "A", "C", "E", "M", "D"};
  
  String[][] dices = {dice1, dice2, dice3, dice4, dice5, dice6, dice7, dice8, dice9, dice10, dice11, dice12, dice13, dice14, dice15, dice16};

  PImage background;
  PFont calibri;

  ArrayList<String> randomLetters = getLetterList();

  int intFontSize = 60;
 
  public void settings() {
    // set window size according to width and height variables which are calculated using the cell and intMargin dimensions
    size(intWindowWidth, intWindowHeight);
  }

  public void setup() {
    String strLetters = "";
    for ( String letter : randomLetters) {
      strLetters += letter;
    }
    System.out.println(strLetters);

    background = loadImage("green.jpg");
    background.resize(intWindowWidth, intWindowHeight);
    image(background, 0, 0);

    calibri = createFont("Calibri Bold", intFontSize);
  }

  public void draw() {
    
    strokeWeight(4);
    stroke(193, 225, 193);
    fill(1, 50, 32);

    rect(centerHoriz(intGameWidth + intGameWidth / 28), centerVert(intGameHeight + intGameHeight / 28), intGameWidth + intGameWidth / 28, intGameHeight + intGameHeight / 28);

    int count = 0;
    for (int r  = 0; r < intRowCount; r++) {
      for (int c = 0; c < intColCount; c++) {
        // change colour to green if array value is true
        if (blnGrid[r][c] == true){
          fill(0, 255, 0);
        }
        else {
          fill(255);
        }
        
        int intCellX = intMargin + intMargin * c + intCellWidth * c + centerHoriz(intGameWidth + intGameWidth / 28) + intGameWidth / 56;
        int intCellY = intMargin + intMargin * r + intCellHeight * r + centerVert(intGameHeight + intGameHeight / 28) + intGameHeight / 56;
        roundedRect(intCellX, intCellY, intCellWidth, intCellHeight);
    
        fill(0);
        textFont(calibri);
        textAlign(CENTER, CENTER);
        text(randomLetters.get(count), intCellX, intCellY, intCellWidth, intCellHeight);
        count++;
      }
    }
  }

  public void roundedRect(int x, int y, int width, int height) {
    noStroke();
    rect(x + width / 10, y, width - width / 5, height);
    rect(x, y + height / 10, width, height - height / 5);
    ellipse(x + width / 10, y + height / 10, width / 5, height / 5);
    ellipse(x + width - width / 10, y + height / 10, width / 5, height / 5);
    ellipse(x + width / 10, y + height - height / 10, width / 5, height / 5);
    ellipse(x + width - width / 10, y + height - height / 10, width / 5, height / 5);
  }

  public int centerHoriz(int intElementWidth) {
    return((intWindowWidth - intElementWidth) / 2);
  }

  // returns value that will center the element verticaly
  public int centerVert(int intElementHeight) {
    return((intWindowHeight - intElementHeight) / 2);
  }

  public ArrayList<String> getLetterList() {
    ArrayList<String> letters = new ArrayList<String>();

    for (String[] x : dices) {
      String letter = x[(int) (Math.random() * 6)];
      letters.add(letter);
    }
    return letters;
  }

  public void mousePressed() {
  }
}

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

  int intCellR;
  int intCellG;
  int intCellB;
    
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

    int count = 0;
    for (int r  = 0; r < intRowCount; r++) {
      for (int c = 0; c < intColCount; c++) {
        // change colour to green if array value is true
        if (blnGrid[r][c] == true){
          intCellR = 0;
          intCellG = 255; 
          intCellB = 0;
        }
        else {
          intCellR = 255;
          intCellG = 255; 
          intCellB = 255;
        }
        
        fill(intCellR, intCellG, intCellB);
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

    for (String[] dice : dices) {
      String letter = dice[(int) (Math.random() * 6)];
      letters.add(letter);
    }
    return letters;
  }

  public void mouseDragged() {
    int intMargin2 = intCellWidth / 4;
    int intCell2Width = intCellWidth / 2;
    int intCell2Height = intCellHeight / 2;

    // do a double for loop to go thru cols and rows, set gridcol and gridrow in them
    for (int c = 0; c < intColCount; c++) {
      for (int r = 0; r < intRowCount; r++) {
        int intCellX1 = intMargin + intMargin * c + intMargin2 + intMargin2 * 2 * c + intCell2Width * c + centerHoriz(intGameWidth + intGameWidth / 28) + intGameWidth / 56;
        int intCellX2 = intMargin + intMargin * c + intMargin2 + intMargin2 * 2 * c + intCell2Width * c + centerHoriz(intGameWidth + intGameWidth / 28) + intGameWidth / 56 + intCell2Width;
        int intCellY1 = intMargin + intMargin * r + intMargin2 + intMargin2 * 2 * r + intCell2Height * r + centerVert(intGameHeight + intGameHeight / 28) + intGameHeight / 56;
        int intCellY2 = intMargin + intMargin * r + intMargin2 + intMargin2 * 2 * r + intCell2Height * r + centerVert(intGameHeight + intGameHeight / 28) + intGameHeight / 56 + intCell2Height;

        if ((mouseX > intCellX1) && (mouseX < intCellX2) && (mouseY > intCellY1) && (mouseY < intCellY2)) {
          blnGrid[r][c] = true;
        }
        else {
        }
      }
    }
  }

  public void mousePressed() {
    for (int c = 0; c < intColCount; c++) {
      for (int r = 0; r < intRowCount; r++) {
        int intCellX1 = intMargin + intMargin * c + intCellWidth * c + centerHoriz(intGameWidth + intGameWidth / 28) + intGameWidth / 56;
        int intCellX2 = intMargin + intMargin * c + intCellWidth * c + centerHoriz(intGameWidth + intGameWidth / 28) + intGameWidth / 56 + intCellWidth;
        int intCellY1 = intMargin + intMargin * r + intCellHeight * r + centerVert(intGameHeight + intGameHeight / 28) + intGameHeight / 56;
        int intCellY2 = intMargin + intMargin * r + intCellHeight * r + centerVert(intGameHeight + intGameHeight / 28) + intGameHeight / 56 + intCellHeight;

        if ((mouseX > intCellX1) && (mouseX < intCellX2) && (mouseY > intCellY1) && (mouseY < intCellY2)) {
          blnGrid[r][c] = true;
        }
        else {
        }
      }
    }
  }

  public void mouseReleased() {
    int intGridCol = (int) ((mouseX - (intMargin + centerHoriz(intGameWidth + intGameWidth / 28) + intGameWidth / 56)) / (intMargin + intCellWidth));
    int intGridRow = (int) ((mouseY - (intMargin + centerVert(intGameWidth + intGameWidth / 28) + intGameWidth / 56)) / (intMargin + intCellHeight));
    for (int c = 0; c < intColCount; c++){
      for (int r = 0; r < intRowCount; r++){
        blnGrid[c][r] = false;
      }

    }
    //blnGrid[intGridRow][intGridCol] = false;
  }
/*
  class wordHunt {
    int intCellWidth; 
    int intCellHeight;
    int intRowCount;
    int intColCount;
    int intMargin;

    int intCellR;
    int intCellG;
    int intCellB;

    public wordHunt(int cellWidth, int cellHeight, int rowCount, int colCount, int margin){
      intCellWidth = cellWidth; 
      intCellHeight = cellHeight;
      intRowCount = rowCount;
      intColCount = colCount;
      intMargin = margin;
    }

    public void drawBoard(){
      strokeWeight(4);
      stroke(193, 225, 193);
      fill(1, 50, 32);

      rect(centerHoriz(intGameWidth + intGameWidth / 28), centerVert(intGameHeight + intGameHeight / 28), intGameWidth + intGameWidth / 28, intGameHeight + intGameHeight / 28);
    }

    public void drawCells(){
      int count = 0;
      for (int r  = 0; r < intRowCount; r++) {
        for (int c = 0; c < intColCount; c++) {
          // change colour to green if array value is true
          if (blnGrid[r][c] == true){
            intCellR = 0;
            intCellG = 255; 
            intCellB = 0;
          }
          else {
            intCellR = 255;
            intCellG = 255; 
            intCellB = 255;
          }
          
          fill(intCellR, intCellG, intCellB);
          int intCellX = intMargin + intMargin * c + intCellWidth * c + centerHoriz(intGameWidth + intGameWidth / 28) + intGameWidth / 56;
          int intCellY = intMargin + intMargin * r + intCellHeight * r + centerVert(intGameHeight + intGameHeight / 28) + intGameHeight / 56;
          roundedRect(intCellX, intCellY, intCellWidth, intCellHeight);

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
  }

/*
  class cell extends wordHunt {

    int intCellR;
    int intCellG;
    int intCellB;

    boolean isClicked;

    public cell(int cellWidth, int cellHeight, int rowCount, int colCount, int margin){
      super();
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

    public void drawCell(int c, int r){
      int intCellX = intMargin + intMargin * c + intCellWidth * c + centerHoriz(intGameWidth + intGameWidth / 28) + intGameWidth / 56;
      int intCellY = intMargin + intMargin * r + intCellHeight * r + centerVert(intGameHeight + intGameHeight / 28) + intGameHeight / 56;
      roundedRect(intCellX, intCellY, intCellWidth, intCellHeight);
    }
  }
  */
}

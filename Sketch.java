import processing.core.PApplet;
import processing.core.PImage; 
import processing.core.PFont;
import java.util.*;
import java.io.File;

public class Sketch extends PApplet {
	
	int intWindowWidth = 600;
  int intWindowHeight = 800;

  int intCellWidth = 80; 
  int intCellHeight = 80;
  int intRowCount = 4;
  int intColCount = 4;
  int intMargin = 10;

  int intGameWidth = intCellWidth * intColCount + intMargin * (intColCount + 1);
  int intGameHeight = intCellHeight * intRowCount + intMargin * (intColCount + 1);

  Cell[][] cGrid = new Cell[intRowCount][intColCount];

  String word = "";

  ArrayList<String> wordLetters = new ArrayList<String>();
  ArrayList<String> wordList = new ArrayList<String>();

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
  PImage imgWordHunt;
  PFont calibri;
  PFont poppins;

  ArrayList<String> randomLetters = getLetterList();
  ArrayList<Cell> wordChars = new ArrayList<Cell>();
  //ArrayList<Integer> cellCordsX = new  ArrayList<Integer>();
  //ArrayList<Integer> cellCordsY = new ArrayList<Integer>();

  int intFontSize = 60;

  int intCellR;
  int intCellG;
  int intCellB;

  int cnt = 0;

  boolean mouseReleased;

  boolean isSetup = false;
  boolean homeScreen = true;
  boolean wordHunt = false;
  boolean wordHuntInst = false;
    
  public void settings() {
    // set window size according to width and height variables which are calculated using the cell and intMargin dimensions
    size(intWindowWidth, intWindowHeight);
  }

  public void setup() {

    background = loadImage("green.jpg");
    background.resize(intWindowWidth, intWindowHeight);
    image(background, 0, 0);

    imgWordHunt = loadImage("word hunt.png");
    imgWordHunt.resize(125, 125);

    calibri = createFont("Calibri Bold", intFontSize);
    poppins = createFont("Poppins-ExtraBold.ttf", 70);

  }

  public void draw() {
    if (homeScreen) {
      textFont(poppins);
      textAlign(CENTER);
      fill(255);
      text("WORD GAMES", 300, 210);
      noStroke();
      fill(120);
      rect(50, 250, 500, 300, 15);
      image(imgWordHunt, (float)112.5, (float)325.0);
      textSize(16);
      fill(255);
      text("Word Hunt", 175, 470);

    }

    if (wordHuntInst) {
      image(background, 0, 0);
      //rect()
      rect(200, 550, 200, 60, 15);
    }

    if (keyPressed && key == 'c') {
      wordHunt = true;
      image(background, 0, 0);
    }

    if (wordHunt) {

      homeScreen = false;
      setupWordGrid();
      isSetup = true;
      stroke(0);
      strokeWeight(10);
      createGameboard();

      //if (mousePressed == true ) {
        //line(mouseX, mouseY, pmouseX, pmouseY);
      //}

    }

    /*
    for (int r  = 0; r < intRowCount; r++) {
      for (int c = 0; c < intColCount; c++) {
        // change colour to green if array value is true
        if (cGrid[r][c].getStatus() == true){
          intCellR = 255;
          intCellG = 255; 
          intCellB = 255;
        }
        else {
          intCellR = 235;
          intCellG = 232; 
          intCellB = 207;
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
    */

    //rect(200, 300, 200, 300);
    
    //line(500, 200, 500, 500);

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
    if (wordHunt) {
      int intMargin2 = intCellWidth / 4;
      int intCell2Width = intCellWidth / 2;
      int intCell2Height = intCellHeight / 2;

      strokeCap(PROJECT);
      strokeWeight(48);
      //line(10, 10, 500, 500);

      for (int c = 0; c < intColCount; c++) {
        for (int r = 0; r < intRowCount; r++) {
          int intCellX1 = intMargin + intMargin * c + intMargin2 + intMargin2 * 2 * c + intCell2Width * c + centerHoriz(intGameWidth + intGameWidth / 28) + intGameWidth / 56;
          int intCellX2 = intMargin + intMargin * c + intMargin2 + intMargin2 * 2 * c + intCell2Width * c + centerHoriz(intGameWidth + intGameWidth / 28) + intGameWidth / 56 + intCell2Width;
          int intCellY1 = intMargin + intMargin * r + intMargin2 + intMargin2 * 2 * r + intCell2Height * r + centerVert(intGameHeight + intGameHeight / 28) + intGameHeight / 56;
          int intCellY2 = intMargin + intMargin * r + intMargin2 + intMargin2 * 2 * r + intCell2Height * r + centerVert(intGameHeight + intGameHeight / 28) + intGameHeight / 56 + intCell2Height;

          if ((mouseX > intCellX1) && (mouseX < intCellX2) && (mouseY > intCellY1) && (mouseY < intCellY2) && cGrid[r][c].getStatus() == false && isAdjacent(r, c) && canSelect(cGrid[r][c], wordChars.get(cnt - 1))) {
            wordChars.add(cGrid[r][c]);
            cGrid[r][c].setStatus(true);
            cGrid[r][c].setCellXY(intCellX1 + intCellWidth / 2, intCellY1 + intCellHeight / 2);

            word += cGrid[r][c].getLetter();

            textSize(40);
            image(background, 0, 0);
            text(word, (width / 2), 150);

            //line(wordChars.get(cnt).getCellX(), wordChars.get(cnt).getCellY(), wordChars.get(cnt - 1).getCellX(), wordChars.get(cnt - 1).getCellY());
            

            cnt++;
            
            
          
          }
          else {
          }
        }
      } 
    }
  }

  public void mousePressed() {
    if (homeScreen) {
      if (mouseX > 112.5 && mouseY > 325 && mouseX < 237.5 && mouseY < 459) {
        wordHuntInst = true;
      }
    }

    if (wordHunt) {
      for (int c = 0; c < intColCount; c++) {
        for (int r = 0; r < intRowCount; r++) {
          int intCellX1 = intMargin + intMargin * c + intCellWidth * c + centerHoriz(intGameWidth + intGameWidth / 28) + intGameWidth / 56;
          int intCellX2 = intMargin + intMargin * c + intCellWidth * c + centerHoriz(intGameWidth + intGameWidth / 28) + intGameWidth / 56 + intCellWidth;
          int intCellY1 = intMargin + intMargin * r + intCellHeight * r + centerVert(intGameHeight + intGameHeight / 28) + intGameHeight / 56;
          int intCellY2 = intMargin + intMargin * r + intCellHeight * r + centerVert(intGameHeight + intGameHeight / 28) + intGameHeight / 56 + intCellHeight;
      
          if ((mouseX > intCellX1) && (mouseX < intCellX2) && (mouseY > intCellY1) && (mouseY < intCellY2)) {
            cGrid[r][c].setStatus(true);
            cGrid[r][c].setCellXY(intCellX1 + intCellWidth / 2, intCellY1 + intCellHeight / 2);

            wordChars.add(cGrid[r][c]);
            word += cGrid[r][c].getLetter();
            textSize(40);
            text(word, width / 2, 150);
            cnt++;
          }
          else {
          }
        }
      }
    }
  }

  public void mouseReleased() {


    if (wordHunt) {
      int intGridCol = (int) ((mouseX - (intMargin + centerHoriz(intGameWidth + intGameWidth / 28) + intGameWidth / 56)) / (intMargin + intCellWidth));
      int intGridRow = (int) ((mouseY - (intMargin + centerVert(intGameWidth + intGameWidth / 28) + intGameWidth / 56)) / (intMargin + intCellHeight));
      for (int c = 0; c < intColCount; c++){
        for (int r = 0; r < intRowCount; r++){
          cGrid[r][c].setStatus(false);
        }
      }
      
      image(background, 0, 0);
      
      wordList.add(word);
      word = "";
      wordChars.clear();
      cnt = 0;

      for (int i = 0; i < wordList.size(); i ++) {
        System.out.println(wordList.get(i));
      }
    }
  }

  
  public boolean isAdjacent(int r, int c) {
    if (r != 0 && r != 3 && c != 0 && c != 3) {
      return cGrid[r-1][c-1].getStatus() || cGrid[r-1][c].getStatus() || cGrid[r-1][c+1].getStatus() || cGrid[r][c-1].getStatus() || cGrid[r][c+1].getStatus() || cGrid[r+1][c-1].getStatus() || cGrid[r+1][c].getStatus() || cGrid[r+1][c+1].getStatus();
    }
    else if (r == 0 && c == 0) {
      return cGrid[r+1][c].getStatus() || cGrid[r+1][c+1].getStatus() || cGrid[r][c+1].getStatus();
    }
    else if (r == 0 && c == 3) {
      return cGrid[r+1][c].getStatus() || cGrid[r+1][c-1].getStatus() || cGrid[r][c-1].getStatus();
    }
    else if (r == 3 && c == 0) {
      return cGrid[r-1][c].getStatus() || cGrid[r-1][c+1].getStatus() || cGrid[r][c+1].getStatus();
    }
    else if (r == 3 && c == 3) {
      return cGrid[r-1][c].getStatus() || cGrid[r-1][c-1].getStatus() || cGrid[r][c-1].getStatus();
    }

    else if (r == 0) {
      return cGrid[r][c-1].getStatus() || cGrid[r][c+1].getStatus() || cGrid[r+1][c-1].getStatus() || cGrid[r+1][c].getStatus() || cGrid[r+1][c+1].getStatus();
    }
    else if (r == 3) {
      return cGrid[r-1][c-1].getStatus() || cGrid[r-1][c].getStatus() || cGrid[r-1][c+1].getStatus() || cGrid[r][c-1].getStatus() || cGrid[r][c+1].getStatus();
    }

    else if (c == 0) {
      return cGrid[r+1][c].getStatus() || cGrid[r-1][c].getStatus() || cGrid[r+1][c+1].getStatus() || cGrid[r][c+1].getStatus() || cGrid[r-1][c+1].getStatus(); 
    }
    else if (c == 3) {
      return cGrid[r+1][c].getStatus() || cGrid[r-1][c].getStatus() || cGrid[r+1][c-1].getStatus() || cGrid[r][c-1].getStatus() || cGrid[r-1][c-1].getStatus();
    }
    else {
      return false;
    }
  }

  
  public boolean canSelect(Cell current, Cell previous) {
    if (Math.abs(current.cVal - previous.cVal) != 1 && Math.abs(current.rVal - previous.rVal) != 1) {
      return false;
    }
    else {
      return true;
    }
  }

  /**
   * This method creates game board when its called
   */
  private void createGameboard(){

    int count = 0;
    for (int r  = 0; r < intRowCount; r++) {
      for (int c = 0; c < intColCount; c++) {
        // change colour to green if array value is true
        if (cGrid[r][c].getStatus() == true){
          intCellR = 255;
          intCellG = 255; 
          intCellB = 255;
        }
        else {
          intCellR = 235;
          intCellG = 232; 
          intCellB = 207;
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

  public void setupWordGrid() {
    if (isSetup == false) {
      String strLetters = "";
      for (String letter : randomLetters) {
        strLetters += letter;
      }
      System.out.println(strLetters);

      int count = 0;

      for (int r = 0; r < intRowCount; r++) {
        for (int c = 0; c < intColCount; c++) {
          Cell cell = new Cell();
          cell.setLetter(randomLetters.get(count));
          cell.setR(r);
          cell.setC(c);
          cGrid[r][c] = cell;
          count++;
        }
      }
    }
    else {

    }
  }


  class Cell {

    // center coordinates of cells
    int cellX;
    int cellY;

    String letter;

    boolean isSelected;

    int intCellWidth; 
    int intCellHeight;

    int num;

    int rVal;
    int cVal;

    public Cell(int cellX, int cellY, int intCellWidth, int intCellHeight, int r, int c){
      this.cellX = cellX;
      this.cellY = cellY;
      this.intCellWidth = intCellWidth;
      this.intCellWidth = intCellHeight;
      this.rVal = r;
      this.cVal = c;
      isSelected = false;
    }

    public Cell(){
      isSelected = false;
    }

    public int getCellX(){
      return cellX;
    }

    public int getCellY(){
      return cellY;
    }

    public String getLetter(){
      return letter;
    }

    public boolean getStatus() {
      return isSelected;
    }
    public int getNum() {
      return num;
    }

    public int rVal() {
      return rVal;
    }

    public int cVal() {
      return cVal;
    }

    public void setLetter(String letter){
      this.letter = letter;
    }

    public void setCellDimensions(int intCellWidth, int intCellHeight){
      this.intCellWidth = intCellWidth;
      this.intCellWidth = intCellHeight;
    }

    public void setCellXY(int cellX, int cellY){
      this.cellX = cellX;
      this.cellY = cellY;
    }

    public void setStatus(boolean isSelected) {
      this.isSelected = isSelected;
    }

    public void setNum(int num) {
      this.num = num;
    }

    public void setR(int r) {
      this.rVal = r;
    }

    public void setC(int c) {
      this.cVal = c;
    }

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

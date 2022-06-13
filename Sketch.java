import processing.core.PApplet;
import processing.core.PImage; 
import processing.core.PFont;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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

  ArrayList<String> threeLetterWords = new ArrayList<String>();
  ArrayList<String> fourLetterWords = new ArrayList<String>();
  ArrayList<String> fiveLetterWords = new ArrayList<String>();
  ArrayList<String> sixLetterWords = new ArrayList<String>();
  ArrayList<String> sevenLetterWords = new ArrayList<String>();
  ArrayList<String> eightLetterWords = new ArrayList<String>();

  ArrayList<ArrayList<String>> listOfLists = new ArrayList<ArrayList<String>>(Arrays.asList(threeLetterWords, fourLetterWords, fiveLetterWords, sixLetterWords, sevenLetterWords, eightLetterWords));
  String[] types = {"3_letter_words.txt", "4_letter_words.txt", "5_letter_words.txt", "6_letter_words.txt", "7_letter_words.txt", "8_letter_words.txt"};

  PImage background;
  PImage imgWordHunt;
  PImage imgDemoWH;
  PFont calibri;
  PFont poppins;

  ArrayList<String> randomLetters = getLetterList();
  ArrayList<Cell> wordChars = new ArrayList<Cell>();

  int intFontSize = 60;

  int intCellR;
  int intCellG;
  int intCellB;

  int cnt = 0;

  boolean mouseReleased;

  boolean isSetup = false;
  boolean homeScreen = true;
  boolean wordHuntInst = false;
  boolean wordHunt = false;
  boolean results = false;

  boolean checkCreateGameboard = true;
  boolean displayTextandConnectCell = false;
  boolean checkDrawBg = true;
  boolean drawTime = false;

  String word = "";
  int points;

  boolean newWord;
    
  int intTime = 6000;
  Integer IntDisplayTime;

  public void settings() {
    // set window size according to width and height variables which are calculated using the cell and intMargin dimensions
    size(intWindowWidth, intWindowHeight);
  }

  public void setup() {

    frame.setAlwaysOnTop(true);

    background = loadImage("green.jpg");
    background.resize(intWindowWidth, intWindowHeight);

    imgWordHunt = loadImage("word hunt.png");
    imgWordHunt.resize(125, 125);

    imgDemoWH = loadImage("demo word hunt.png");
    imgDemoWH.resize(240, 240);

    calibri = createFont("Calibri Bold", intFontSize);
    poppins = createFont("Poppins-ExtraBold.ttf", 70);

    try {
      for (int i = 0; i < types.length; i++) {
        File file = new File(types[i]);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
          listOfLists.get(i).add(scanner.nextLine());
        }
        scanner.close();
      }
    }
    catch (FileNotFoundException ignored) {
    }
  }

  public void draw() {
    if (homeScreen) {

      image(background, 0, 0);
      points = 0;

      textFont(poppins);
      textAlign(CENTER);
      fill(10);
      text("WORD GAMES", 300, 210);

      noStroke();
      fill(120, 200);
      rect(50, 250, 500, 300, 15);

      image(imgWordHunt, (float)112.5, (float)325.0);

      textSize(16);
      fill(255);
      text("Word Hunt", 175, 470);

      rect((float)362.5, 325, 125, 125);

      text("coming soon", 425, 470);
    }

    if (wordHuntInst) {
      image(background, 0, 0);

      fill(255);
      rect(50, 120, 500, 530);

      fill(135, 214, 141);
      rect(200, 550, 200, 60, 15);

      image(imgDemoWH, 180, 280);

      fill(0);
      textFont(calibri, 16);
      textAlign(CENTER, CENTER);
      text("PRESS SPACE TO PLAY", 200, 550, 200, 60);

      textFont(poppins, 30);
      text("How to play:", 50, 120, 500, 70);

      textFont(calibri, 15);
      text("Connect letters togther by dragging your finger. Make as many words as you can.", 150, 190, 300, 80);
    }

    if (keyPressed && key == ' ') {
      wordHunt = true;
    }

    if (wordHunt) {
      if (intTime == 0) {
        results = true;
      }
      IntDisplayTime = intTime / 100;

      if ((float)(intTime / 100) == (float)intTime / 100) {
        text(IntDisplayTime.toString(), 0, 0);
        System.out.println(IntDisplayTime);
      }
      intTime--;

      noStroke();
      rect(200, 50, 200, 80);
      
      text("", 200, 50, 200, 80);

      wordHuntInst = false;
      homeScreen = false;
      setupWordGrid();
      isSetup = true;
      
      if (checkDrawBg == true) {
        drawGridBg();
        checkDrawBg = false;
      }

      createGameboard();
      if (displayTextandConnectCell == true){
        displaySelectedText();
      }

    }

    if (keyPressed && key == 'p') {
      results = true;
    }
    if (results) {
      wordHunt = false;
      image(background, 0, 0);

      fill(0);
      textSize(30);
      textAlign(CENTER);
      text("POINTS: " + points, width / 2, 200);
    }

  }

  /**
   * Created a rounded rectangle
   * @param x
   * @param y
   * @param width
   * @param height
   */
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

  /**
   * Set flags to true when mouse drag even starts
   */
  public void mouseDragged() {
    if (wordHunt) {

      strokeCap(PROJECT);
      strokeWeight(10);

      checkCreateGameboard = true;
      displayTextandConnectCell = true;
      //displaySelectedText();
    }
  }

  /**
   * Private method to display text and highlight cell and connect cell when mouse dragged over cell boxes
   */
  private void displaySelectedText(){
    int intMargin2 = intCellWidth / 4;
    int intCell2Width = intCellWidth / 2;
    int intCell2Height = intCellHeight / 2;
    int intCell2X1;
    int intCell2X2;
    int intCell2Y1;
    int intCell2Y2;
    int intCellX;
    int intCellY;


    for (int c = 0; c < intColCount; c++) {
      for (int r = 0; r < intRowCount; r++) {
        intCell2X1 = intMargin + intMargin * c + intMargin2 + intMargin2 * 2 * c + intCell2Width * c + centerHoriz(intGameWidth + intGameWidth / 28) + intGameWidth / 56;
        intCell2X2 = intMargin + intMargin * c + intMargin2 + intMargin2 * 2 * c + intCell2Width * c + centerHoriz(intGameWidth + intGameWidth / 28) + intGameWidth / 56 + intCell2Width;
        intCell2Y1 = intMargin + intMargin * r + intMargin2 + intMargin2 * 2 * r + intCell2Height * r + centerVert(intGameHeight + intGameHeight / 28) + intGameHeight / 56;
        intCell2Y2 = intMargin + intMargin * r + intMargin2 + intMargin2 * 2 * r + intCell2Height * r + centerVert(intGameHeight + intGameHeight / 28) + intGameHeight / 56 + intCell2Height;

        intCellX = intMargin + intMargin * c + intCellWidth * c + centerHoriz(intGameWidth + intGameWidth / 28) + intGameWidth / 56;
        intCellY = intMargin + intMargin * r + intCellHeight * r + centerVert(intGameHeight + intGameHeight / 28) + intGameHeight / 56;
        
        if ((mouseX > intCell2X1) && (mouseX < intCell2X2) && (mouseY > intCell2Y1) && (mouseY < intCell2Y2) && cGrid[r][c].getStatus() == false && isAdjacent(r, c) && canSelect(cGrid[r][c], wordChars.get(cnt - 1))) {
          wordChars.add(cGrid[r][c]);
          cGrid[r][c].setStatus(true);
          cGrid[r][c].setCellXY(intCellX + intCellWidth / 2, intCellY + intCellHeight / 2);

          word += cGrid[r][c].getLetter();
          
          drawGridBg();

          fill(0);
          textSize(40);
          text(word, (width / 2), 150);
          
          doAddWord(cnt, word);

          cnt++;       
        
        }
        
      }
    }

    stroke(150);
    strokeWeight(7);
    for (int i=0; i< wordChars.size(); i ++){
      if (i >= 1){
        line(wordChars.get(i-1).getCellX(), wordChars.get(i-1).getCellY(), wordChars.get(i).getCellX(), wordChars.get(i).getCellY());
      } else {

      }  
    }
  }

  /**
   * Highlight cell and display text when mouse pressed
   */
  public void mousePressed() {
    
    if (homeScreen) {
      if (mouseX > 112.5 && mouseY > 325 && mouseX < 237.5 && mouseY < 459) {
        wordHuntInst = true;
      }
    }

    if (wordHunt) {
      checkCreateGameboard = true;
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
            
            if (doAddWord(cnt, word) == 1) {
              newWord = false;
            }
            else if (doAddWord(cnt, word) == 2) {
              newWord = true;
            }
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
      //int intGridCol = (int) ((mouseX - (intMargin + centerHoriz(intGameWidth + intGameWidth / 28) + intGameWidth / 56)) / (intMargin + intCellWidth));
      //int intGridRow = (int) ((mouseY - (intMargin + centerVert(intGameWidth + intGameWidth / 28) + intGameWidth / 56)) / (intMargin + intCellHeight));
      for (int c = 0; c < intColCount; c++){
        for (int r = 0; r < intRowCount; r++){
          cGrid[r][c].setStatus(false);
        }
      }
      
      if (doAddWord(cnt, word) == 2) {
        wordList.add(word);
      }
      checkDrawBg = true;
      checkCreateGameboard = true;
      displayTextandConnectCell = false;

      word = "";
      wordChars.clear();
      cnt = 0;

      for (int i = 0; i < wordList.size(); i ++) {
        System.out.println(wordList.get(i));
      }
    }
  }

  public int doAddWord(int count, String word) {
    if (wordList.contains(word)) {
      //you already got the word
      return 1;
    }
    else if (cnt == 3 && threeLetterWords.contains(word.toLowerCase())) {
      //true
      points += 100;
      return 2;
    }
    else if (cnt == 4 && fourLetterWords.contains(word.toLowerCase())) {
      points += 400;
      return 2;
    }
    else if (cnt == 5 && fiveLetterWords.contains(word.toLowerCase())) {
      points += 800;
      return 2;
    }
    else if (cnt == 6 && sixLetterWords.contains(word.toLowerCase())) {
      points += 1400;
      return 2;
    }
    else if (cnt == 7 && sevenLetterWords.contains(word.toLowerCase())) {
      points += 1800;
      return 2;
    }
    else if (cnt == 8 && eightLetterWords.contains(word.toLowerCase())) {
      points += 2200;
      return 2;
    }
    else {
      //false
      return 3;
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

    if (checkCreateGameboard) {

      int count = 0;

      for (int r  = 0; r < intRowCount; r++) {
        for (int c = 0; c < intColCount; c++) {
          // change colour to white if array value is true
          if (cGrid[r][c].getStatus()){
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
    checkCreateGameboard = false;
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

  public void setDictionary() {
    try {
      for (int i = 0; i < types.length; i++) {
        File file = new File(types[i]);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
          listOfLists.get(i).add(scanner.nextLine());
        }
        scanner.close();
      }
    }
    catch (FileNotFoundException ignored) {
    }
  }

  public void drawGridBg() {
    image(background, 0, 0);
    fill(0, 100);
    rect((intWindowWidth - intGameWidth) / 2, (intWindowHeight - intGameHeight) / 2, intGameWidth, intGameHeight, 15);
  }
  class Cell {

    // center coordinates of cells
    int cellX;
    int cellY;

    String letter;

    boolean isSelected;
    boolean isNewWord;

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
}

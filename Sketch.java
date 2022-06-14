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
  PImage imgWordSearch;
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

  boolean blnCheckCreateGameboard = true;
  boolean blnDisplayTextAndConnectCell = false;
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

    imgWordSearch = loadImage("word hunt.png");
    imgWordSearch.resize(125, 125);

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

      image(imgWordSearch, (float)112.5, (float)325.0);

      textSize(16);
      fill(255);
      text("Word Search", 175, 470);

      // I was originally going to make another game but I ran out of time
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

    if (keyPressed && key == ' ' && wordHuntInst == true) {
      wordHunt = true;
      checkDrawBg = true;
    }

    if (wordHunt) {
      blnCheckCreateGameboard = true;
      wordHuntInst = false;
      homeScreen = false;
      setupWordGrid();
      isSetup = true;

      if (checkDrawBg == true) {
        drawGridBg();
        checkDrawBg = false;
      }

      createGameboard();
      if (blnDisplayTextAndConnectCell == true){
        selectAndConnectCells();
      }

      // go to results screen (end game) when time reaches 0 or if user presses control
      if (intTime == 0 || (keyPressed && keyCode == CONTROL) && wordHunt == true) {
        results = true;
      }
      IntDisplayTime = intTime / 100;

      // white box to show words and points
      noStroke();
      fill(255);
      rect(100, 0, 400, 100);

      textAlign(LEFT);
      fill(0);
      textFont(poppins, 30);
      text("SCORE: " + points, 140, 40);

      textFont(poppins, 18);
      text("words: " + wordList.size(), 140, 80);

      fill(26, 76, 57);
      rect(400, 90, 90, 30, 10);

      textFont(calibri, 14);
      textAlign(CENTER, CENTER);
      fill(255);
      text("time: " + IntDisplayTime.toString(), 400, 90, 90, 30);

      // increment time by -1 every millisecond
      intTime--;

    }

    if (results) {
      wordHunt = false;
      image(background, 0, 0);

      fill(50, 100);
      rect(100, 60, 400, 82);

      fill(255, 100);
      rect(150, 150, 300, 550, 14);

      fill(0);
      textFont(poppins, 30);
      textAlign(CENTER);
      text("TOTAL POINTS: " + points, intWindowWidth / 2, 100);

      textFont(calibri, 25);
      text("total words: " + wordList.size(), intWindowWidth / 2, 130);

      textFont(calibri, 22);
      textAlign(LEFT);
      int intWordDispX = 190;
      int intWordDispY;
      int x = 0;
      // loop through list of scored words and display in two columns
      for (int i = 0; i < wordList.size(); i++) {

        if (200 + x * 30 > 650) {
          intWordDispX = 340;
          x = 0;
        }
        intWordDispY = 200 + x * 30;
    
        text(wordList.get(i), intWordDispX, intWordDispY);
        x++;
      }

      fill(0);
      textAlign(CENTER);
      textFont(calibri, 14);
      text("Play Again", 490, (float)762);

      fill(0);
      rect(530, 750, 40, 15);
      triangle(570, 740, 570, 775, 585, (float)757.5);
      
      // return to homescreen if user presses play again
      if (mousePressed == true && mouseX > 490 && mouseX < 585 && mouseY > 740 && mouseY < 775 && results == true) {
        results = false;
        homeScreen = true;
      }
    }
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
      blnCheckCreateGameboard = true;
      blnDisplayTextAndConnectCell = true;
    }
  }

  /**
   * Private method to display text and highlight cell and connect cell when mouse dragged over cell boxes
   */
  public void selectAndConnectCells(){
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
      } 
      else {
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
      blnCheckCreateGameboard = true;
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

            fill(0);
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
      for (int c = 0; c < intColCount; c++){
        for (int r = 0; r < intRowCount; r++){
          cGrid[r][c].setStatus(false);
        }
      }
      
      if (doAddWord(cnt, word) == 2) {
        wordList.add(word);
      }
      checkDrawBg = true;
      blnCheckCreateGameboard = true;
      blnDisplayTextAndConnectCell = false;

      word = "";
      wordChars.clear();
      cnt = 0;

      for (int i = 0; i < wordList.size(); i ++) {
        System.out.println(wordList.get(i));
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
  

  // Methods that determine validity of words and selected cells


  /**
   * Method to determine whether to add the selected 'word' to the found word list
   * @param wordLen: 
   * @param strWord
   * @return
   */
  public int doAddWord(int wordLen, String strWord) {
    // Word has already been found
    if (wordList.contains(word)) {
      return 1;
    }

    // Word is valid
    else if (wordLen == 3 && threeLetterWords.contains(strWord.toLowerCase())) {
      points += 100;
      return 2;
    }
    else if (wordLen == 4 && fourLetterWords.contains(strWord.toLowerCase())) {
      points += 400;
      return 2;
    }
    else if (wordLen == 5 && fiveLetterWords.contains(strWord.toLowerCase())) {
      points += 800;
      return 2;
    }
    else if (wordLen == 6 && sixLetterWords.contains(strWord.toLowerCase())) {
      points += 1400;
      return 2;
    }
    else if (wordLen == 7 && sevenLetterWords.contains(strWord.toLowerCase())) {
      points += 1800;
      return 2;
    }
    else if (wordLen == 8 && eightLetterWords.contains(strWord.toLowerCase())) {
      points += 2200;
      return 2;
    }

    // Word is not valid
    else {
      return 3;
    }
  }

  /**
   * Method to determine if a cell object in the 2d array is adjacent to a selected cell
   * @param r: row value
   * @param c: column value
   * @return boolean value based on if a cell is adjacnet to any selected cells
   */
  public boolean isAdjacent(int r, int c) {
    // If cell is not on the edges of the grid
    if (r != 0 && r != 3 && c != 0 && c != 3) {
      return cGrid[r-1][c-1].getStatus() || cGrid[r-1][c].getStatus() || cGrid[r-1][c+1].getStatus() || cGrid[r][c-1].getStatus() || cGrid[r][c+1].getStatus() || cGrid[r+1][c-1].getStatus() || cGrid[r+1][c].getStatus() || cGrid[r+1][c+1].getStatus();
    }

    // If cell is on top left
    else if (r == 0 && c == 0) {
      return cGrid[r+1][c].getStatus() || cGrid[r+1][c+1].getStatus() || cGrid[r][c+1].getStatus();
    }
    // If cell is on top right
    else if (r == 0 && c == 3) {
      return cGrid[r+1][c].getStatus() || cGrid[r+1][c-1].getStatus() || cGrid[r][c-1].getStatus();
    }
    // If cell is on bottom left
    else if (r == 3 && c == 0) {
      return cGrid[r-1][c].getStatus() || cGrid[r-1][c+1].getStatus() || cGrid[r][c+1].getStatus();
    }
    // If cell is on bottom right
    else if (r == 3 && c == 3) {
      return cGrid[r-1][c].getStatus() || cGrid[r-1][c-1].getStatus() || cGrid[r][c-1].getStatus();
    }

    // If cell is on the top edge
    else if (r == 0) {
      return cGrid[r][c-1].getStatus() || cGrid[r][c+1].getStatus() || cGrid[r+1][c-1].getStatus() || cGrid[r+1][c].getStatus() || cGrid[r+1][c+1].getStatus();
    }
    // If cell is on the bottom edge
    else if (r == 3) {
      return cGrid[r-1][c-1].getStatus() || cGrid[r-1][c].getStatus() || cGrid[r-1][c+1].getStatus() || cGrid[r][c-1].getStatus() || cGrid[r][c+1].getStatus();
    }
    // If cell is on the left edge
    else if (c == 0) {
      return cGrid[r+1][c].getStatus() || cGrid[r-1][c].getStatus() || cGrid[r+1][c+1].getStatus() || cGrid[r][c+1].getStatus() || cGrid[r-1][c+1].getStatus(); 
    }
    // If cell is on the right edge
    else if (c == 3) {
      return cGrid[r+1][c].getStatus() || cGrid[r-1][c].getStatus() || cGrid[r+1][c-1].getStatus() || cGrid[r][c-1].getStatus() || cGrid[r-1][c-1].getStatus();
    }
    // Otherwise return false
    else {
      return false;
    }
  }

  /**
   * Determines whether or not a cell can be selected based on the previous selected cell
   * @param current: Current cell that mouse is on
   * @param previous: Previous cell that mouse is on
   * @return true or false of if a cell can be selected
   */
  public boolean canSelect(Cell current, Cell previous) {
    // Check if the current cell's collumn and row is 1 away from the previous cell's collumn and row number
    if (Math.abs(current.cVal - previous.cVal) != 1 && Math.abs(current.rVal - previous.rVal) != 1) {
      return false;
    }
    return true;
  }


  // Methods that help draw the game


  /**
   * Creates the game board (cells) when its called, does it only once even if it is called in draw()
   */
  private void createGameboard(){

    // Checking if boolean value is true
    if (blnCheckCreateGameboard) {

      // Variable tracks the order of cells in the 2d array
      int intCellCount = 0;

      // Intialize cell x and y coordinates to draw each cell (values change according to how many rows and columns there are)
      int intCellX;
      int intCellY;

      // Iterate through the 2d array to draw cell grid
      for (int r  = 0; r < intRowCount; r++) {
        for (int c = 0; c < intColCount; c++) {
          // Change colour to white if selected status of cell object is true
          if (cGrid[r][c].getStatus()) {
            intCellR = 255;
            intCellG = 255; 
            intCellB = 255;
          }
          // Otherwise, keep rgb at beige
          else {
            intCellR = 235;
            intCellG = 232; 
            intCellB = 207;
          }
          
          fill(intCellR, intCellG, intCellB);

          // Set cell x and y coordinates
          intCellX = intMargin + intMargin * c + intCellWidth * c + centerHoriz(intGameWidth + intGameWidth / 28) + intGameWidth / 56;
          intCellY = intMargin + intMargin * r + intCellHeight * r + centerVert(intGameHeight + intGameHeight / 28) + intGameHeight / 56;
          // Draw cells
          roundedRect(intCellX, intCellY, intCellWidth, intCellHeight);

          // Display word characters as they are being selected
          fill(0);
          textFont(calibri);
          textAlign(CENTER, CENTER);
          text(randomLetters.get(intCellCount), intCellX, intCellY, intCellWidth, intCellHeight);

          // Increment variable one each iteration
          intCellCount++;
        }
      }
    }
    // Change boolean value to false so method only runs once
    blnCheckCreateGameboard = false;
  }

  /** 
   * Method to determine the centering x-value of an element based on its width and the window width
   * @param intElementWidth: width of element that is being centered
   * @return the value that will center an element horizontally
   */
  public int centerHoriz(int intElementWidth) {
    return((intWindowWidth - intElementWidth) / 2);
  }

  /** 
   * Method to determine the centering y-value of an element based on its height and the window height
   * @param intElementHeight: width of element that is being centered
   * @return the value that will center an element vertically
   */
  public int centerVert(int intElementHeight) {
    return((intWindowHeight - intElementHeight) / 2);
  }

  /**
   * Creates a rounded rectangle (i was unaware of the rectangle radius value at this time)
   * @param x: x coordinate to draw rectangle
   * @param y: y coordinate to draw rectangle
   * @param width: width of rectangle
   * @param height: height of rectangle
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

  /**
   * Draws background of word search game (image + opaque rectangle that holds the cells)
   */
  public void drawGridBg() {
    image(background, 0, 0);
    fill(0, 100);
    rect((intWindowWidth - intGameWidth) / 2, (intWindowHeight - intGameHeight) / 2, intGameWidth, intGameHeight, 15);
  }

  // Cell class to store attributes of each cell in the grid
  class Cell {

    // Center coordinates of cells
    int intCellX;
    int intCellY;

    // Cell letter
    String strLetter;

    boolean isSelected;

    int intCellWidth; 
    int intCellHeight;

    // Column and row values of cell
    int rVal;
    int cVal;

    // Default constructor
    public Cell(){
      isSelected = false;
    }

    // Getter methods
    public int getCellX(){
      return intCellX;
    }

    public int getCellY(){
      return intCellY;
    }

    public String getLetter(){
      return strLetter;
    }

    public boolean getStatus() {
      return isSelected;
    }

    public int rVal() {
      return rVal;
    }

    public int cVal() {
      return cVal;
    }

    // Setter methods
    public void setLetter(String letter){
      this.strLetter = letter;
    }

    public void setCellDimensions(int intCellWidth, int intCellHeight){
      this.intCellWidth = intCellWidth;
      this.intCellWidth = intCellHeight;
    }

    public void setCellXY(int cellX, int cellY){
      this.intCellX = cellX;
      this.intCellY = cellY;
    }

    public void setStatus(boolean isSelected) {
      this.isSelected = isSelected;
    }

    public void setR(int r) {
      this.rVal = r;
    }

    public void setC(int c) {
      this.cVal = c;
    }
  }
}

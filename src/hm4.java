import java.util.Random;
import java.util.Scanner;

public class hm4 {

    static final int SIZE_Y = 3;
    static final int SIZE_X = 3;
    static final int SIZE_WIN = 3;
    static final char[][] fieldg =  new char [SIZE_Y][SIZE_X];

    static final char player_DOT = 'X';
    static final char AI_DOT = 'O';
    static final char EMPTY_DOT = '.';


    static Scanner scr = new Scanner(System.in);
    static Random rnd = new Random();
    public static void main(String[] args) {
        emptyField();
        printField();
        do {
            playerMove();
            System.out.println("Ваш ход на поле");
            printField();
            if (checkWin(player_DOT)) {
                System.out.println("Вы выиграли");
                break;
            } else if (fullField()) break;
            AiMove();
            System.out.println("Ход ИИ на поле");
            printField();
            if (checkWin(AI_DOT)) {
                System.out.println("Выиграл ИИ");
                break;
            } else if (fullField()) break;
        } while (true);
        System.out.println("!Конец игры!");
    }


    private static void emptyField () {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                fieldg [i][j] = EMPTY_DOT;
            }
        }
    }


    private static void printField ()     {
        printFieldLine ();
        for (int i = 0; i < SIZE_Y; i++) {
            System.out.print("|");
            for (int j = 0; j < SIZE_X; j++) {
                System.out.print(fieldg[i][j]+"|");
            }
            System.out.println("");
        }
        printFieldLine ();
    }

    private static void printFieldLine () {
        for (int i = 0; i < fieldg.length * 2 + 1; i++) {
            System.out.print("-");
        }
        System.out.println("");
    }

    private static void dotField (int y, int x, char dot) {
        fieldg [y][x] = dot;
    }

    private static void playerMove () {
        int x, y;
        do {
            System.out.printf("Введите координаты в формате X Y):\n", SIZE_X, SIZE_Y);
            x = Integer.valueOf(scr.next()) - 1;
            y = Integer.valueOf(scr.next()) - 1;
        } while (!checkMove(y, x));
        dotField(y, x, player_DOT);
    }

    private static void AiMove () {
        int x, y;

        for (int v = 0; v < SIZE_Y; v++) {
            for (int h = 0; h < SIZE_X; h++) {

                if (h + SIZE_WIN <= SIZE_X) {
                    if (checkHorisont(v, h, player_DOT) == SIZE_WIN - 1) {
                        if (MoveAiLineHorisont(v, h, AI_DOT)) return;
                    }

                    if (v - SIZE_WIN > -2) {
                        if (checkDiaUp(v, h, player_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaUp(v, h, AI_DOT)) return;
                        }
                    }
                    if (v + SIZE_WIN <= SIZE_Y) {
                        if (checkDiaDown(v, h, player_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaDown(v, h, AI_DOT)) return;
                        }
                    }
                }
                if (v + SIZE_WIN <= SIZE_Y) {
                    if (checkVertical(v, h, player_DOT) == SIZE_WIN - 1) {
                        if(MoveAiLineVertical(v, h, AI_DOT)) return;
                    }
                }
            }
        }

        for (int v = 0; v < SIZE_Y; v++) {
            for (int h = 0; h < SIZE_X; h++) {

                if (h + SIZE_WIN <= SIZE_X) {
                    if (checkHorisont(v, h, AI_DOT) == SIZE_WIN - 1) {
                        if (MoveAiLineHorisont(v, h, AI_DOT)) return;
                    }

                    if (v - SIZE_WIN > -2) {
                        if (checkDiaUp(v, h, AI_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaUp(v, h, AI_DOT)) return;
                        }
                    }
                    if (v + SIZE_WIN <= SIZE_Y) {
                        if (checkDiaDown(v, h, AI_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaDown(v, h, AI_DOT)) return;
                        }
                    }

                }
                if (v + SIZE_WIN <= SIZE_Y) {
                    if (checkVertical(v, h, AI_DOT) == SIZE_WIN - 1) {
                        if(MoveAiLineVertical(v, h, AI_DOT)) return;
                    }
                }
            }
        }


        do {
            y = rnd.nextInt(SIZE_Y);
            x = rnd.nextInt(SIZE_X);
        } while (!checkMove(y, x));
        dotField(y, x, AI_DOT);
    }


    private static boolean MoveAiLineHorisont(int v, int h, char dot) {
        for (int j = h; j < SIZE_WIN; j++) {
            if ((fieldg[v][j] == EMPTY_DOT)) {
                fieldg[v][j] = dot;
                return true;
            }
        }
        return false;
    }

    private static boolean MoveAiLineVertical(int v, int h, char dot) {
        for (int i = v; i < SIZE_WIN; i++) {
            if ((fieldg[i][h] == EMPTY_DOT)) {
                fieldg[i][h] = dot;
                return true;
            }
        }
        return false;
    }


    private static boolean MoveAiDiaUp(int v, int h, char dot) {
        for (int i = 0, j = 0; j < SIZE_WIN; i--, j++) {
            if ((fieldg[v + i][h + j] == EMPTY_DOT)) {
                fieldg[v + i][h + j] = dot;
                return true;
            }
        }
        return false;
    }


    private static boolean MoveAiDiaDown(int v, int h, char dot) {

        for (int i = 0; i < SIZE_WIN; i++) {
            if ((fieldg[i + v][i + h] == EMPTY_DOT)) {
                fieldg[i + v][i + h] = dot;
                return true;
            }
        }
        return false;
    }

    private static boolean checkMove(int y, int x) {
        if (x < 0 || x >= SIZE_X || y < 0 || y >= SIZE_Y) return false;
        else return fieldg[y][x] == EMPTY_DOT;

    }

    private  static boolean fullField() {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                if (fieldg[i][j] == EMPTY_DOT) return false;
            }
        }
        System.out.println("Игра закончилась в ничью");
        return true;
    }


    private static boolean checkWin(char dot) {
        for (int v = 0; v < SIZE_Y; v++){
            for (int h = 0; h < SIZE_X; h++) {

                if (h + SIZE_WIN <= SIZE_X) {
                    if (checkHorisont(v, h, dot) >= SIZE_WIN) return true;

                    if (v - SIZE_WIN > -2) {
                        if (checkDiaUp(v, h, dot) >= SIZE_WIN) return true;
                    }
                    if (v + SIZE_WIN <= SIZE_Y) {
                        if (checkDiaDown(v, h, dot) >= SIZE_WIN) return true;
                    }
                }
                if (v + SIZE_WIN <= SIZE_Y) {
                    if (checkVertical(v, h, dot) >= SIZE_WIN) return true;
                }
            }
        }
        return false;
    }



    private static int checkDiaUp(int v, int h, char dot) {
        int count=0;
        for (int i = 0, j = 0; j < SIZE_WIN; i--, j++) {
            if ((fieldg[v+i][h+j] == dot)) count++;
        }
        return count;
    }


    private static int checkDiaDown(int v, int h, char dot) {
        int count=0;
        for (int i = 0; i < SIZE_WIN; i++) {
            if ((fieldg[i+v][i+h] == dot)) count++;
        }
        return count;
    }

    private static int checkHorisont(int v, int h, char dot) {
        int count=0;
        for (int j = h; j < SIZE_WIN + h; j++) {
            if ((fieldg[v][j] == dot)) count++;
        }
        return count;
    }

    private static int checkVertical(int v, int h, char dot) {
        int count=0;
        for (int i = v; i< SIZE_WIN + v; i++) {
            if ((fieldg[i][h] == dot)) count++;
        }
        return count;
    }


}
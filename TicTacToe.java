/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tictactoe;

import java.util.Scanner;
import java.util.Random;

public class TicTacToe {
    static int[][] board = new int[3][3]; // 0 = kosong, 1 = X (user), -1 = O (komputer)
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("Selamat datang di Tic Tac Toe!");
        int turn = batuGuntingKertas(); // 1 = user, -1 = komputer
        printBoard();

        while (true) {
            if (turn == 1) {
                userMove();
                printBoard();
                if (checkWin(1)) {
                    System.out.println("Kamu menang!");
                    break;
                }
            } else {
                computerMove();
                printBoard();
                if (checkWin(-1)) {
                    System.out.println("Komputer menang!");
                    break;
                }
            }

            if (isFull()) {
                System.out.println("Permainan seri.");
                break;
            }

            turn = -turn; // gantian
        }
    }

    static int batuGuntingKertas() {
        String[] pilihan = {"batu", "gunting", "kertas"};
        while (true) {
            System.out.print("Pilih batu, gunting, atau kertas: ");
            String user = scanner.next().toLowerCase();
            String komputer = pilihan[random.nextInt(3)];
            System.out.println("Komputer memilih: " + komputer);

            if (user.equals(komputer)) {
                System.out.println("Seri! Ulangi lagi.\n");
                continue;
            }

            boolean userMenang =
                (user.equals("batu") && komputer.equals("gunting")) ||
                (user.equals("gunting") && komputer.equals("kertas")) ||
                (user.equals("kertas") && komputer.equals("batu"));

            if (userMenang) {
                System.out.println("Kamu menang suit! Kamu jalan duluan.\n");
                return 1;
            } else {
                System.out.println("Komputer menang suit! Komputer jalan duluan.\n");
                return -1;
            }
        }
    }

    static void userMove() {
        int row, col;
        while (true) {
            System.out.println("Giliranmu (X). Masukkan baris dan kolom (0-2):");
            System.out.print("Baris: ");
            row = scanner.nextInt();
            System.out.print("Kolom: ");
            col = scanner.nextInt();
            if (isValid(row, col)) {
                board[row][col] = 1;
                break;
            } else {
                System.out.println("Langkah tidak valid. Coba lagi.");
            }
        }
    }

    static void computerMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1, bestCol = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = -1;
                    int score = minimax(0, false);
                    board[i][j] = 0;
                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = i;
                        bestCol = j;
                    }
                }
            }
        }
        board[bestRow][bestCol] = -1;
        System.out.println("Komputer memilih baris " + bestRow + " kolom " + bestCol);
    }

    static int minimax(int depth, boolean isMaximizing) {
        if (checkWin(-1)) return 10 - depth;
        if (checkWin(1)) return depth - 10;
        if (isFull()) return 0;

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = -1;
                        best = Math.max(best, minimax(depth + 1, false));
                        board[i][j] = 0;
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = 1;
                        best = Math.min(best, minimax(depth + 1, true));
                        board[i][j] = 0;
                    }
                }
            }
            return best;
        }
    }

    static boolean isValid(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == 0;
    }

    static boolean isFull() {
        for (int[] row : board) {
            for (int val : row) {
                if (val == 0) return false;
            }
        }
        return true;
    }

    static boolean checkWin(int player) {
        int target = player * 3;
        for (int i = 0; i < 3; i++) {
            if (board[i][0] + board[i][1] + board[i][2] == target) return true;
            if (board[0][i] + board[1][i] + board[2][i] == target) return true;
        }
        if (board[0][0] + board[1][1] + board[2][2] == target) return true;
        if (board[0][2] + board[1][1] + board[2][0] == target) return true;
        return false;
    }

    static void printBoard() {
        System.out.println("Papan permainan:");
        for (int[] row : board) {
            for (int val : row) {
                if (val == 1) System.out.print(" X ");
                else if (val == -1) System.out.print(" O ");
                else System.out.print(" . ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

package com.example.todofromscratch.game;

import java.util.Random;



public class Map {

    private int[][] map;

    public Map() {
        map = new int[10][10];

        // Randomly fill the map
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int randNum = random.nextInt(3);
                map[i][j] = randNum;
            }
        }
    }

    // Override toString for the map
    @Override
    public String toString() {
        StringBuilder tempStr = new StringBuilder();
        for (int[] row : map) {
            for (int cell : row) {
                tempStr.append(cell).append(" ");
            }
            tempStr.append("\n");
        }
        return tempStr.toString();
    }

    public GameEnum.Terrain move(int x, int y) {
        if (x < 0 || x > 9 || y < 0 || y > 9) {
            return GameEnum.Terrain.INVALID;
        } else {
            return GameEnum.Terrain.FOREST;
        }
    }

    public String getSurrounding(int x, int y) {
        StringBuilder surrounding = new StringBuilder();
        for (int x_row = -3; x_row <= 3; x_row++) {
            for (int y_col = -3; y_col <= 3; y_col++) {

                if (x_row + x < 0 || x_row + x > 9 || y_col + y < 0 || y_col + y > 9) {
                    continue;
                } else {
                    surrounding.append(map[x_row + x][y_col + y]).append(" ");
                }
            }
            surrounding.append('\n');
        }
        return surrounding.toString();
    }

    public static void main(String[] args) {
        Map map = new Map();
        System.out.println(map);
        System.out.println(map.getSurrounding(5, 5));
        System.out.println(map.move(5, 5));
        System.out.println(map.move(10, 10));
        System.out.println(map.move(-1, -1));
        System.out.println(map.move(0, 0));
        System.out.println(map.move(9, 9));
        System.out.println(map.move(0, 9));
        System.out.println(map.move(9, 0));
    }
}

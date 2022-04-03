package com.sedlacek.ld50.main;

public class Config {

    public static final long FIXED_UPDATE_INTERVAL = 1000;
    public static int SIZE_MULT = 3;
    public static int P_MULT = SIZE_MULT*2;

    public static int WIDTH = 240*SIZE_MULT;
    public static int HEIGHT = 240*SIZE_MULT;
    public static int X_MID = WIDTH/2;
    public static int Y_MID = HEIGHT/2;
    public static double fps = 60.0D;
    
    public static enum Direction {
    	UP,
    	DOWN,
    	LEFT,
    	RIGHT
    }
    
    public static final int TILE_SIZE = 16;

    public static String NAME = "Dungeon Escape - Ludum Dare #50 Compo";

    public static boolean showInfo = false;

    public static boolean running;

    public static boolean debugMode = false;

    public static void debug(String ... msg){
        if(debugMode){
            System.out.print("DEBUG: ");
            for(String i: msg){
                System.out.print(i);
            }
            System.out.println();
        }
    }
}

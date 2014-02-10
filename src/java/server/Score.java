/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.util.ArrayList;

/**
 *
 * @author bigcompy
 */
public class Score {
    int amount;
    int team;
    int blocks;
    int throwes;
    int catches;
    int passes;
    int match;
    int autoScore;
    String functioning;
    String alliance;
    ArrayList<int[]> positions;
    
    public void setTeam(int t, int m, String a) {
        team = t;
        amount = 0;
        positions = new ArrayList<int[]>();
        match = m;
        alliance = a;
        autoScore = 0;
    }
    
    public int getTeam() {
        return team;
    }
    
    public int getScore() {
        return amount;
    }
    
    public int getBlocks() {
        return blocks;
    }
    
    public int getThrows() {
        return throwes;
    }
    
    public int getCatches() {
        return catches;
    }
    
    public int getPasses() {
        return passes;
    }
    
    public int[] getPositions(int i) {
        return positions.get(i);
    }
    
    public void addBlock() {
        blocks++;
    }
    
    public void addThrow() {
        amount+=10;
        throwes++;
    }
    
    public void addCatch() {
        amount+=10;
        catches++;
    }
    
    public void addPass() {
        passes++;
    }
    
    public void addShot(int x, int y, int s) {
        positions.add(new int[]{x,y,s});
        
        if(s != -1)
            amount += s;
    }
    
    public int getShots() {
        return positions.size();
    }
    
    public void setStart(int x, int y) {
        positions.add(0,new int[]{x,y});
    }
    
    public int getMatch() {
        return match;
    }
    
    public void setAlliance(String s) {
        alliance = s;
    }
    
    public String getAlliance() {
        return alliance;
    }
    
    public void setAuto(int i) {
        autoScore = i;
    }
    
    public void setFunctioning(String f) {
        functioning = f;
    }
    
    public int getAuto() {
        return autoScore;
    } 
    
    public String getFunction() {
        return functioning;
    }
}

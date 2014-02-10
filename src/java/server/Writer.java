/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author bigcompy
 */
public class Writer {
    WritableWorkbook workBook;
    WritableSheet excelSheet;
    WritableSheet shotSheet;
    WritableCellFormat times;
    public Writer() throws IOException, WriteException, BiffException {
        WorkbookSettings wbSettings = new WorkbookSettings();
        
        wbSettings.setLocale(new Locale("en", "EN"));
        
        Workbook wb = Workbook.getWorkbook(new File("C:/ScoutingDoc.xls"));
        workBook = Workbook.createWorkbook(new File("C:/ScoutingDoc.xls"), wb);
        excelSheet = workBook.getSheet(0);
        shotSheet = workBook.getSheet(1);
        
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
        times = new WritableCellFormat(times10pt);
    }
    
    public void writeColumn(String column) throws WriteException {
        String[] s = column.split(",");
        
        excelSheet.addCell(new Label(0, 0, "Target", times));
        excelSheet.addCell(new Label(1, 0, "Location", times));
        excelSheet.addCell(new Label(2, 0, "Hit", times));
        
        for(int i = 0; i < s.length; i++)
            excelSheet.addCell(new Label(i%3, i/3+1, s[i], times));
    }
    
    public void close() throws IOException, WriteException {
        workBook.write();
        workBook.close();
    }
    
    public void changeValue(int minCol, int totalCol, int maxCol, int row, int val) throws WriteException {
        if(val < Integer.parseInt(excelSheet.getCell(minCol, row).getContents()))
            excelSheet.addCell(new Label(minCol,row,""+val,times));
        
        if(val > Integer.parseInt(excelSheet.getCell(maxCol, row).getContents()))
            excelSheet.addCell(new Label(maxCol,row,""+val,times));
        
        excelSheet.addCell(new Label(totalCol,row,""+(Integer.parseInt(excelSheet.getCell(totalCol, row).getContents())+val),times));
    }
    
    public void changeValue(int totalCol, int row, int val) throws WriteException {
        excelSheet.addCell(new Label(totalCol,row,""+(Integer.parseInt(excelSheet.getCell(totalCol, row).getContents())+val),times));
    }
    
    public void write(Score[] sc) throws WriteException {
        System.out.println("Finishing");
        Score s;
        for(int t = 0; t < 6; t++) {
            s = sc[t];
            int i = 0;
            int team = s.getTeam();
            while(excelSheet.getCell(0, i).getContents() != "")
                i++;

            excelSheet.addCell(new Number(0,i,team));
            excelSheet.addCell(new Number(1,i,s.getMatch()));
            excelSheet.addCell(new Label(2,i,s.getAlliance(),times));
            try{
                excelSheet.addCell(new Number(3,i,s.getPositions(0)[0]));
                excelSheet.addCell(new Number(4,i,s.getPositions(0)[1]));
            }catch(NullPointerException e) {}
            excelSheet.addCell(new Number(5,i,s.getAuto()));
            excelSheet.addCell(new Number(6,i,s.getScore()));
            excelSheet.addCell(new Number(7,i,s.getBlocks()));
            excelSheet.addCell(new Number(8,i,s.getPasses()));
            excelSheet.addCell(new Number(9,i,s.getCatches()));
            excelSheet.addCell(new Number(10,i,s.getThrows()));
            excelSheet.addCell(new Label(11,i,s.getFunction(),times));

            i=0;
            while(shotSheet.getCell(0, i).getContents() != "")
                i++;
            
            try{
                for(int x = 1; x < s.getShots(); x++) {
                    try{
                    int[] shot = s.getPositions(x);
                    shotSheet.addCell(new Number(0, i+x-1, team));
                    shotSheet.addCell(new Number(1, i+x-1, shot[0]));
                    shotSheet.addCell(new Number(2, i+x-1, shot[1]));
                    shotSheet.addCell(new Number(3, i+x-1, shot[2]));
                    }catch(ArrayIndexOutOfBoundsException e) {

                    }
                }
            }catch(NullPointerException e){}
        }
    }
}
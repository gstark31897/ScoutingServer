/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

/**
 *
 * @author bigcompy
 */
@WebServlet(name = "form", urlPatterns = {"/form"})
public class form extends HttpServlet {
    static Score[] scores;
    int finished = 0;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, WriteException, BiffException {
            request.setAttribute("goal", "somethingnotthere");
            System.out.println(request.getParameter("teamNumber") + ":" + request.getParameter("matchNumber") + ":" + request.getParameter("alliance"));
            
            int team = Integer.parseInt(request.getParameter("teamNumber"));
            boolean b = false;
            
            if(scores == null) {
                scores = new Score[6];
                scores[0] = new Score();
                scores[1] = new Score();
                scores[2] = new Score();
                scores[3] = new Score();
                scores[4] = new Score();
                scores[5] = new Score();
            }
            
            for(int i = 0; i < 6; i++) {
                if(scores[i].getTeam() == team)
                    b = true;
            }
            
            if(!b) {
                for(int i = 0; i < 6 && !b; i++) {
                    if(scores[i].getTeam() == 0) {
                        scores[i].setTeam(team,Integer.parseInt(request.getParameter("matchNumber")),request.getParameter("alliance"));
                        b = true;
                    }
                }
            } 
            
            if(!b) {
                System.out.println("More Than 6 Are Scouting!");
            }else{
                System.out.println("WORKING...");
                if(request.getParameter("finish") != null) {
                    System.out.println("Match Finished!");
                    for(int i = 0; i < 6; i++) {
                        if(scores[i].getTeam() == team) {
                            scores[i].setStart(Integer.parseInt(request.getParameter("startP").split(":")[0]),Integer.parseInt(request.getParameter("startP").split(":")[1]));
                            scores[i].setAlliance(request.getParameter("alliance"));
                            scores[i].setAuto(Integer.parseInt(request.getParameter("autoScore")));
                            scores[i].setFunctioning(request.getParameter("f"));
                            finished++;
                        }
                    }
                    
                    if(finished == 6) {
                        Writer writer = new Writer();
                        writer.write(scores);
                        writer.close();
                        finished = 0;
                        
                        scores = new Score[6];
                        scores[0] = new Score();
                        scores[1] = new Score();
                        scores[2] = new Score();
                        scores[3] = new Score();
                        scores[4] = new Score();
                        scores[5] = new Score();
                    }
                }else if(request.getParameter("block") != null) {
                    System.out.println("BLOCK!");
                    for(int i = 0; i < 6; i++) {
                        if(scores[i].getTeam() == team)
                            scores[i].addBlock();
                    }
                }else if(request.getParameter("coop") != null) {
                    System.out.println("COOP!");
                    for(int i = 0; i < 6; i++) {
                        if(scores[i].getTeam() == team) {
                            String coop = request.getParameter("coop");
                            if(coop.equals("throw"))
                                scores[i].addThrow();
                            else if(coop.equals("catch"))
                                scores[i].addCatch();
                            else
                                scores[i].addPass();
                        }
                    }
                }else{
                    System.out.println("SHOT!");
                    for(int i = 0; i < 6; i++) {
                        if(scores[i].getTeam() == team) {
                            int score = 0;
                            String goal = request.getParameter("goal");
                            if(request.getParameter("hit").equals("hit"))
                                if(goal.contains("bottom"))
                                    score = 1;
                                else
                                    score = 10;
                            else if(request.getParameter("hit").equals("blocked"))
                                score = -1;
                            
                            scores[i].addShot(Integer.parseInt(request.getParameter("pos").split(":")[0]), Integer.parseInt(request.getParameter("pos").split(":")[1]), score);
                        }
                    }
                }
            }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (WriteException ex) {
            Logger.getLogger(form.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(form.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (WriteException ex) {
            Logger.getLogger(form.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(form.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redcats.tst.servlet.testCase;

import com.redcats.tst.database.DatabaseSpring;
import com.redcats.tst.exception.CerberusException;
import com.redcats.tst.factory.IFactoryLogEvent;
import com.redcats.tst.factory.impl.FactoryLogEvent;
import com.redcats.tst.log.MyLogger;
import com.redcats.tst.service.ILogEventService;
import com.redcats.tst.service.impl.LogEventService;
import com.redcats.tst.service.impl.UserService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author bcivel
 */
@WebServlet(name = "DeleteTestCase", urlPatterns = {"/DeleteTestCase"})
public class DeleteTestCase extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        DatabaseSpring database = appContext.getBean(DatabaseSpring.class);

        Connection connection = database.connect();
        try {
            /*
             * Test Insert
             */
            String testcasedeleted[] = request.getParameterValues("test_testcase_delete");
            String test = "";
            PreparedStatement prepStmt;

            if (testcasedeleted != null) {

                for (int i = 0; i < testcasedeleted.length; i++) {
                    String testcasesplited[] = testcasedeleted[i].split(" - ");
                    test = testcasesplited[0];

                    String deleteTestCaseExecutionStatement = "DELETE FROM TestCaseExecution WHERE Test = ? AND testcase = ? ";
                    prepStmt = connection.prepareStatement(deleteTestCaseExecutionStatement);
                    try {
                        prepStmt.setString(1, testcasesplited[0]);
                        prepStmt.setString(2, testcasesplited[1]);
                        prepStmt.executeUpdate();
                    } finally {
                        prepStmt.close();
                    }

                    String deleteTestCasestepactioncontrolStatement = "DELETE FROM TestCasestepactioncontrol WHERE Test = ? AND testcase = ? ";
                    prepStmt = connection.prepareStatement(deleteTestCasestepactioncontrolStatement);
                    try {
                        prepStmt.setString(1, testcasesplited[0]);
                        prepStmt.setString(2, testcasesplited[1]);
                        prepStmt.executeUpdate();
                    } finally {
                        prepStmt.close();
                    }
                    //stmt.execute("DELETE FROM TestCasestepactioncontrol where Test = '" + testcasesplited[0] + "'" + " and testcase = '" + testcasesplited[1] + "'");

                    String deleteTestCasestepactionStatement = "DELETE FROM TestCasestepaction WHERE Test = ? AND testcase = ? ";
                    prepStmt = connection.prepareStatement(deleteTestCasestepactionStatement);
                    try {
                        prepStmt.setString(1, testcasesplited[0]);
                        prepStmt.setString(2, testcasesplited[1]);
                        prepStmt.executeUpdate();
                    } finally {
                        prepStmt.close();
                    }
                    //stmt.execute("DELETE FROM TestCasestepaction where Test = '" + testcasesplited[0] + "'" + " and testcase = '" + testcasesplited[1] + "'");

                    String deleteTestCasestepbatchStatement = "DELETE FROM TestCasestepbatch WHERE Test = ? AND testcase = ? ";
                    prepStmt = connection.prepareStatement(deleteTestCasestepbatchStatement);
                    try {
                        prepStmt.setString(1, testcasesplited[0]);
                        prepStmt.setString(2, testcasesplited[1]);
                        prepStmt.executeUpdate();
                    } finally {
                        prepStmt.close();
                    }
                    //stmt.execute("DELETE FROM TestCasestepbatch where Test = '" + testcasesplited[0] + "'" + " and testcase = '" + testcasesplited[1] + "'");

                    String deleteTestCasecountrypropertiesStatement = "DELETE FROM TestCasecountryproperties WHERE Test = ? AND testcase = ? ";
                    prepStmt = connection.prepareStatement(deleteTestCasecountrypropertiesStatement);
                    try {
                        prepStmt.setString(1, testcasesplited[0]);
                        prepStmt.setString(2, testcasesplited[1]);
                        prepStmt.executeUpdate();
                    } finally {
                        prepStmt.close();
                    }
                    //stmt.execute("DELETE FROM TestCasecountryproperties where Test = '" + testcasesplited[0] + "'" + " and testcase = '" + testcasesplited[1] + "'");

                    String deleteTestCasecountryStatement = "DELETE FROM TestCasecountry WHERE Test = ? AND testcase = ? ";
                    prepStmt = connection.prepareStatement(deleteTestCasecountryStatement);
                    try {
                        prepStmt.setString(1, testcasesplited[0]);
                        prepStmt.setString(2, testcasesplited[1]);
                        prepStmt.executeUpdate();
                    } finally {
                        prepStmt.close();
                    }
                    //stmt.execute("DELETE FROM TestCasecountry where Test = '" + testcasesplited[0] + "'" + " and testcase = '" + testcasesplited[1] + "'");

                    String deleteTestCaseStatement = "DELETE FROM TestCase WHERE Test = ? AND testcase = ? ";
                    prepStmt = connection.prepareStatement(deleteTestCaseStatement);
                    try {
                        prepStmt.setString(1, testcasesplited[0]);
                        prepStmt.setString(2, testcasesplited[1]);
                        prepStmt.executeUpdate();
                    } finally {
                        prepStmt.close();
                    }
                    //stmt.execute("DELETE FROM TestCase where Test = '" + testcasesplited[0] + "'" + " and testcase = '" + testcasesplited[1] + "'");

                    /**
                     * Adding Log entry.
                     */
                    ILogEventService logEventService = appContext.getBean(LogEventService.class);
                    IFactoryLogEvent factoryLogEvent = appContext.getBean(FactoryLogEvent.class);
                    try {
                        logEventService.insertLogEvent(factoryLogEvent.create(0, 0, request.getUserPrincipal().getName(), null, "/DeleteTestCase", "DELETE", "Delete testcase : ['" + testcasesplited[0] + "'|'" + testcasesplited[1] + "']", "", ""));
                    } catch (CerberusException ex) {
                        Logger.getLogger(UserService.class.getName()).log(Level.ERROR, null, ex);
                    }


                }
            }

            response.sendRedirect("Test.jsp?stestbox=" + test);


        } catch (SQLException ex) {
            MyLogger.log(DeleteTestCase.class.getName(), Level.FATAL, ex.toString());
        } finally {
            out.close();
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                MyLogger.log(DeleteTestCase.class.getName(), Level.WARN, e.toString());
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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

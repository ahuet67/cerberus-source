/*
 * Cerberus  Copyright (C) 2013  vertigo17
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This file is part of Cerberus.
 *
 * Cerberus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Cerberus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Cerberus.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.cerberus.servlet.documentation;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.cerberus.crud.entity.Documentation;
import org.cerberus.log.MyLogger;
import org.cerberus.crud.service.IDocumentationService;
import org.cerberus.util.ParameterParserUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@WebServlet(name = "ReadDocumentation", urlPatterns = {"/ReadDocumentation"})
public class ReadDocumentation extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse response) throws ServletException, IOException {
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        IDocumentationService docService = appContext.getBean(IDocumentationService.class);
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
        JSONObject jsonResponse = new JSONObject();
        List<Documentation> result = new ArrayList<Documentation>();
        JSONObject format = new JSONObject();

        String lang = ParameterParserUtil.parseStringParam(policy.sanitize(httpServletRequest.getParameter("lang")), "en");

        result = docService.findAllWithEmptyDocLabel(lang);
        format = docService.formatGroupByDocTable(result);  
        try {
            jsonResponse.put("labelTable", format);
        } catch (JSONException ex) {
            Logger.getLogger(ReadDocumentation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        response.setContentType("application/json");
        response.getWriter().print(jsonResponse.toString());
    }
    
    private JSONObject convertDocToJSONObject(Documentation doc) throws JSONException {

        Gson gson = new Gson();
        JSONObject result = new JSONObject(gson.toJson(doc));
        return result;
    }
}
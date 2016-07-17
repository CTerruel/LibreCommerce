/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.filtro;

import br.com.librecommerce.modelo.Funcionario;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Clovis
 */
@WebFilter(filterName = "AppAutorizacao", urlPatterns = {"/faces/app/cadastros/*"})
public class AppAutorizacao implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {}
    
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        
        Funcionario funcionario = (Funcionario) session.getAttribute("login");
        
        if (funcionario.isAdmin()) {
            //resp.sendRedirect(req.getContextPath() + "/faces/seguranca/login.xhtml");
            chain.doFilter(request, response);
        }
        else {
            resp.sendRedirect(req.getContextPath() + "/faces/app/pageError/pageError.xhtml");
            //chain.doFilter(request, response);
        }
        
    }

    @Override
    public void destroy() {}

}

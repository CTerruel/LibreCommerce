/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.filtro;

import br.com.librecommerce.modelo.Funcionario;
import java.io.IOException;
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
@WebFilter(filterName = "AppAutenticacao", urlPatterns = {"/faces/app/*"})
public class AppAutenticacao implements Filter {
    
    
    public AppAutenticacao() {}    

    public void init(FilterConfig filterConfig) {}
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        
        Funcionario funcionario = (Funcionario) session.getAttribute("login");
        
        if (funcionario == null) {
            resp.sendRedirect(req.getContextPath() + "/faces/seguranca/login.xhtml");
        }
        else {
            chain.doFilter(request, response);
        }
        
    }
    
    public void destroy() {}

}

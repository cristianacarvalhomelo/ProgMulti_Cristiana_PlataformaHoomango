//package com.hoomango.util;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebFilter("/*")
//public class AuthFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//
//        String urlAcessada = req.getRequestURI();
//        String contextPath = req.getContextPath();
//
//        boolean isPublica =
//                urlAcessada.startsWith(contextPath + "/home.xhtml")
//                        || urlAcessada.startsWith(contextPath + "/login.xhtml")
//                        || urlAcessada.startsWith(contextPath + "/cadastroTutor.xhtml")
//                        || urlAcessada.startsWith(contextPath + "/cadastroCuidador.xhtml")
//                        || urlAcessada.contains("javax.faces.resource");
//
//        Object emailUsuarioLogado = req.getSession().getAttribute("emailUsuarioLogado");
//
//        if (!isPublica && emailUsuarioLogado == null) {
//            res.sendRedirect(contextPath + "/login.xhtml");
//            return;
//        }
//
//        chain.doFilter(request, response);
//    }
//}
//

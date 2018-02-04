/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

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
import modelo.Usuario;


//@WebFilter("*.xhtml")
public class Autorizacaofilter implements Filter {

    private static final String[][] DIREITO_ACESSO = {
        {"COMUM",
            "ProjetoPOO/faces/login.xhtml",
            "/ProjetoPOO/faces/p2.xhtml",
            "/ProjetoPOO/faces/manutencao/manutencaoItem.xhtml",
            "/ProjetoPOO/faces/manutencao/manutencaoComentario.xhtml"},
    
        {"ADMIN",
            "/faces/login.xhtml",
            "/ProjetoPOO/faces/p1.xhtml",
            "/ProjetoPOO/faces/manutencao/manutencaoUsuario.xhtml",
            "/ProjetoPOO/faces/manutencao/manutencaoItem.xhtml",
            "/ProjetoPOO/faces/manutencao/manutencaoTipoItem.xhtml",
            "/ProjetoPOO/faces/manutencao/manutencaoClassificacao.xhtml",
            "/ProjetoPOO/faces/manutencao/manutencaoComentario.xhtml"}
    };

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        Usuario usu = null;

        HttpSession sess = ((HttpServletRequest) req).getSession();

        if (sess != null) {
            usu = (Usuario) sess.getAttribute("usuarioLogado");

        }

        if ((usu == null)
                && !request.getRequestURI().endsWith("/faces/login.xhtml")
                && !request.getRequestURI().contains("/javax.faces.resource/")) {
            response.sendRedirect(request.getContextPath() + "/faces/login.xhtml");
        } else {
            try {
                boolean foi = false;
                if (usu.getTipoUsuario().equals("COMUM")) {
                    for (int i = 1; i < DIREITO_ACESSO[0].length; i++) {
                        if (request.getRequestURI().endsWith(DIREITO_ACESSO[0][i])) {
                            chain.doFilter(req, res);
                            foi = false;
                            break;
                        } else {
                            foi = true;
                        }
                    }
                    if (foi) {
                        response.sendRedirect(request.getContextPath() + "/faces/login.xhtml");
                    }
                } else if (usu.getTipoUsuario().equals("ADMIN")) {
                    for (int i = 1; i < DIREITO_ACESSO[1].length; i++) {
                        if (request.getRequestURI().endsWith(DIREITO_ACESSO[1][i])) {
                            chain.doFilter(req, res);
                            foi = false;
                            break;
                        } else {
                            foi = true;
                        }
                    }
                    if (foi) {
                        response.sendRedirect(request.getContextPath() + "/faces/manutencaoClassificacao.xhtml");
                    }

                }
            } catch (NullPointerException e) {
                chain.doFilter(req, res);
            }

        }

    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}


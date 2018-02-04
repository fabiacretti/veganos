package controle;

import dao.UsuarioDAO;
import dao.ClassificacaoDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Usuario;
import modelo.Classificacao;
import org.primefaces.context.RequestContext;
import util.UtilInterface;
import javax.faces.context.FacesContext;
import javax.faces.bean.SessionScoped;
import util.SessionContext;
import javax.faces.application.FacesMessage;

@ManagedBean
@SessionScoped
public class UsuarioControle {

    private List<Usuario> Usuarios = new ArrayList<Usuario>();
    private Usuario usuario = new Usuario();
    private boolean salvar = false;
    private int idClassificacao=0;
    
  //  public void login() {
  //      try {
  //          Usuario usuarioLogado = UsuarioDAO.verificaLogin(usuario);
  //          if (usuarioLogado == null) {
  //             System.out.println("usuário não encontrado");
  //          } else {
  //              System.out.println("usuário encontrado");
  //              System.out.println("NOME: " + usuarioLogado.getNome());
  //          }
            
    public String login() {
        Usuario usuarioLogado = null;
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            usuarioLogado = UsuarioDAO.verificaLogin(usuario);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (usuarioLogado != null) {

            if (usuarioLogado.getTipoUsuario().equals("ADMIN")) {
                SessionContext.getInstance().setAttribute("usuarioLogado", usuarioLogado);
                return "/faces/p1.xhtml?faces-redirect=true";

            } else if (usuarioLogado.getTipoUsuario().equals("comum")) {
                SessionContext.getInstance().setAttribute("usuarioLogado", usuarioLogado);
                return "/faces/p2.xhtml?faces-redirect=true";

            } else {
                usuarioLogado = null;
                doLogout();
                return "/faces/login.xhtml?faces-redirect=true";
            }
        } else {
            usuarioLogado = null;
            usuario = new Usuario();
            doLogout();
            FacesMessage mensagem = new FacesMessage("Email/senha inválidos!");
            mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, mensagem);

            return null;
        }
    }

    public String doLogout() {
        SessionContext.getInstance().encerrarSessao();
        return "/faces/login.xhtml?faces-redirect=true";
    }          

    public int getIdClassificacao() {
        return idClassificacao;
    }

    public void setIdClassificacao(int idClassificacao) {
        this.idClassificacao = idClassificacao;
    }
    
    @PostConstruct
    public void atualizaUsuarios() {
        try {
            Usuarios = UsuarioDAO.getLista();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void preparaIncluir() {
        salvar = true;
        usuario = new Usuario();
    }

    public void preparaAlterar() {
        salvar = false;
    }

    public void salvar() {
        try {
            Classificacao c = new Classificacao();
            c.setIdClassificacao(idClassificacao);
            usuario.setClassificacao(c);
            if (salvar) {
                UsuarioDAO.inserir(usuario);

            } else {
                UsuarioDAO.alterar(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        atualizaUsuarios();
    }

    public void excluir() {
        try {
            UsuarioDAO.excluir(usuario);
            atualizaUsuarios();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> getUsuarios() {
        return Usuarios;
    }

    public void setUsuarios(List<Usuario> Usuarios) {
        this.Usuarios = Usuarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
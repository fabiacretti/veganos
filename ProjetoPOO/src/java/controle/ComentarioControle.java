package controle;

import dao.ComentarioDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Comentario;
import modelo.Usuario;
import modelo.Item;
import org.primefaces.context.RequestContext;
import util.UtilInterface;

@ManagedBean
@SessionScoped
public class ComentarioControle {

    private List<Comentario> Comentarios = new ArrayList<Comentario>();
    private Comentario Comentario = new Comentario();
    private boolean salvar = false;
    private int idUsuario=0;
    private int idItem=0;

    public int getUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }
     
    @PostConstruct
    public void atualizaComentarios() {
        try {
            Comentarios = ComentarioDAO.getLista();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void preparaIncluir() {
        salvar = true;
        Comentario = new Comentario();
    }

    public void preparaAlterar() {
        salvar = false;
    }

    public void salvar() {
        try {
            Usuario u= new Usuario();
            Item it= new Item();
            u.setIdUsuario(idUsuario);
            it.setIdItem(idItem);
            Comentario.setUsuario(u);
            Comentario.setItem(it);
            if (salvar) {
                ComentarioDAO.inserir(Comentario);

            } else {
                ComentarioDAO.alterar(Comentario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        atualizaComentarios();
    }

    public void excluir() {
        try {
            ComentarioDAO.excluir(Comentario);
            atualizaComentarios();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Comentario> getComentarios() {
        return Comentarios;
    }

    public void setComentarios(List<Comentario> Comentarios) {
        this.Comentarios = Comentarios;
    }

    public Comentario getComentario() {
        return Comentario;
    }

    public void setComentario(Comentario Comentario) {
        this.Comentario = Comentario;
    }

}

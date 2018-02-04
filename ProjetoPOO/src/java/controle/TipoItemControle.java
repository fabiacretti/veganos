package controle;

import dao.TipoItemDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.TipoItem;
import org.primefaces.context.RequestContext;
import util.UtilInterface;

@ManagedBean
@SessionScoped
public class TipoItemControle {

    private List<TipoItem> TipoItens = new ArrayList<TipoItem>();
    private TipoItem TipoItem = new TipoItem();
    private boolean salvar = false;

    public boolean isSalvar() {
        return salvar;
    }

    public void setSalvar(boolean salvar) {
        this.salvar = salvar;
    }

    @PostConstruct
    public void atualizaTipoItens() {
        try {
            TipoItens = TipoItemDAO.getLista();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void preparaIncluir() {
        salvar = true;
        TipoItem = new TipoItem();
    }

    public void preparaAlterar() {
        salvar = false;
    }

    public void salvar() {
        try {
            if (salvar) {
                TipoItemDAO.inserir(TipoItem);

            } else {
                TipoItemDAO.alterar(TipoItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        atualizaTipoItens();
    }

    public void excluir() {
        try {
            TipoItemDAO.excluir(TipoItem);
            atualizaTipoItens();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TipoItem> getTipoItens() {
        return TipoItens;
    }

    public void setTipoItens(List<TipoItem> TipoItens) {
        this.TipoItens = TipoItens;
    }

    public TipoItem getTipoItem() {
        return TipoItem;
    }

    public void setTipoItem(TipoItem TipoItem) {
        this.TipoItem = TipoItem;
    }

}

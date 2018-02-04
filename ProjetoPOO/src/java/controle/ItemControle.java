 package controle;

import dao.ItemDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Item;
import modelo.Usuario;
import modelo.TipoItem;
import modelo.Classificacao;
import org.primefaces.context.RequestContext;
import util.UtilInterface;

@ManagedBean
@SessionScoped
public class ItemControle {

    private List<Item> itens = new ArrayList<Item>();
    private Item item = new Item();
    private boolean salvar = false;
    private int idTipoItem=0;
    private int idClassificacao=0;
    private int idUsuario=0;
    private int idItemPai=0;

    public int getIdTipoItem() {
        return idTipoItem;
    }

    public void setIdTipoItem(int idTipoItem) {
        this.idTipoItem = idTipoItem;
    }

    public int getIdClassificacao() {
        return idClassificacao;
    }

    public void setIdClassificacao(int idClassificacao) {
        this.idClassificacao = idClassificacao;
    }

    public int getIdItemPai() {
        return idItemPai;
    }

    public void setIdItemPai(int idItemPai) {
        this.idItemPai = idItemPai;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    


    @PostConstruct
    public void atualizaItens() {
        try {
            itens = ItemDAO.getLista();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void preparaIncluir() {
        salvar = true;
        item = new Item();
    }

    public void preparaAlterar() {
        salvar = false;
    }

    public void salvar() {
        try {
            Usuario u= new Usuario();
            TipoItem it= new TipoItem();
            Classificacao cla= new Classificacao();
            u.setIdUsuario(idUsuario);
            it.setIdTipoItem(idTipoItem);
            cla.setIdClassificacao(idClassificacao);
            Item itemPai = new Item();
            itemPai.setIdItem(idItemPai);
            item.setItemPai(itemPai);
            item.setUsuario(u);
            item.setTipoItem(it);
            item.setClassificacao(cla);
            
            if (salvar) {
                ItemDAO.inserir(item);

            } else {
                ItemDAO.alterar(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        atualizaItens();
    }

    public void excluir() {
        try {
            ItemDAO.excluir(item);
            atualizaItens();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> Itens) {
        this.itens = Itens;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item Item) {
        this.item = Item;
    }

}

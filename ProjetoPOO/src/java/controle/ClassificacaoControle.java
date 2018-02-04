package controle;

import dao.ClassificacaoDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Classificacao;
import org.primefaces.context.RequestContext;
import util.UtilInterface;

@ManagedBean
@SessionScoped
public class ClassificacaoControle {

    private List<Classificacao> classificacoes = new ArrayList<Classificacao>();
    private Classificacao classificacao = new Classificacao();
    private boolean salvar = false;

    public boolean isSalvar() {
        return salvar;
    }

    public void setSalvar(boolean salvar) {
        this.salvar = salvar;
    }

    @PostConstruct
    public void atualizaClassificacoes() {
        try {
            classificacoes = ClassificacaoDAO.getLista();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void preparaIncluir() {
        salvar = true;
        classificacao = new Classificacao();
    }

    public void preparaAlterar() {
        salvar = false;
    }

    public void salvar() {
        try {
            if (salvar) {
                ClassificacaoDAO.inserir(classificacao);

            } else {
                ClassificacaoDAO.alterar(classificacao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        atualizaClassificacoes();
    }

    public void excluir() {
        try {
            ClassificacaoDAO.excluir(classificacao);
            atualizaClassificacoes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Classificacao> getClassificacoes() {
        return classificacoes;
    }

    public void setClassificacoes(List<Classificacao> Classificacoes) {
        this.classificacoes = classificacoes;
    }

    public Classificacao getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(Classificacao Classificacao) {
        this.classificacao = classificacao;
    }

}

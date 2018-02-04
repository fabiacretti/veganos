package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Classificacao;
import java.util.List;
import util.Conexao;

public class ClassificacaoDAO {

    public static void inserir(Classificacao classificacao) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "insert into classificacao (descricao, ordem) values (?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, classificacao.getDescricao());
        stmt.setString(2, classificacao.getOrdem());

        stmt.execute();
        stmt.close();
        con.close();
    }

    public static void alterar(Classificacao classificacao) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "update classificacao set descricao = ?, ordem = ? where idClassificacao = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, classificacao.getDescricao());
        stmt.setString(2, classificacao.getOrdem());
        stmt.setInt(3, classificacao.getIdClassificacao());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void excluir(Classificacao classificacao) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "DELETE FROM classificacao WHERE  idClassificacao = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, classificacao.getIdClassificacao());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static List<Classificacao> getLista() throws SQLException {
        List<Classificacao> lista = new ArrayList<Classificacao>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM classificacao";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Classificacao classificacao = new Classificacao();
            classificacao.setIdClassificacao(rs.getInt("idClassificacao"));
            classificacao.setDescricao(rs.getString("descricao"));
            classificacao.setOrdem(rs.getString("ordem"));
            lista.add(classificacao);
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;
    }
}

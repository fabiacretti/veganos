package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.TipoItem;
import java.util.List;
import util.Conexao;

public class TipoItemDAO {

    public static void inserir(TipoItem tipoItem) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "insert into tipoItem (descricao) values (?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, tipoItem.getDescricao());

        stmt.execute();
        stmt.close();
        con.close();
    }

    public static void alterar(TipoItem tipoItem) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "update tipoItem set descricao = ? where idTipoItem = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, tipoItem.getDescricao());
        stmt.setInt(2, tipoItem.getIdTipoItem());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void excluir(TipoItem tipoItem) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "DELETE FROM tipoItem WHERE  idTipoItem = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, tipoItem.getIdTipoItem());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static List<TipoItem> getLista() throws SQLException {
        List<TipoItem> lista = new ArrayList<TipoItem>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM tipoItem";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            TipoItem tipoItem = new TipoItem();
            tipoItem.setIdTipoItem(rs.getInt("idTipoItem"));
            tipoItem.setDescricao(rs.getString("descricao"));
            lista.add(tipoItem);
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;
    }
}

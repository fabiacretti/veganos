package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Item;
import modelo.Usuario;
import modelo.TipoItem;
import modelo.Classificacao;
import java.util.List;
import util.Conexao;

public class ItemDAO {

    public static void inserir(Item item) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "";
        if (item.getItemPai().getIdItem() == 0) {
            sql
                    = "insert into item (nome, status, comentarioAdmin,idUsuario, idTipoItem, idClassificacao) values (?,?,?,?,?,?)";

        } else {
            sql
                    = "insert into item (nome, status, comentarioAdmin,idUsuario, idTipoItem, idClassificacao,idItemPai ) values (?,?,?,?,?,?,?)";

        }
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, item.getNome());
        stmt.setString(2, item.getStatus());
        stmt.setString(3, item.getComentarioAdmin());
        stmt.setInt(4, item.getUsuario().getIdUsuario());
        stmt.setInt(5, item.getTipoItem().getIdTipoItem());
        stmt.setInt(6, item.getClassificacao().getIdClassificacao());
        if (item.getItemPai().getIdItem() != 0) {
            stmt.setInt(7, item.getItemPai().getIdItem());
        }

        stmt.execute();
        stmt.close();
        con.close();
    }

    public static void alterar(Item item) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "update item set nome = ?, status = ?, comentarioAdmin=?, idUsuario=?, idTipoItem=?, idClassificacao=?, idItemPai=? where idItem = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, item.getNome());
        stmt.setString(2, item.getStatus());
        stmt.setString(3, item.getComentarioAdmin());
        stmt.setInt(4, item.getUsuario().getIdUsuario());
        stmt.setInt(5, item.getTipoItem().getIdTipoItem());
        stmt.setInt(6, item.getClassificacao().getIdClassificacao());
        stmt.setInt(7, item.getItemPai().getIdItem());
        stmt.setInt(8, item.getIdItem());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void excluir(Item item) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "DELETE FROM item WHERE  idItem = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, item.getIdItem());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static List<Item> getLista() throws SQLException {
        List<Item> lista = new ArrayList<Item>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT \n"
                + "	*,\n"
                + "	c.descricao as descCategoria,\n"
                + "	ti.descricao as descTipo,\n"
                + "	ip.nome as nomeItemPai,\n"
                + "	i.nome as nomeItem,\n"
                + "	ip.status as statusIp,\n"
                + "	ip.comentarioAdmin as comIp,\n"
                + "	i.status as statusI,\n"
                + "	i.comentarioAdmin as comI\n"
                + "	\n"
                + "FROM \n"
                + "	tipoitem ti,\n"
                + "	classificacao c,\n"
                + "	usuario u,\n"
                + "	item i\n"
                + "LEFT JOIN \n"
                + "	item ip ON i.idItemPai = ip.idItem\n"
                + "WHERE\n"
                + "	i.idTipoItem = ti.idTipoItem AND\n"
                + "	i.idClassificacao = c.idClassificacao AND\n"
                + "	i.idUsuario = u.idUsuario";
        System.out.println(sql);
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("idUsuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setEmail(rs.getString("email"));

            TipoItem tipoitem = new TipoItem();
            tipoitem.setIdTipoItem(rs.getInt("idTipoItem"));
            tipoitem.setDescricao(rs.getString("descTipo"));

            Classificacao classificacao = new Classificacao();
            classificacao.setIdClassificacao(rs.getInt("idClassificacao"));
            classificacao.setDescricao(rs.getString("descCategoria"));
            classificacao.setOrdem(rs.getString("ordem"));

            Item itemPai = new Item();
            itemPai.setIdItem(rs.getInt("idItemPai"));
            itemPai.setNome(rs.getString("nomeItemPai"));
            itemPai.setStatus(rs.getString("statusIp"));
            itemPai.setComentarioAdmin(rs.getString("comIp"));

            Item item = new Item();
            item.setIdItem(rs.getInt("idItem"));
            item.setNome(rs.getString("nomeItem"));
            item.setStatus(rs.getString("statusI"));
            item.setComentarioAdmin(rs.getString("comI"));

            item.setItemPai(itemPai);
            item.setClassificacao(classificacao);
            item.setTipoItem(tipoitem);
            item.setUsuario(usuario);

            lista.add(item);
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;
    }
}

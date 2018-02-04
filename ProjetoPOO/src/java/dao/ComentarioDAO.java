package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Item;
import modelo.Comentario;
import java.util.List;
import modelo.Usuario;
import util.Conexao;

public class ComentarioDAO {

    public static void inserir(Comentario comentario) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "insert into comentario (comentario, estrelas, status,idUsurio, idItem) values (?,?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, comentario.getComentario());
        stmt.setString(2, comentario.getEstrelas());
        stmt.setString(3, comentario.getStatus());
        stmt.setInt(4, comentario.getUsuario().getIdUsuario());
        stmt.setInt(5, comentario.getItem().getIdItem());
        stmt.execute();
        stmt.close();
        con.close();
    }

    public static void alterar(Comentario comentario) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "update comentario set comentario = ?, estrelas = ?, status=?,idUsuario=?, idItem=? where idComentario = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, comentario.getComentario());
        stmt.setString(2, comentario.getEstrelas());
        stmt.setString(3, comentario.getStatus());
        stmt.setInt(4, comentario.getUsuario().getIdUsuario());
        stmt.setInt(5, comentario.getItem().getIdItem());
        stmt.setInt(6, comentario.getIdComentario());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void excluir(Comentario comentario) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "DELETE FROM comentario WHERE  idComentario = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, comentario.getIdComentario());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static List<Comentario> getLista() throws SQLException {
        List<Comentario> lista = new ArrayList<Comentario>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM comentario";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("idUsuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setEmail(rs.getString("email"));
            
            Item item = new Item();
            item.setIdItem(rs.getInt("idItem"));
            item.setNome(rs.getString("nome"));
            item.setStatus(rs.getString("status"));
            
            Comentario comentario = new Comentario();
            comentario.setIdComentario(rs.getInt("idComentario"));
            comentario.setComentario(rs.getString("comentario"));
            comentario.setEstrelas(rs.getString("estrelas"));
            comentario.setStatus(rs.getString("status"));
            comentario.setUsuario(usuario);
            lista.add(comentario);
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;
    }
}

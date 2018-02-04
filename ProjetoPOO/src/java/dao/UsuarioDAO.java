package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Usuario;
import modelo.Classificacao;
import java.util.List;
import util.Conexao;

public class UsuarioDAO {

    public static void main(String[] args) {
        try {
            Usuario u = new Usuario();
            u.setEmail("cadu@24");
            u.setSenha("123");

            Usuario usuarioLogado = verificaLogin(u);
            if (usuarioLogado == null) {
                System.out.println("usuário não encontrado");
            } else {
                System.out.println("---------------------");
                System.out.println("NOME: " + usuarioLogado.getNome());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Usuario verificaLogin(Usuario usuario) throws SQLException {
        Usuario usu = new Usuario();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM usuario WHERE email=? and senha = password(md5(?))";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, usuario.getEmail());
        stmt.setString(2, usuario.getSenha());
        ResultSet rs = stmt.executeQuery();
        usu = null;
        System.out.println(stmt);
        if (rs.first()) {
            usu = new Usuario();
            usu.setIdUsuario(rs.getInt("idUsuario"));
            usu.setNome(rs.getString("nome"));
            usu.setEmail(rs.getString("email"));
            usu.setTipoUsuario(rs.getString("tipoUsuario"));
        }

        return usu;
    }

    public static void inserir(Usuario usuario) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "insert into usuario (nome, email, senha, tipoUsuario, idClassificacao) values (?,?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getEmail());
        stmt.setString(3, usuario.getSenha());
        stmt.setString(4, usuario.getTipoUsuario());
        stmt.setInt(5, usuario.getClassificacao().getIdClassificacao());
        stmt.execute();
        stmt.close();
        con.close();
    }

    public static void alterar(Usuario usuario) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "update usuario set nome = ?, email = ?, senha = ?, tipoUsuario=?, idClassificacao=? where idUsuario = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getEmail());
        stmt.setString(3, usuario.getSenha());
        stmt.setString(4, usuario.getTipoUsuario());
        stmt.setInt(5, usuario.getClassificacao().getIdClassificacao());
        stmt.setInt(6, usuario.getIdUsuario());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void excluir(Usuario usuario) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "DELETE FROM usuario WHERE  idUsuario = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, usuario.getIdUsuario());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static List<Usuario> getLista() throws SQLException {
        List<Usuario> lista = new ArrayList<Usuario>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT \n"
                + "	* \n"
                + "FROM \n"
                + "	usuario u,\n"
                + "	classificacao c\n"
                + "WHERE\n"
                + "	u.idClassificacao = c.idClassificacao";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Classificacao classificacao = new Classificacao();
            classificacao.setIdClassificacao(rs.getInt("idClassificacao"));
            classificacao.setDescricao(rs.getString("descricao"));
            classificacao.setOrdem(rs.getString("ordem"));

            Usuario usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("idUsuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setEmail(rs.getString("email"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setTipoUsuario(rs.getString("tipoUsuario"));
            usuario.setClassificacao(classificacao);
            lista.add(usuario);
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;
    }
}

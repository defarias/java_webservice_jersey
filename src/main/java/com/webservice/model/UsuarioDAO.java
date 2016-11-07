package com.webservice.model;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.ArrayList;

import sun.misc.BASE64Encoder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mysql.jdbc.Blob;
import com.webservice.persistence.DAO;
import com.webservice.pojo.Usuario;

/**
 * Created by Deywid on 20/07/2016.
 */

@Path("/usuario")
public class UsuarioDAO extends DAO {
	
	private String criptografar(String senha) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(senha.getBytes());
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(digest.digest());
	}
	
	@POST
	@Path("/logar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario logar(String json) throws Exception {
		Usuario usuario = construirObjeto(json);
		
		conectarBanco();
		
		String sql = 
				"SELECT * FROM usuario "
				+ "WHERE email = ? "
				+ "AND senha = ?";
		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, usuario.getEmail());
		preparedStatement.setString(2, criptografar(usuario.getSenha()));
		
		resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			usuario = new Usuario();
			usuario.setCodigo(resultSet.getInt("codigo"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setSexo(resultSet.getString("sexo").charAt(0));
			usuario.setCpf(resultSet.getString("cpf"));
			usuario.setEmail(resultSet.getString("email"));
			usuario.setSenha(resultSet.getString("senha"));
			usuario.setFoto(resultSet.getString("foto"));
			usuario.setByteImagem(resultSet.getBytes("byteImagem"));
			usuario.setStatus(resultSet.getInt("status"));
			
			System.out.println("Nome: "+usuario.getNome());
			System.out.println("Email: "+usuario.getEmail());
			System.out.println("Senha:"+usuario.getSenha());
			System.out.println("Imagem:"+usuario.getFoto());
			
			
		} else {
			usuario = null;
		}
		desconectarBanco();
		
		return usuario;
	}

	@POST
	@Path("/cadastrar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario cadastrar(String json) throws Exception {
		
		Usuario usuario = construirObjeto(json);
		
		conectarBanco();
				
		String sql = 
				"INSERT INTO usuario "
				+ "VALUES(null, ?,?,?,?,?,?,?,?)";
		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, usuario.getNome());
		preparedStatement.setString(2, Character.toString(usuario.getSexo()));
		preparedStatement.setString(3, usuario.getCpf());
		preparedStatement.setString(4, usuario.getEmail());
		preparedStatement.setString(5, criptografar(usuario.getSenha()));
		preparedStatement.setString(6, usuario.getFoto());
		preparedStatement.setBytes(7, usuario.getByteImagem());
		preparedStatement.setInt(8, usuario.getStatus());
		preparedStatement.execute();
		
		desconectarBanco();
		
		return usuario;
	}
	
	@PUT
	@Path("/alterar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario alterar(String json) throws Exception {
		Usuario usuario = construirObjeto(json);
		
		conectarBanco();
		
		String sql = 
				"UPDATE usuario SET "
				+ "nome = ?, "
				+ "sexo = ?, "
				+ "cpf = ?, "
				+ "email = ?, "
				+ "senha =?, "
				+ "foto = ? "
				+ "WHERE codigo = ?";	
		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, usuario.getNome());
		preparedStatement.setString(2, Character.toString(usuario.getSexo()));
		preparedStatement.setString(3, usuario.getCpf());
		preparedStatement.setString(4, usuario.getEmail());
		preparedStatement.setString(5, criptografar(usuario.getSenha()));
		preparedStatement.setString(6, usuario.getFoto());
		preparedStatement.setInt(7, usuario.getCodigo());
		preparedStatement.execute();	
		
		desconectarBanco();
		return usuario;
	}

	private boolean checarStatus(Usuario usuario) throws Exception {
		boolean resultado;
		
		conectarBanco();
		
		String sql = 
				"SELECT * FROM usuario "
				+ "WHERE codigo = ? "
				+ "AND status = ?";
		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, usuario.getCodigo());
		preparedStatement.setInt(2, 1);
		
		resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			resultado = true;
		} else {
			resultado = false;
		}
		desconectarBanco();
		
		return resultado;
	}

	@PUT
	@Path("/desativar")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean desativarConta(Usuario usuario) throws Exception {
		boolean resultado;
		
		conectarBanco();
		
		String sql = 
				"UPDATE usuario SET "
				+ "status = ? "
				+ "WHERE codigo = ?";
		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, 0);
		preparedStatement.setInt(2, usuario.getCodigo());
		preparedStatement.execute();

		desconectarBanco();
		
		if(!checarStatus(usuario)){
			resultado = true;
		}else{
			resultado = false;
		}
		return resultado;
	}
	
	@PUT
	@Path("/reativar")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean reativarConta(Usuario usuario) throws Exception {
		boolean resultado;
		
		conectarBanco();
		
		String sql = 
				"UPDATE usuario SET "
				+ "status = ? "
				+ "WHERE codigo = ?";
		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, 1);
		preparedStatement.setInt(2, usuario.getCodigo());
		preparedStatement.execute();
		
		desconectarBanco();
		
		if(checarStatus(usuario)){
			resultado = true;
		}else{
			resultado = false;
		}
		return resultado;
	}
	
	@GET
	@Path("/buscarTodos")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Usuario> buscarTodos() throws SQLException{
		ArrayList<Usuario> resultado = new ArrayList<>();
		conectarBanco();
		
		String sql = "SELECT * FROM usuario";
		preparedStatement = connection.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()){
			Usuario usuario = new Usuario();
			usuario.setCodigo(resultSet.getInt("codigo"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setSexo(resultSet.getString("sexo").charAt(0));
			usuario.setCpf(resultSet.getString("cpf"));
			usuario.setEmail(resultSet.getString("email"));
			usuario.setSenha(resultSet.getString("senha"));
			usuario.setFoto(resultSet.getString("foto"));
			usuario.setStatus(resultSet.getInt("status"));
			resultado.add(usuario);
		}
		
		return resultado;
	}
	
	private Usuario construirObjeto(String json){
		System.out.println("JSON: "+json);
		
		Gson gson = new Gson();
        Usuario usuario = new Usuario();
        Type usuarioType = new TypeToken<Usuario>(){}.getType();
        usuario = gson.fromJson(json, usuarioType);
		
        System.out.println("JSON: "+usuario.getNome());

		return usuario;
	}

}

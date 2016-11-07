package com.webservice.model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webservice.persistence.DAO;
import com.webservice.pojo.Classificacao;
import com.webservice.pojo.Comentario;
import com.webservice.pojo.Reclamacao;

@Path("/comentario")
public class ComentarioDAO extends DAO{
	
	@POST
	@Path("/cadastrar")
	@Produces(MediaType.APPLICATION_JSON)
	public void cadastrar(String json) throws Exception{
		Comentario comentario = construirObjeto(json);
		conectarBanco();
		
		String sql = 
				"INSERT INTO comentarios "
				+ "VALUES(null, ?,?,?,NOW())";
		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, comentario.getCodigoUsuario());
		preparedStatement.setInt(2, comentario.getCodigoReclamacao());
		preparedStatement.setString(3, comentario.getComentario());
		preparedStatement.execute();
		
		desconectarBanco();
	}
	
	@PUT
	@Path("/alterar")
	@Produces(MediaType.APPLICATION_JSON)
	public void alterar(String json) throws Exception{
		Comentario comentario = construirObjeto(json);
		conectarBanco();
		
		String sql = 
				"UPDATE comentarios SET"
				+ "comentario = ? "
				+ "WHERE codigo = ?";
	
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, comentario.getComentario());
		preparedStatement.setInt(2, comentario.getCodigo());
		preparedStatement.setInt(3, comentario.getCodigoUsuario());
		
		desconectarBanco();
	}
	
	@DELETE
	@Path("/excluir")
	@Produces(MediaType.APPLICATION_JSON)
	public void excluir(String json) throws Exception{
		Comentario comentario = construirObjeto(json);
		conectarBanco();
		
		String sql = 
				"DELETE FROM comentarios"
				+ "WHERE codigo = ?";
		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, comentario.getCodigo());
		preparedStatement.execute();
		
		desconectarBanco();
	}
	
	@POST
	@Path("/buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Comentario> buscar(String json) throws Exception{
		Comentario comentario = construirObjeto(json);
		ArrayList<Comentario> listaComentarios = new ArrayList<>();
		conectarBanco();
		
		String sql = 
				"SELECT "
				+ "comentarios.codigo,"
				+ "comentarios.codigoUsuario,"
				+ "comentarios.codigoReclamacoes,"
				+ "comentarios.comentario, "
				+ "usuario.nome "
				+ "FROM comentarios "
				+ "INNER JOIN usuario "
				+ "ON comentarios.codigoUsuario = usuario.codigo "
				+ "WHERE codigoReclamacoes = ?";
		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, comentario.getCodigoReclamacao());
		
		resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()){
			comentario = new Comentario();
			comentario.setCodigo(resultSet.getInt("codigo"));
			comentario.setCodigoUsuario(resultSet.getInt("codigoUsuario"));
			comentario.setCodigoReclamacao(resultSet.getInt("codigoReclamacoes"));
			comentario.setComentario(resultSet.getString("comentario"));
			comentario.setNomeCriador(resultSet.getString("nome"));
			listaComentarios.add(comentario);
		}
		desconectarBanco();
		
		for(Comentario c : listaComentarios){
			System.out.println("Comentario: "+ c.getComentario());
		}
		
		return listaComentarios;
	}
	
	private Comentario construirObjeto(String json){
		System.out.println("JSON: "+json);
		
		Gson gson = new Gson();
		Comentario comentario = new Comentario();
        Type usuarioType = new TypeToken<Comentario>(){}.getType();
        comentario = gson.fromJson(json, usuarioType);
		
		return comentario;
	}
}

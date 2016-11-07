package com.webservice.model;

import java.util.ArrayList;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.webservice.persistence.DAO;
import com.webservice.pojo.Categoria;

/**
 * Created by Deywid on 31/07/2016.
 */

@Path("/categoria")
public class CategoriaDAO extends DAO{
	
	@POST
	@Path("/cadastrar")
	@Produces(MediaType.APPLICATION_JSON)
	public void cadastrar(Categoria categoria) throws Exception{
		conectarBanco();
		
		String sql = 
				"INSERT INTO categorias"
				+ "VALUES(null, ?,?)";
		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, categoria.getNome());
		preparedStatement.setString(2, categoria.getDescricao());
		preparedStatement.execute();
		
		desconectarBanco();
	}
	
	@PUT
	@Path("/alterar")
	@Produces(MediaType.APPLICATION_JSON)
	public void alterar(Categoria categoria) throws Exception{
		conectarBanco();
		
		String sql = 
				"UPDATE categorias SET "
				+ "nome = ?,"
				+ "descricao = ? "
				+ "WHERE codigo = ?";
		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, categoria.getNome());
		preparedStatement.setString(2, categoria.getDescricao());
		preparedStatement.setInt(3, categoria.getCodigo());
		preparedStatement.execute();
		
		desconectarBanco();		
	}
	
	@DELETE
	@Path("/excluir")
	@Produces(MediaType.APPLICATION_JSON)
	public void excluir(Categoria categoria) throws Exception{
		conectarBanco();
		
		String sql = 
				"DELETE FROM categorias "
				+ "WHERE codigo = ?";
		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, categoria.getCodigo());
		preparedStatement.execute();
		
		desconectarBanco();
	}
	
	@GET
	@Path("/buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Categoria> buscar() throws Exception{
		ArrayList<Categoria> lista = new ArrayList<>();
		
		conectarBanco();
		
		String sql = 
				"SELECT * FROM categorias";
		
		preparedStatement = connection.prepareStatement(sql);
		
		resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
			Categoria categoria = new Categoria();
			categoria.setCodigo(resultSet.getInt("codigo"));
			categoria.setNome(resultSet.getString("nome"));
			categoria.setDescricao(resultSet.getString("descricao"));
			lista.add(categoria);
		}
		desconectarBanco();
		
		return lista;
	}
}

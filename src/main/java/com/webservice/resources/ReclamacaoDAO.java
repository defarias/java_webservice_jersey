package com.webservice.resources;

import java.util.Date;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webservice.model.Reclamacao;
import com.webservice.model.Usuario;
import com.webservice.persistence.DAO;

/**
 * Created by Deywid on 29/07/2016.
 */

@Path("/reclamacao")
public class ReclamacaoDAO extends DAO {

	@POST
	@Path("/cadastrar")
	@Produces(MediaType.APPLICATION_JSON)	
	public void cadastrar(String json) throws Exception{
		Reclamacao reclamacao = construirObjeto(json);
		Date date = new Date();				
		
		conectarBanco();
		
		String sql = 
				"INSERT INTO reclamacoes "
				+ "VALUES(null, ?,?,?,?,?,?,?,?)";
		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, reclamacao.getCodigoUsuario());
		preparedStatement.setInt(2, reclamacao.getCodigoCategoria());
		preparedStatement.setString(3, reclamacao.getFoto());
		preparedStatement.setDouble(4, reclamacao.getLatitude());
		preparedStatement.setDouble(5, reclamacao.getLongitude());
		preparedStatement.setString(6, reclamacao.getObservacao());
		preparedStatement.setInt(7,reclamacao.getStatus());
		preparedStatement.setDate(8, (java.sql.Date) date);
		preparedStatement.execute();
		
		desconectarBanco();
	}
	
	@PUT
	@Path("/alterar")
	@Produces(MediaType.APPLICATION_JSON)
	public void alterar(String json) throws Exception{
		Reclamacao reclamacao = construirObjeto(json);
		conectarBanco();
		
		String sql = 
				"UPDATE reclamacoes SET "
				+ "codigoCategorias = ?,"
				+ "foto = ?,"
				+ "latitude = ?,"
				+ "longitude = ?,"
				+ "observacao = ?,"
				+ "status = ?"
				+ "WHERE codigo = ?";
		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, reclamacao.getCodigoCategoria());
		preparedStatement.setString(2, reclamacao.getFoto());
		preparedStatement.setDouble(3, reclamacao.getLatitude());
		preparedStatement.setDouble(4, reclamacao.getLongitude());
		preparedStatement.setString(5, reclamacao.getObservacao());
		preparedStatement.setInt(6,reclamacao.getStatus());
		preparedStatement.setInt(7, reclamacao.getCodigo());	
		preparedStatement.execute();
		
		desconectarBanco();
	}
	
	@DELETE
	@Path("/excluir")
	@Produces(MediaType.APPLICATION_JSON)
	public void excluir(String json) throws Exception{
		Reclamacao reclamacao = construirObjeto(json);
		conectarBanco();
		
		String sql = 
				"DELETE FROM reclamacoes"
				+ "WHERE codigo = ?";
		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, reclamacao.getCodigo());
		preparedStatement.execute();
		
		desconectarBanco();
	}
	
	@POST
	@Path("/minhasReclamacoes")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reclamacao> buscarMinhasReclamacoes(String json) throws Exception{
		ArrayList<Reclamacao> lista = new ArrayList<>();
		ClassificacaoDAO classificacaoDAO = new ClassificacaoDAO();		
		Reclamacao reclamacao = construirObjeto(json);
		
		conectarBanco();
		
		String sql = 
				"SELECT * FROM reclamacoes "
				+ "WHERE codigoUsuario = ?";
		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, reclamacao.getCodigoUsuario());
		resultSet = preparedStatement.executeQuery();
		 
		while(resultSet.next()){
			Reclamacao reclamacao2 = new Reclamacao();
			reclamacao2.setCodigo(resultSet.getInt("codigo"));
			reclamacao2.setCodigoUsuario(resultSet.getInt("codigoUsuario"));
			reclamacao2.setCodigoCategoria(resultSet.getInt("codigoCategoria"));
			reclamacao2.setFoto(resultSet.getString("foto"));
			reclamacao2.setLatitude(resultSet.getDouble("latitude"));
			reclamacao2.setLongitude(resultSet.getDouble("longitude"));
			reclamacao2.setObservacao(resultSet.getString("observacao"));
			reclamacao2.setStatus(resultSet.getInt("status"));
			//reclamacao2.setClassificacao(classificacaoDAO.buscar(resultSet.getInt("codigo")));
			lista.add(reclamacao2);
		}
		desconectarBanco();			
		return lista;
	}
	private Reclamacao construirObjeto(String json){
		System.out.println("JSON: "+json);
		
		Gson gson = new Gson();
        Reclamacao reclamacao = new Reclamacao();
        Type usuarioType = new TypeToken<Reclamacao>(){}.getType();
        reclamacao = gson.fromJson(json, usuarioType);
		
        System.out.println("JSON: "+reclamacao.getObservacao());

		return reclamacao;
	}
	
}

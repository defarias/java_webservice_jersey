package com.webservice.resources;

import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webservice.model.Classificacao;
import com.webservice.model.Reclamacao;
import com.webservice.persistence.DAO;

@Path("/classificacao")
public class ClassificacaoDAO extends DAO {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void cadastrar(String json) throws Exception {
		Classificacao classificacao = construirObjeto(json);
		conectarBanco();

		String sql = "INSERT INTO classificacoes " + "VALUES(?,?,?)";

		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, classificacao.getCodigoUsuario());
		preparedStatement.setInt(2, classificacao.getCodigoReclamacao());
		preparedStatement.setInt(3, classificacao.getClassificacao());
		preparedStatement.execute();

		desconectarBanco();
	}

	public void alterar(String json) throws Exception {
		Classificacao classificacao = construirObjeto(json);
		conectarBanco();
		
		String sql = 
				"UPDATE classificacoes SET"
				+ "classificacao = ?,"
				+ "WHERE codigoUsuario = ? "
				+ "AND codigoReclamacoes = ?";
		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, classificacao.getClassificacao());
		preparedStatement.setInt(2, classificacao.getCodigoUsuario());
		preparedStatement.setInt(3, classificacao.getCodigoReclamacao());
		preparedStatement.execute();
		
		desconectarBanco();
	}
	
	
	public ArrayList<Classificacao> buscar(String json) throws Exception {
		Classificacao classificacao = construirObjeto(json);
		conectarBanco();
		ArrayList<Classificacao> lista = new ArrayList<>();
		
		String sql = 
				"SELECT * FROM Classificacao WHERE codigoReclamacoes = ?";
		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, classificacao.getCodigoReclamacao());
		resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()){
			classificacao = new Classificacao();
			classificacao.setCodigoUsuario(resultSet.getInt("codigoUsuario"));
			classificacao.setCodigoReclamacao(resultSet.getInt("codigoReclamacoes"));
			classificacao.setClassificacao(resultSet.getInt("classificacao"));
			lista.add(classificacao);
		}	
		desconectarBanco();
		
		return lista;
		
	}
	private Classificacao construirObjeto(String json){
		System.out.println("JSON: "+json);
		
		Gson gson = new Gson();
		Classificacao classificacao = new Classificacao();
        Type usuarioType = new TypeToken<Classificacao>(){}.getType();
        classificacao = gson.fromJson(json, usuarioType);
		
        System.out.println("JSON: "+classificacao.getClassificacao());

		return classificacao;
	}
}
 
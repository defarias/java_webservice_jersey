package com.webservice.model;

import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webservice.persistence.DAO;
import com.webservice.pojo.Classificacao;
import com.webservice.pojo.Reclamacao;

@Path("/classificacao")
public class ClassificacaoDAO extends DAO {

	@POST
	@Path("/cadastrar")
	@Produces(MediaType.APPLICATION_JSON)
	public void cadastrar(String json) throws Exception {
		Classificacao classificacao = construirObjeto(json);
		int codigo = buscarCodigo(classificacao); 
		System.out.println("Classificacao:"+codigo);
		if(codigo==0){
			conectarBanco();

			String sql = "INSERT INTO classificacoes " + "VALUES(NULL,?,?,?)";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, classificacao.getCodigoUsuario());
			preparedStatement.setInt(2, classificacao.getCodigoReclamacao());
			preparedStatement.setDouble(3, classificacao.getClassificacao());
			preparedStatement.execute();

			desconectarBanco();

		}else{
			classificacao.setCodigo(codigo);
			alterar(classificacao);
		}
		
	}

	public void alterar(Classificacao classificacao) throws Exception {
		conectarBanco();
		
		String sql = 
				"UPDATE classificacoes SET "
				+ "classificacao = ? "
				+ "WHERE codigo = ?";
		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setDouble(1, classificacao.getClassificacao());
		preparedStatement.setInt(2, classificacao.getCodigo());
		preparedStatement.execute();
		
		desconectarBanco();
	}
	
	public int buscarCodigo(Classificacao classificacao) throws Exception {
		int retorno = 0;
		conectarBanco();
		
		String sql = 
				"SELECT * FROM classificacoes WHERE codigoReclamacoes = ? AND codigoUsuario = ?";
		
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, classificacao.getCodigoReclamacao());
		preparedStatement.setInt(2, classificacao.getCodigoUsuario());
		resultSet = preparedStatement.executeQuery();
		
		if(resultSet.next()){
			classificacao = new Classificacao();
			classificacao.setCodigo(resultSet.getInt("codigo"));
			retorno = classificacao.getCodigo();
		}	
		desconectarBanco();
		
		return retorno;
	}
	
	
	public ArrayList<Classificacao> buscarTodos(String json) throws Exception {
		Classificacao classificacao = construirObjeto(json);
		conectarBanco();
		ArrayList<Classificacao> lista = new ArrayList<>();
		
		String sql = 
				"SELECT * FROM classificacoes WHERE codigoReclamacoes = ?";
		
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
 
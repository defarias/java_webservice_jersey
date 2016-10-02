package com.webservice.resources;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.GET; //import da biblioteca jersey
import javax.ws.rs.Path; //import da biblioteca jersey
import javax.ws.rs.Produces; //import da biblioteca jersey
import javax.ws.rs.core.MediaType;

import com.webservice.model.Usuario;
import com.webservice.persistence.DAO;

@Path("/teste") // o @path define a URI do recurso que nesse caso será /helloworld
public class Services extends DAO{

	@GET // utilizando apenas o verbo GET, ou seja, vou apenas ler o recurso
	@Path("/hi")
	@Produces(MediaType.TEXT_PLAIN) // define qual tipo MIME é retornado para o cliente
	public String exibir(){
		
		new File("imageb").mkdir();
		return "Hello World";
	}
	@GET // utilizando apenas o verbo GET, ou seja, vou apenas ler o recurso
	@Path("/cadastrar")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario cadastrarTeste() throws Exception{
		Usuario usuario = new Usuario();
		UsuarioDAO usuarioDAO = new UsuarioDAO();

		usuario.setNome("Deywid");
		usuario.setSexo('m');
		usuario.setCpf("440260578-44");
		usuario.setEmail("deywid.farias@gmail.com");
		usuario.setSenha("etc5666");
		usuario.setFoto("");
		usuario.setStatus(1);

		//usuarioDAO.cadastrar(usuario);
		return usuario;
	}
	@GET
	@Path("/buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario buscar() throws SQLException{
		ArrayList<Usuario> resultado = new ArrayList<>();
		conectarBanco();
		Usuario usuario = null;
		
		String sql = "SELECT * FROM usuario WHERE codigo = 67";
		preparedStatement = connection.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		
		if(resultSet.next()){
			usuario = new Usuario();
			usuario.setCodigo(resultSet.getInt("codigo"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setSexo(resultSet.getString("sexo").charAt(0));
			usuario.setCpf(resultSet.getString("cpf"));
			usuario.setEmail(resultSet.getString("email"));
			usuario.setSenha(resultSet.getString("senha"));
			usuario.setFoto(resultSet.getString("foto"));
			usuario.setStatus(resultSet.getInt("status"));
		}
		
		return usuario;
	}
	 
} 

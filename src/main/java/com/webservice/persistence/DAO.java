package com.webservice.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class DAO {
	protected Connection connection = null;
	protected PreparedStatement preparedStatement;
	protected ResultSet resultSet;
	String url = "jdbc:mysql://localhost:3306/saneamob_database";
	String usuario = "root";
	String senha = "";
	
	protected void conectarBanco(){
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(url, usuario, senha);
		}catch (Exception e){
			System.out.println("Falha na conexão");
		}
	}
	protected void desconectarBanco()throws Exception{	
		if(connection != null){
			connection.close();
		}	
			
	}

}

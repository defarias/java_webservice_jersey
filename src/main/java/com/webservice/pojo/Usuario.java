package com.webservice.pojo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Deywid on 20/07/2016.
 */

@XmlRootElement
public class Usuario{

    private int codigo;
    private String nome;
    private char sexo;
    private String cpf;
    private String email;
    private String senha;
    private byte[] byteImagem;
    private String foto;
    private int status;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public byte[] getByteImagem() {
		return byteImagem;
	}

	public void setByteImagem(byte[] byteImagem) {
		this.byteImagem = byteImagem;
	}
}

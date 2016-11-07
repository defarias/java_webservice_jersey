package com.webservice.pojo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Deywid on 13/07/2016.
 */
@XmlRootElement
public class Comentario {
    private int codigo;
	private int codigoUsuario;
    private int codigoReclamacao;
    private String comentario;
    private String nomeCriador;

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public int getCodigoReclamacao() {
        return codigoReclamacao;
    }

    public void setCodigoReclamacao(int codigoReclamacao) {
        this.codigoReclamacao = codigoReclamacao;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNomeCriador() {
		return nomeCriador;
	}

	public void setNomeCriador(String nomeCriador) {
		this.nomeCriador = nomeCriador;
	}
}

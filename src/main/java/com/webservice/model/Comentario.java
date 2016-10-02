package com.webservice.model;

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
    private String Comentario;
    private Date data;
    private String criador;

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
        return Comentario;
    }

    public void setComentario(String comentario) {
        Comentario = comentario;
    }

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getCriador() {
		return criador;
	}

	public void setCriador(String criador) {
		this.criador = criador;
	}
}

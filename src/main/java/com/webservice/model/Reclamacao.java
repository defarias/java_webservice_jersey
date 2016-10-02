package com.webservice.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Deywid on 13/07/2016.
 */
@XmlRootElement
public class Reclamacao {
    private int codigo;
    private int codigoUsuario;
    private int codigoCategoria;
    private String foto;
    private double latitude;
    private double longitude;
    private String observacao;
    private int status;
    private ArrayList<Classificacao> classificacao = new ArrayList<>(); 

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(int codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(int codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public ArrayList<Classificacao> getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(ArrayList<Classificacao> classificacao) {
		this.classificacao = classificacao;
	}
}

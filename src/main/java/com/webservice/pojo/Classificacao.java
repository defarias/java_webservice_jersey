package com.webservice.pojo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Deywid on 13/07/2016.
 */
@XmlRootElement
public class Classificacao {
	private int codigo;
    private int codigoUsuario;
    private int codigoReclamacao;
    private double classificacao;

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

    public double getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(double classificacao) {
        this.classificacao = classificacao;
    }

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
}

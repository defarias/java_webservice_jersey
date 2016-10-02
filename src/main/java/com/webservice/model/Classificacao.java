package com.webservice.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Deywid on 13/07/2016.
 */
@XmlRootElement
public class Classificacao {
    private int codigoUsuario;
    private int codigoReclamacao;
    private int classificacao;

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

    public int getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(int classificacao) {
        this.classificacao = classificacao;
    }
}

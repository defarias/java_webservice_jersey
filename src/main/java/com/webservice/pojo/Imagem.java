package com.webservice.pojo;
/**
 * Created by Deywid on 03/11/2016.
 */

public class Imagem {
    private String nome;
    private String image;

    public Imagem(String nome, String image){
        this.nome = nome;
        this.image = image;
    }

    public Imagem() {

    }

	public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

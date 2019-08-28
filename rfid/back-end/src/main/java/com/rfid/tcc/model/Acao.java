package com.rfid.tcc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Acao {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;

    private String nome;

    private Integer codigorfid;

    private String caminho;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCodigorfid() {
        return codigorfid;
    }

    public void setCodigorfid(Integer codigorfid) {
        this.codigorfid = codigorfid;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}
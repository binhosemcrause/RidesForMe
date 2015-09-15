package br.com.ridesforme.ridesforme.basicas;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by Marcos on 05/09/2015.
 */
public class Carona implements Serializable{

    public String idaVolta;
    public String origem;
    public String destino;
    public Date data;
    public int vagaDisponivel;
    public int _id;


    public Carona(){}

    public Carona(String ida, String origem, String destino, Date data, int vaga){
        this.idaVolta = ida;
        this.origem = origem;
        this.destino = destino;
        this.data = data;
        this.vagaDisponivel = vaga;
    }

    public Carona(String origem, String destino){
        this.origem = origem;
        this.destino = destino;
    }

    public Carona(String ida, String origem, String destino, Date data){
        this.idaVolta = ida;
        this.origem = origem;
        this.destino = destino;
        this.data = data;
    }


}

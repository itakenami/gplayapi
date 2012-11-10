package models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import play.data.validation.MaxSize;
import play.db.jpa.Model;

@Entity(name="analistas")
public class Analista extends Model{


    @MaxSize(message = "* tamanho maximo de 255 caracteres.",value=255)
    public String nome;

    @MaxSize(message = "* tamanho maximo de 255 caracteres.",value=255)
    public String especialidade;

    @ManyToOne
    @JoinColumn(name ="cargo_id")
    public Cargo cargo;
    
    @Override
    public String toString(){
        return nome;
    }

}

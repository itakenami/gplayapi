package models;

import javax.persistence.Entity;
import play.data.validation.MaxSize;
import play.db.jpa.Model;

@Entity(name="cargos")
public class Cargo extends Model{

    @MaxSize(message = "* tamanho maximo 20 caracteres.",value=10)
    public String nome;
    
    @Override
    public String toString(){
        return nome;
    }

}

package models;

import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import play.data.validation.MaxSize;
import play.db.jpa.Model;

@Entity(name="projetos")
public class Projeto extends Model {


    @MaxSize(message = "* tamanho maximo 255 caracteres.",value=255)
    public String nome;

    @MaxSize(message = "* tamanho maximo 255 caracteres.",value=255)
    public String descricao;

    public Date data_fim;

    public Date data_inicio;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="analistas_projetos", joinColumns={@JoinColumn(name="projeto_id")}, inverseJoinColumns={@JoinColumn(name="analista_id")})
    public Set<Analista> analistas;
    
    @Override
    public String toString(){
        return nome;
    }

}

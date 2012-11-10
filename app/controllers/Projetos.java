package controllers;

import api.wadl.annotation.Param;
import api.wadl.annotation.Path;
import api.wadl.annotation.Paths;
import api.wadl.annotation.Resource;
import java.util.List;
import models.Projeto;
import utils.Result;
import utils.transform.Exclude;
import controllers.restapi.DefaultController;

@Resource(name="projetos",param={@Param(name="{id}",type="int")})
public class Projetos extends DefaultController {
    
    
    @Path(method="GET",id="getAllProjetos")
    public static void listAll(){
        
        List<Projeto> projetos = Projeto.findAll();
        renderObject(Result.OK(projetos),new Exclude().exclude("analistas"));
    }
    
    @Path(name="/{id}",method="GET",id="getProjetoById")
    public static void findId(Long id) {
        if(id != null) {
            Projeto projeto = Projeto.findById(id);
            projeto.analistas.size();
            if(projeto!=null){
		renderObject(Result.OK(projeto));
            }else{
                //Registro nao encontrado
		renderObject(Result.ERROR(3));
            }
        }else{
            //Parametro nao informado
	    renderObject(Result.ERROR(4));
        }       
    }
    
    
    @Path(name="/{id}",method="DELETE",id="deleteProjeto")
    public static void delete(Long id) {
        try {
            Projeto projeto = Projeto.findById(id);
            if(projeto!=null){
                projeto.delete();
                renderObject(Result.OK("Projeto apagado com sucesso."));
            }else{
                //Registro nao encontrado
                renderObject(Result.ERROR(3));
            }
        } catch (Exception ex) {
            //Erro de sistema
            renderObject(Result.SYSERROR(ex.getMessage()));
        }
    }
    
    @Paths(
        {
            @Path(method="POST",id="addProjeto" ,param={
                @Param(name="projeto.nome",type="string"),
                @Param(name="projeto.descricao",type="string"),
                @Param(name="projeto.data_inicio",type="string"),
                @Param(name="projeto.data_fim",type="string"),
                @Param(name="projeto.analistas.id",type="int")
            }),
            @Path(name="/{id}",id="saveProjeto",method="PUT", param={
                @Param(name="projeto.nome",type="string"),
                @Param(name="projeto.descricao",type="string"),
                @Param(name="projeto.data_inicio",type="string"),
                @Param(name="projeto.data_fim",type="string"),
                @Param(name="projeto.analistas.id",type="int")
            })
        }
    )
    public static void save(Long id, Projeto projeto){
      
      if(id != null){
          
          Projeto projeto_aux = projeto;
          projeto = Projeto.findById(id);
          if(projeto != null){          
	      projeto.nome  = projeto_aux.nome;
              projeto.descricao = projeto_aux.descricao;
              projeto.data_inicio = projeto_aux.data_inicio;
              projeto.data_fim = projeto_aux.data_fim;
              projeto.analistas = projeto_aux.analistas;
          }else{
              //Registro nao encontrado
              renderObject(Result.ERROR(3));
          }
      }
      
      validation.valid(projeto);
      
      if (validation.hasErrors()) {
          renderObject(Result.VALIDERROR(validation));
          return;
      }
      
      Projeto obj_out = projeto.save();
      renderObject(Result.OK(obj_out));
      
    }    
}

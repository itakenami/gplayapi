package controllers;

import api.wadl.annotation.Param;
import api.wadl.annotation.Path;
import api.wadl.annotation.Paths;
import api.wadl.annotation.Resource;
import java.util.List;
import models.Analista;
import utils.Result;
import controllers.restapi.DefaultController;

@Resource(name="analistas",param={@Param(name="{id}",type="int")})
public class Analistas extends DefaultController {
    
    
    @Path(method="GET",id="getAllAnalista")
    public static void listAll(){
        List<Analista> analistas = Analista.findAll();			
        renderObject(Result.OK(analistas));
    }
    
    @Path(name="/{id}",method="GET",id="getAnalistaById")
    public static void findId(Long id) {
        if(id != null) {
            Analista analista = Analista.findById(id);
            if(analista!=null){
		renderObject(Result.OK(analista));
            }else{
                //Registro nao encontrado
		renderObject(Result.ERROR(3));
            }
        }else{
            //Parametro nao informado
	    renderObject(Result.ERROR(4));
        }       
    }
    
    
    @Path(name="/{id}",method="DELETE",id="deleteAnalista")
    public static void delete(Long id) {
        try {
            Analista analista = Analista.findById(id);
            if(analista!=null){
                analista.delete();
                renderObject(Result.OK("Cargo apagado com sucesso."));
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
            @Path(method="POST",id="addAnalista" ,param={
                @Param(name="analista.nome",type="string"),
                @Param(name="analista.especialidade",type="string"),
                @Param(name="analista.cargo_id",type="int")
            }),
            @Path(name="/{id}",id="saveAnalista",method="PUT", param={
                @Param(name="analista.nome",type="string"),
                @Param(name="analista.especialidade",type="string"),
                @Param(name="analista.cargo.id",type="int")
            })
        }
    )
    public static void save(Long id, Analista analista){
      
      if(id != null){
          
          //System.out.println("ANTES - VOU SALVAR AGORA: "+analista.nome+ " - "+analista.especialidade+" - "+analista.cargo.id);
          
          Analista analista_aux = analista;
          analista = Analista.findById(id);
          if(analista != null){          
	      analista.nome  = analista_aux.nome;
              analista.especialidade = analista_aux.especialidade;
              analista.cargo = analista_aux.cargo;
              
              
          }else{
              //Registro nao encontrado
              renderObject(Result.ERROR(3));
          }
      }
      
      validation.valid(analista);
      
      if (validation.hasErrors()) {
          renderObject(Result.VALIDERROR(validation));
          return;
      }
      
      Analista obj_out = analista.save();
      renderObject(Result.OK(obj_out));
      
    }    
}

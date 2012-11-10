package controllers;

import api.wadl.annotation.Param;
import api.wadl.annotation.Path;
import api.wadl.annotation.Paths;
import api.wadl.annotation.Resource;
import java.util.List;
import models.Cargo;
import utils.Result;
import controllers.restapi.DefaultController;

@Resource(name="cargos",param={@Param(name="{id}",type="int")})
public class Cargos extends DefaultController {
    
    
    @Path(method="GET",id="getAllCargo")
    public static void listAll(){
        List<Cargo> cargos = Cargo.findAll();			
        renderObject(Result.OK(cargos));
    }
    
    @Path(name="/{id}",method="GET",id="getCargoById")
    public static void findId(Long id) {
        if(id != null) {
            Cargo cargo = Cargo.findById(id);
            if(cargo!=null){
		renderObject(Result.OK(cargo));
            }else{
                //Registro nao encontrado
		renderObject(Result.ERROR(3));
            }
        }else{
            //Parametro nao informado
	    renderObject(Result.ERROR(4));
        }       
    }
    
    
    @Path(name="/{id}",method="DELETE",id="deleteCargo")
    public static void delete(Long id) {
        try {
            Cargo cargo = Cargo.findById(id);
            if(cargo!=null){
                cargo.delete();
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
            @Path(method="POST",id="addCargo" ,param={
                @Param(name="cargo.nome",type="string")
            }),
            @Path(name="/{id}",id="saveCargo",method="PUT", param={
                @Param(name="cargo.nome",type="string")
            })
        }
    )
    public static void save(Long id, Cargo cargo){
      
      if(id != null){
          Cargo cargo_aux = cargo;
          cargo = Cargo.findById(id);
          if(cargo != null){          
	      cargo.nome  = cargo_aux.nome;
          }else{
              //Registro nao encontrado
              renderObject(Result.ERROR(3));
          }
      }
      
      validation.valid(cargo);
      
      if (validation.hasErrors()) {
          renderObject(Result.VALIDERROR(validation));
          return;
      }
      
      Cargo obj_out = cargo.save();
      renderObject(Result.OK(obj_out));
      
    }    
}

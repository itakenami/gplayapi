package controllers;

import utils.Result;
import controllers.restapi.DefaultController;
import api.wadl.annotation.AnnotationSuport;

public class Wadl extends DefaultController {
    public static void generate() {
        AnnotationSuport.getInstance().addClass(controllers.Cargos.class);
		AnnotationSuport.getInstance().addClass(controllers.Analistas.class);
		AnnotationSuport.getInstance().addClass(controllers.Projetos.class);
        renderWadl();
    }
    
}

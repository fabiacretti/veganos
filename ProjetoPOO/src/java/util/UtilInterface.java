package util;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@SessionScoped
public class UtilInterface {
    
    public static void msgSucesso(String txt) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("SUCESSO!", txt));
    }

    public static void msgErro(String txt) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, txt, txt));
    }
}

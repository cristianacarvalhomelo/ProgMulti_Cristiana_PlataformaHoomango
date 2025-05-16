package com.hoomango.util;

import jakarta.faces.context.FacesContext;

public class SessionUtil {

    public static void setAtributo(String chave, Object valor) {
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put(chave, valor);
    }

    public static Object getAtributo(String chave) {
        return FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get(chave);
    }

    public static void removerAtributo(String chave) {
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .remove(chave);
    }

    public static void invalidarSessao() {
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .invalidateSession();
    }
}

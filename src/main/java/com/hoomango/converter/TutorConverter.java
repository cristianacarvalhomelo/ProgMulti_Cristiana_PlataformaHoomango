package com.hoomango.converter;

import com.hoomango.model.Tutor;
import com.hoomango.service.TutorService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("tutorConverter")
@ApplicationScoped
public class TutorConverter implements Converter<Tutor> {

    @Inject
    private TutorService tutorService;

    @Override
    public Tutor getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("TutorConverter.getAsObject: value=" + value);
        if (value == null || value.isEmpty())
            System.out.println("TutorConverter.getAsObject: value=" + value);
        assert value != null;
        return tutorService.buscarPorId(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Tutor tutor) {
        if (tutor == null || tutor.getId() == null) {
            return "";
        }
        return tutor.getId().toString();
    }
}

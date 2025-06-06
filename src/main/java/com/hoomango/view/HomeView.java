package com.hoomango.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.util.Arrays;
import java.util.List;

@Named
@RequestScoped
public class HomeView {

    public List<String> getImagens() {
        return Arrays.asList(
                "resources/images/banner10.avif",
                "resources/images/banner17.jpg",
                "resources/images/banner8.jpg",
                "resources/images/banner14.jpg",
                "resources/images/banner6.jpg",
                "resources/images/banner19.webp"
        );
    }
}

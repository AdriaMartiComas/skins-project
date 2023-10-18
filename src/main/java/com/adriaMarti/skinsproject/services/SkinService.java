package com.adriaMarti.skinsproject.services;

import com.adriaMarti.skinsproject.models.entity.Skin;
import com.adriaMarti.skinsproject.models.entity.SkinUtil;

import java.util.List;
import java.util.Optional;

public interface SkinService {

    //lista skins disponibles archivo JSON
    List<SkinUtil> listarDisponibles();

    //lista skins BD
    List<Skin> listarBD();

    //detalle skin BD
    Optional<Skin> mostrarPorId(Long id);

    //guardar o modificar skin BD
    Skin guardar(Skin skin);

    //eliminar skin BD
    void eliminar(Long id);

}

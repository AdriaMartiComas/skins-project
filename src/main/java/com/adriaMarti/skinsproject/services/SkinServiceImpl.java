package com.adriaMarti.skinsproject.services;

import com.adriaMarti.skinsproject.models.entity.Skin;
import com.adriaMarti.skinsproject.models.entity.SkinUtil;
import com.adriaMarti.skinsproject.repositories.SkinRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SkinServiceImpl implements SkinService {

    @Autowired
    private SkinRepository repository;

   private final String filePath = ".//data//SkinsDisponibles.json";


    @Override
    @Transactional(readOnly = true)
    public List<SkinUtil> listarDisponibles() {
        ObjectMapper objMap = new ObjectMapper();
        try {
            return objMap.readValue(new File(filePath), new TypeReference<List<SkinUtil>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Skin> listarBD() {
        return (List<Skin>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Skin> mostrarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Skin guardar(Skin skin) {
        return repository.save(skin);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }


}

package com.adriaMarti.skinsproject.controllers;

import com.adriaMarti.skinsproject.models.entity.Skin;
import com.adriaMarti.skinsproject.models.entity.SkinUtil;
import com.adriaMarti.skinsproject.services.SkinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/skins")
public class SkinController {
    @Autowired
    private SkinService skinService;

    //Devuelve una lista de todas las skins disponibles para comprar
    @GetMapping("/avaible")
    public ResponseEntity<?> avaibleSkins(){
        return ResponseEntity.ok(skinService.listarDisponibles());
    }

    //Devuelve una lista de las skins compradas por el usuario.
    @GetMapping("/myskins")
    public ResponseEntity<List<Skin>> mySkins(){
        return ResponseEntity.ok(skinService.listarBD());
    }

    //adquirir una skin y guardarla en la base de datos.
    @PostMapping("/buy")
    public ResponseEntity<?> buySkin(@RequestBody Skin skin){
        for (Skin skinObtenida: skinService.listarBD()) {
            if(skinObtenida.getNombre().equalsIgnoreCase(skin.getNombre())){
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("mensaje", "Ya as adquirido este skin"));
            }
        }
        //logica validacion de que el skin a comprar existe y de que el color elejido forma parte del skin
        if(skinPorNombre(skin.getNombre()) != null){
            SkinUtil skinexiste = skinPorNombre(skin.getNombre());
            Skin skinBD = new Skin();
            skinBD.setNombre(skin.getNombre());
            skinBD.setTipo(skinexiste.getTipo());
            skinBD.setPrecio(skinexiste.getPrecio());
            skinBD.setColor(skinexiste.getColor().get(0));
            if(existeColor(skinexiste, skin.getColor())){
                skinBD.setColor(skin.getColor());
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(skinService.guardar(skinBD));
        }
        return ResponseEntity.badRequest()
                .body(Collections
                        .singletonMap("mensaje", "el skin introducido no existe"));


    }

    //Permite a los usuarios cambiar el color de una skin comprada.
    @PutMapping("/color")
    public ResponseEntity<?> colorSkin(@RequestBody Skin skin){
        for (Skin skinObtenida: skinService.listarBD()) {
            if(skinObtenida.getNombre().equalsIgnoreCase(skin.getNombre())){
                SkinUtil skinexiste = skinPorNombre(skin.getNombre());
                if(existeColor(skinexiste, skin.getColor())){
                   skinObtenida.setColor(skin.getColor());
                   return ResponseEntity.ok(skinService.guardar(skinObtenida));
                }
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("mensaje", "La skin no tiene este color disponible"));
            }
        }
        return ResponseEntity.badRequest()
                .body(Collections
                        .singletonMap("mensaje", "No tienes esta skin"));
    }

    //Permite a los usuarios eliminar una skin comprada.
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> eliminarSkin(@PathVariable Long id){
        Optional<Skin> o = skinService.mostrarPorId(id);
        if (o.isPresent()){
            skinService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Devuelve una determinada skin.
    @GetMapping("/getskin/{id}")
    public ResponseEntity<?> detalleSkin(@PathVariable Long id){
        Optional<Skin> o = skinService.mostrarPorId(id);
        if (o.isPresent()){
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }


    //METODOS UTILES

    //validar una skin disponible por nombre
    private SkinUtil skinPorNombre(String nombre){
        List<SkinUtil> skins = skinService.listarDisponibles();
        for (SkinUtil skinUtil: skins) {
            if(skinUtil.getNombre().equalsIgnoreCase(nombre)){
                return skinUtil;
            }

        }
        return null;
    }

    //validar si un color esta disponible
    boolean existeColor(SkinUtil skinUtil, String color){
        List<String> colores = skinUtil.getColor();
        for (String colorexiste : colores) {
            if(colorexiste.equalsIgnoreCase(color)){
                return true;
            }
        }
        return false;
    }
}

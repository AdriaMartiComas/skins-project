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

@RestController
@RequestMapping("/skins")
public class SkinController {


    @Autowired
    private SkinService skinService;

    //GET /skins/avaible - Devuelve una lista de todas las skins disponibles para comprar
    @GetMapping("/avaible")
    public ResponseEntity<?> avaibleSkins(){
        return ResponseEntity.ok(skinService.listarDisponibles());
    }


    //GET /skins/myskins - Devuelve una lista de las skins compradas por el usuario.
    @GetMapping("/myskins")
    public ResponseEntity<List<Skin>> mySkins(){
        return ResponseEntity.ok(skinService.listarBD());
    }

    //POST /skins/buy - Permite a los usuarios adquirir una skin y guardarla en la base de
    //datos.
    @PostMapping("/buy")
    public ResponseEntity<?> buySkin(@RequestBody Skin skin){
        //logica validacion de que el skin a comprar existe y de que el color elejido forma parte del skin
        return ResponseEntity.ok(skinService.guardar(skin));
    }

    //PUT /skins/color - Permite a los usuarios cambiar el color de una skin comprada.


    //DELETE /skins/delete/{id} - Permite a los usuarios eliminar una skin comprada.


    //GET /skin/getskin/{id} – Devuelve una determinada skin.


    //metodo util para cargar info del archivo JSON
    private ResponseEntity<?> cargarSkins(){
        List<SkinUtil> skins = skinService.listarDisponibles();
        if(skins.isEmpty()){
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("mensaje", "error al cargar el archivo con los skins disponibles"));
        }
        return ResponseEntity.ok(skins);
    }
}

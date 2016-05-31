package br.pucminas.algotimizacao.config.controller;

import br.pucminas.algotimizacao.config.model.SimplexModel;
import com.codahale.metrics.annotation.Timed;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by anderson on 11/05/16.
 */
@RestController
@RequestMapping("/simplex")
public class SimplexController {

    /**
     * POST  /resolver -> resolve o simplex recebendo os valores solicitados
     */
    @RequestMapping(value = "/resolver",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    //@Timed
    public ResponseEntity<String> resolverSimplex (@RequestParam double[] fo){
           SimplexModel.init();
        return new ResponseEntity<>("Simplex resolvido!", HttpStatus.OK);
    }


}

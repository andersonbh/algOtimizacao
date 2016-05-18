package br.pucminas.algotimizacao.config.controller;

import br.pucminas.algotimizacao.config.model.SimplexModel;
import br.pucminas.algotimizacao.config.response.DataData;
import br.pucminas.algotimizacao.config.response.DataResponse;
import com.codahale.metrics.annotation.Timed;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by anderson on 11/05/16.
 */
@RestController
@RequestMapping("/simplex")
public class SimplexController {

    SimplexModel sm;

    /**
     * POST  /resolver -> resolve o simplex recebendo os valores solicitados
     */
    @RequestMapping(value = "/resolver", method = RequestMethod.POST)
    @ResponseBody
    public DataResponse resolverSimplex (@RequestParam (value = "fo[]") String fo[], @RequestParam (value = "res") String res ){
        double[] funcao = new double[fo.length];
        double[][] restricoes;
        for(int i = 0; i< fo.length;i++){
            funcao[i] = Double.parseDouble(fo[i]);
        }

        try {
            JSONArray arr = new JSONArray(res);

            restricoes = new double[arr.length()][arr.getJSONObject(0).length()];
            for(int i = 0; i < arr.length(); i++){
                for( int j = 0 ; j < arr.getJSONObject(0).length();j++){
                    restricoes[i][j] = arr.getJSONObject(i).getDouble("" + j);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DataData resposta = new DataData();
        resposta.add(funcao);
        resposta.setMessage("aeeee");
//           sm.resolverSimplex();
        return resposta;
    }



}

package br.pucminas.algotimizacao.config.controller;

import br.pucminas.algotimizacao.config.model.simplexModel;
import br.pucminas.algotimizacao.config.response.DataData;
import br.pucminas.algotimizacao.config.response.DataResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

/**
 * Created by anderson on 11/05/16.
 */
@RestController
@RequestMapping("/simplex")
public class SimplexController {

    simplexModel sm;
    /**
     * POST  /resolver -> resolve o simplex recebendo os valores solicitados
     */

    @RequestMapping(value = "/resolver", method = RequestMethod.POST)
    @ResponseBody
    public DataResponse resolverSimplex(@RequestParam (value = "tres[]") String tres[], @RequestParam (value = "fo[]") String fo[], @RequestParam (value = "res") String res, @RequestParam (value = "lim[]") String lim[], @RequestParam  (value = "maxMin") boolean  maxMin ){
        double[] funcao = new double[fo.length];
        double[][] restricoes;
        double[] totalRestricoes = new double[tres.length];
        int[] limites = new int[lim.length];

        for(int i = 0; i< fo.length;i++){
            funcao[i] = Double.parseDouble(fo[i]);
        }

        for(int i = 0; i< tres.length;i++){
            totalRestricoes[i] = Double.parseDouble(tres[i]);
            limites[i] = Integer.parseInt(lim[i]);
        }



        try {
            JSONArray arr = new JSONArray(res);

            restricoes = new double[arr.length()][arr.getJSONObject(0).length()];
            for(int i = 0; i < arr.length(); i++){
                for( int j = 0 ; j < arr.getJSONObject(0).length();j++){
                    restricoes[i][j] = arr.getJSONObject(i).getDouble("" + j);
                }

            }

            simplexModel.iniciar(funcao, restricoes, totalRestricoes, limites, maxMin);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DataData resposta = new DataData();
        resposta.add(funcao);
        return resposta;
    }



}

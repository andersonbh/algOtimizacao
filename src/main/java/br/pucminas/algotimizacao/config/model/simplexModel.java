package br.pucminas.algotimizacao.config.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anderson on 11/05/16.
 */
public class SimplexModel {

    Celula matResult[][];
    List<String> bas = new ArrayList<>();
    List<String> nbas = new ArrayList<>();

    public String resolverSimplex(/*double[] fo, double [][] restricoes*/){
        matResult = new Celula[3][4];
        matResult[0][0] = new Celula(0, -320);
        matResult[1][0] = new Celula(80, -20);
        matResult[2][0] = new Celula(60, -40);
        matResult[0][1] = new Celula(-24, 14);
        matResult[1][1] = new Celula(-4, 1);
        matResult[2][1] = new Celula(-6, 2);
        matResult[0][2] = new Celula(16, 4);
        matResult[1][2] = new Celula(4, (1/4));
        matResult[2][2] = new Celula(2, (1/2));
        matResult[0][3] = new Celula(3, 0);
        matResult[1][3] = new Celula(0, 0);
        matResult[2][3] = new Celula(1, 0);

        bas.add("x1");
        bas.add("x2");

        nbas.add("x3");
        nbas.add("x4");
        nbas.add("x5");

        troca(2,1);
        return "Simplex resolvido com sucesso";
    }

    public void troca(int i, int j){
        matResult[i][j].setN1(1/matResult[i][j].getN1());
        for(int a = 0; a < matResult[0].length; a++){
            if(a != j){
                matResult[i][a].setN2(matResult[i][j].getN2() * matResult[i][a].getN1());
            }
        }

        for(int b = 0; b < matResult.length; b++){
            if(b != i){
                matResult[b][j].setN2(matResult[i][j].getN1() * (-1) * matResult[b][j].getN1());
            }
        }

        for(int d = 0; d < matResult.length; d++){
            for(int f = 0; f < matResult[0].length; f++){
                if(d != i && f != j){
                    matResult[d][f].setN2(matResult[d][j].getN2() * matResult[i][f].getN1());
                    System.out.print(matResult[d][f].getN1() + "/" + matResult[d][f].getN2());
                }
                System.out.print("\n");
            }
        }

        String aux = bas.get(i - 1);
        bas.add(i - 1,nbas.get(j - 1));
        nbas.add(j - 1, aux);
    }

}

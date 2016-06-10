package br.pucminas.algotimizacao.config.entidades;

/**
 * Created by anderson on 09/06/16.
 */
public class Model {
    private double[][] tabela; // tabela (matriz)
    private int numeroDeRestricoes; // numero de restricoes
    private int tamanhoFuncaoObjetivo; // tamanho da funcao objetivo (numero de variaveis)

    public Model(double[][] restricoesLadoEsquerdo, double[] restricoesLadoDireito, int[] limites, double[] funcaoObjetivo) {
        numeroDeRestricoes = restricoesLadoDireito.length;
        tamanhoFuncaoObjetivo = funcaoObjetivo.length;
        tabela = new double[numeroDeRestricoes + 1][tamanhoFuncaoObjetivo
            + numeroDeRestricoes + 1];

        // inicializa restricoes
        for (int i = 0; i < numeroDeRestricoes; i++) {
            for (int j = 0; j < tamanhoFuncaoObjetivo; j++) {
                tabela[i][j] = restricoesLadoEsquerdo[i][j];
            }
        }

        for (int i = 0; i < numeroDeRestricoes; i++)
            tabela[i][numeroDeRestricoes + tamanhoFuncaoObjetivo] = restricoesLadoDireito[i];

        // inicializa limites de cada restricao
        for (int i = 0; i < numeroDeRestricoes; i++) {
            //Caso seja maior igual será -1, caso seja igual sera 0 e caso seja menor igual sera 1
            int limite = limites[i];
            tabela[i][tamanhoFuncaoObjetivo + i] = limite;
        }

        // inicializa a funcao objetivo
        for (int j = 0; j < tamanhoFuncaoObjetivo; j++)
            tabela[numeroDeRestricoes][j] = funcaoObjetivo[j];
    }

    public double[][] getTabela() {
        return tabela;
    }

    public int getNumeroDeRestricoes() {
        return numeroDeRestricoes;
    }

    public int getTamanhoFuncaoObjetivo() {
        return tamanhoFuncaoObjetivo;
    }

}

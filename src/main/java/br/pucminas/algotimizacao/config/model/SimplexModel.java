package br.pucminas.algotimizacao.config.model;
import br.pucminas.algotimizacao.config.entidades.Model;

/**
 * Created by anderson on 11/05/16.
 */
public class SimplexModel {

    private double[][] tabela; // tabela
    private int numeroDeRestricoes; // numero de restricoes
    private int tamanhoFuncaoObjetivo; // tamanho da funcao objetivo

    private boolean maximizarMinimizar; // caso queira maximizar sera true, senao false

    private int[] variaveisBasicas; // variaveisBasicas[i] = variaveis basicas da posicao i

    public SimplexModel(double[][] tabela, int numeroDeRestricoes, int tamanhoFuncaoObjetivo, boolean maximizarMinimizar) {
        this.maximizarMinimizar = maximizarMinimizar;
        this.numeroDeRestricoes = numeroDeRestricoes;
        this.tamanhoFuncaoObjetivo = tamanhoFuncaoObjetivo;
        this.tabela = tabela;

        variaveisBasicas = new int[numeroDeRestricoes];
        for (int i = 0; i < numeroDeRestricoes; i++)
            variaveisBasicas[i] = tamanhoFuncaoObjetivo + i;

        resolverSimplex();
    }

    /**
     * @param funcaoObjetivo é a funcao objetivo
     * @param restricoesLadoEsquerdo é o lado esquerdo da equacao, antes do limite
     * @param restricoesLadoDireito é o lado direito da equacao, depois do limite
     * @param limites são os limites(sinais), menor igual é 1, igual é 0 e maior igual é -1
     * @param maxMin pode ser true para Maximizar ou false para Minimizar
     */
    public static String[] iniciar(double[] funcaoObjetivo, double[][] restricoesLadoEsquerdo, double [] restricoesLadoDireito, int[] limites, boolean maxMin) {
        Model model = new Model(restricoesLadoEsquerdo, restricoesLadoDireito, limites, funcaoObjetivo);

        SimplexModel simplex = new SimplexModel(model.getTabela(), model.getNumeroDeRestricoes(), model.getTamanhoFuncaoObjetivo(), maxMin);
        double[] x = simplex.primal();

        String[] resultado = new String[x.length + 1];
        for (int i = 0; i < x.length; i++) {
            resultado [i]= "x[" + (i + 1) + "] = " + x[i]  ;
            System.out.println("x[" + (i + 1) + "] = " + x[i]);
        }

        resultado [x.length] = "Solucao: " + simplex.getSolucao();
        System.out.println("Solucao: " + simplex.getSolucao());

        return resultado;
    }

    private void resolverSimplex() {
        while (true) {
            mostrar();
            int q = 0;

            // Encontra coluna q
            if (maximizarMinimizar) {
                q = getMaiorPositivo();
            } else {
                q = getMenorNegativo();
            }
            if (q == -1)
                break; // optimal

            // Encontra linha p
            int p = minRatioRule(q);
            if (p == -1)
                throw new ArithmeticException("Solucao Impossível");

            // Pivo
            pivot(p, q);

            // Atualiza as variaveis basicas
            variaveisBasicas[p] = q;
        }
    }

    // Index da coluna nao-basica com maior custo positivo
    private int getMaiorPositivo() {
        int q = 0;
        for (int j = 1; j < numeroDeRestricoes + tamanhoFuncaoObjetivo; j++)
            if (tabela[numeroDeRestricoes][j] > tabela[numeroDeRestricoes][q])
                q = j;

        if (tabela[numeroDeRestricoes][q] <= 0)
            return -1;
        else
            return q;
    }

    // Index da coluna nao basica com custo mais negativo
    private int getMenorNegativo() {
        int q = 0;
        for (int j = 1; j < numeroDeRestricoes + tamanhoFuncaoObjetivo; j++)
            if (tabela[numeroDeRestricoes][j] < tabela[numeroDeRestricoes][q])
                q = j;

        if (tabela[numeroDeRestricoes][q] >= 0)
            return -1; // Otimo
        else
            return q;
    }

    // Encontra linha p usando a regra da taxa minima (-1 se nao existe)
    private int minRatioRule(int q) {
        int p = -1;
        for (int i = 0; i < numeroDeRestricoes; i++) {
            if (tabela[i][q] <= 0)
                continue;
            else if (p == -1)
                p = i;
            else if ((tabela[i][numeroDeRestricoes + tamanhoFuncaoObjetivo] / tabela[i][q]) < (tabela[p][numeroDeRestricoes + tamanhoFuncaoObjetivo] / tabela[p][q]))
                p = i;
        }
        return p;
    }

    // Pivota a entrada (p, q) usando eliminacao
    private void pivot(int p, int q) {
        // Tudo alem da linha p e coluna q
        for (int i = 0; i <= numeroDeRestricoes; i++)
            for (int j = 0; j <= numeroDeRestricoes
                + tamanhoFuncaoObjetivo; j++)
                if (i != p && j != q)
                    tabela[i][j] -= tabela[p][j] * tabela[i][q] / tabela[p][q];

        // Coloca zero na coluna q
        for (int i = 0; i <= numeroDeRestricoes; i++)
            if (i != p)
                tabela[i][q] = 0.0;

        // Escala linha p
        for (int j = 0; j <= numeroDeRestricoes + tamanhoFuncaoObjetivo; j++)
            if (j != q)
                tabela[p][j] /= tabela[p][q];

        tabela[p][q] = 1.0;
    }

    // Retorna solucao
    public double getSolucao() {
        return -tabela[numeroDeRestricoes][numeroDeRestricoes + tamanhoFuncaoObjetivo];
    }

    // Retorna vetor de solucao primal
    public double[] primal() {
        double[] x = new double[tamanhoFuncaoObjetivo];
        for (int i = 0; i < numeroDeRestricoes; i++)
            if (variaveisBasicas[i] < tamanhoFuncaoObjetivo)
                x[variaveisBasicas[i]] = tabela[i][numeroDeRestricoes + tamanhoFuncaoObjetivo];
        return x;
    }

    // Exibe tabela
    public void mostrar() {
        System.out.println("M = " + numeroDeRestricoes);
        System.out.println("N = " + tamanhoFuncaoObjetivo);
        for (int i = 0; i <= numeroDeRestricoes; i++) {
            for (int j = 0; j <= numeroDeRestricoes + tamanhoFuncaoObjetivo; j++) {
                System.out.printf("%7.2f ", tabela[i][j]);
            }
            System.out.println();
        }

        System.out.println("Solucao = " + getSolucao());
        for (int i = 0; i < numeroDeRestricoes; i++)
            if (variaveisBasicas[i] < tamanhoFuncaoObjetivo)
                System.out.println("x_" + variaveisBasicas[i] + " = " + tabela[i][numeroDeRestricoes + tamanhoFuncaoObjetivo]);

        System.out.println();
    }
}

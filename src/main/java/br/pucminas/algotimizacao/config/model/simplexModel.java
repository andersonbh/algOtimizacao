package br.pucminas.algotimizacao.config.model;
import br.pucminas.algotimizacao.config.entidades.Model;

/**
 * Created by anderson on 11/05/16.
 */
public class SimplexModel {

    private double[][] tabela; // tabela
    private int numeroDeRestricoes; // numero de restricoes
    private int tamanhoFuncaoObjetivo; // tamanho da funcao objetivo


    private boolean maximizarMinimizar; // boolean, caso queira maximizar sera true, senao false

    private int[] variaveisBasicas; // variaveisBasicas[i] = variaveis basicas da posicao i

    public SimplexModel(double[][] tabela, int numberOfConstraint, int numberOfOriginalVariable, boolean maximizeOrMinimize) {
        this.maximizarMinimizar = maximizeOrMinimize;
        this.numeroDeRestricoes = numberOfConstraint;
        this.tamanhoFuncaoObjetivo = numberOfOriginalVariable;
        this.tabela = tabela;

        variaveisBasicas = new int[numeroDeRestricoes];
        for (int i = 0; i < numeroDeRestricoes; i++)
            variaveisBasicas[i] = tamanhoFuncaoObjetivo + i;

        resolverSimplex();
    }


    /**
     *
     * @param funcaoObjetivo é a funcao objetivo
     * @param restricoesLadoEsquerdo é o lado esquerdo da equacao, antes do limite
     * @param restricoesLadoDireito é o lado direito da equacao, depois do limite
     * @param limites são os limites(sinais), menor igual é 1, igual é 0 e maior igual é -1
     * @param maxMin pode ser true para Maximizar ou false para Minimizar
     */
    public static void iniciar(double[] funcaoObjetivo, double[][] restricoesLadoEsquerdo, double [] restricoesLadoDireito, int[] limites, boolean maxMin) {

        /*
        Funcao exemplo testada:
        MaxZ = 40 X1 + 30 X2
        3 X1 + 4 X2 <= 12
        7 X1 + 2 X 2 <= 14
        X1, X2 >= 0

        Resultado:
        MaxZ= 115,45
        X1= 1,91
        X2= 1,45
        X3= 0
        X4= 0
        */

        Model model = new Model(restricoesLadoEsquerdo, restricoesLadoDireito, limites, funcaoObjetivo);

        SimplexModel simplex = new SimplexModel(model.getTabela(), model.getNumeroDeRestricoes(), model.getTamanhoFuncaoObjetivo(), maxMin);
        double[] x = simplex.primal();
        for (int i = 0; i < x.length; i++) {
            System.out.println("x[" + i + "] = " + x[i]);
        }

        System.out.println("Solucao: " + simplex.value());
    }

    private void resolverSimplex() {
        while (true) {

            mostrar();
            int q = 0;
            // find entering column q
            if (maximizarMinimizar) {
                q = dantzig();
            } else {
                q = dantzigNegative();
            }
            if (q == -1)
                break; // optimal

            // find leaving row p
            int p = minRatioRule(q);
            if (p == -1)
                throw new ArithmeticException("Linear program is unbounded");

            // pivot
            pivot(p, q);

            // update variaveisBasicas
            variaveisBasicas[p] = q;

        }
    }

    // index of a non-basic column with most positive cost
    private int dantzig() {
        int q = 0;
        for (int j = 1; j < numeroDeRestricoes + tamanhoFuncaoObjetivo; j++)
            if (tabela[numeroDeRestricoes][j] > tabela[numeroDeRestricoes][q])
                q = j;

        if (tabela[numeroDeRestricoes][q] <= 0)
            return -1;
        else
            return q;
    }

    // index of a non-basic column with most negative cost
    private int dantzigNegative() {
        int q = 0;
        for (int j = 1; j < numeroDeRestricoes + tamanhoFuncaoObjetivo; j++)
            if (tabela[numeroDeRestricoes][j] < tabela[numeroDeRestricoes][q])
                q = j;

        if (tabela[numeroDeRestricoes][q] >= 0)
            return -1; // optimal
        else
            return q;
    }

    // find row p using min ratio rule (-1 if no such row)
    private int minRatioRule(int q) {
        int p = -1;
        for (int i = 0; i < numeroDeRestricoes; i++) {
            if (tabela[i][q] <= 0)
                continue;
            else if (p == -1)
                p = i;
            else if ((tabela[i][numeroDeRestricoes
                + tamanhoFuncaoObjetivo] / tabela[i][q]) < (tabela[p][numeroDeRestricoes
                + tamanhoFuncaoObjetivo] / tabela[p][q]))
                p = i;
        }
        return p;
    }

    // pivot on entry (p, q) using Gauss-Jordan elimination
    private void pivot(int p, int q) {

        // everything but row p and column q
        for (int i = 0; i <= numeroDeRestricoes; i++)
            for (int j = 0; j <= numeroDeRestricoes
                + tamanhoFuncaoObjetivo; j++)
                if (i != p && j != q)
                    tabela[i][j] -= tabela[p][j] * tabela[i][q]
                        / tabela[p][q];

        // zero out column q
        for (int i = 0; i <= numeroDeRestricoes; i++)
            if (i != p)
                tabela[i][q] = 0.0;

        // scale row p
        for (int j = 0; j <= numeroDeRestricoes + tamanhoFuncaoObjetivo; j++)
            if (j != q)
                tabela[p][j] /= tabela[p][q];
        tabela[p][q] = 1.0;
    }

    // return optimal objective value
    public double value() {
        return -tabela[numeroDeRestricoes][numeroDeRestricoes
            + tamanhoFuncaoObjetivo];
    }

    // return primal solution vector
    public double[] primal() {
        double[] x = new double[tamanhoFuncaoObjetivo];
        for (int i = 0; i < numeroDeRestricoes; i++)
            if (variaveisBasicas[i] < tamanhoFuncaoObjetivo)
                x[variaveisBasicas[i]] = tabela[i][numeroDeRestricoes
                    + tamanhoFuncaoObjetivo];
        return x;
    }

    // print tabela
    public void mostrar() {
        System.out.println("M = " + numeroDeRestricoes);
        System.out.println("N = " + tamanhoFuncaoObjetivo);
        for (int i = 0; i <= numeroDeRestricoes; i++) {
            for (int j = 0; j <= numeroDeRestricoes
                + tamanhoFuncaoObjetivo; j++) {
                System.out.printf("%7.2f ", tabela[i][j]);
            }
            System.out.println();
        }
        System.out.println("value = " + value());
        for (int i = 0; i < numeroDeRestricoes; i++)
            if (variaveisBasicas[i] < tamanhoFuncaoObjetivo)
                System.out.println("x_"
                    + variaveisBasicas[i]
                    + " = "
                    + tabela[i][numeroDeRestricoes
                    + tamanhoFuncaoObjetivo]);
        System.out.println();
    }
}

package br.pucminas.algotimizacao.config.model;

/**
 * Created by anderson on 11/05/16.
 */
public class SimplexModel {

    private double[][] tableaux; // tableaux
    private int numberOfConstraints; // number of constraints
    private int numberOfOriginalVariables; // number of original variables


    private boolean maximizeOrMinimize;

    private int[] basis; // basis[i] = basic variable corresponding to row i

    public SimplexModel(double[][] tableaux, int numberOfConstraint, int numberOfOriginalVariable, boolean maximizeOrMinimize) {
        this.maximizeOrMinimize = maximizeOrMinimize;
        this.numberOfConstraints = numberOfConstraint;
        this.numberOfOriginalVariables = numberOfOriginalVariable;
        this.tableaux = tableaux;

        basis = new int[numberOfConstraints];
        for (int i = 0; i < numberOfConstraints; i++)
            basis[i] = numberOfOriginalVariables + i;

        solve();
    }


    /**
     *
     * @param funcaoObjetivo é a funcao objetivo
     * @param constraintLeftSide é o lado esquerdo da equacao, antes do limite
     * @param constraintRightSide é o lado direito da equacao, depois do limite
     * @param limites são os limites(sinais), menor igual é 1, igual é 0 e maior igual é -1
     * @param maxMin pode ser true para Maximizar ou false para Minimizar
     */
    public static void init(double[] funcaoObjetivo, double[][] constraintLeftSide, double [] constraintRightSide, int[] limites, boolean maxMin) {

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

        Modeler model = new Modeler(constraintLeftSide, constraintRightSide, limites, funcaoObjetivo);

        SimplexModel simplex = new SimplexModel(model.getTableaux(), model.getNumberOfConstraint(), model.getNumberOfOriginalVariable(), maxMin);
        double[] x = simplex.primal();
        for (int i = 0; i < x.length; i++) {
            System.out.println("x[" + i + "] = " + x[i]);
        }

        System.out.println("Solucao: " + simplex.value());
    }


    // run simplex algorithm starting from initial BFS
    private void solve() {
        while (true) {

            show();
            int q = 0;
            // find entering column q
            if (maximizeOrMinimize) {
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

            // update basis
            basis[p] = q;

        }
    }

    // index of a non-basic column with most positive cost
    private int dantzig() {
        int q = 0;
        for (int j = 1; j < numberOfConstraints + numberOfOriginalVariables; j++)
            if (tableaux[numberOfConstraints][j] > tableaux[numberOfConstraints][q])
                q = j;

        if (tableaux[numberOfConstraints][q] <= 0)
            return -1; // optimal
        else
            return q;
    }

    // index of a non-basic column with most negative cost
    private int dantzigNegative() {
        int q = 0;
        for (int j = 1; j < numberOfConstraints + numberOfOriginalVariables; j++)
            if (tableaux[numberOfConstraints][j] < tableaux[numberOfConstraints][q])
                q = j;

        if (tableaux[numberOfConstraints][q] >= 0)
            return -1; // optimal
        else
            return q;
    }

    // find row p using min ratio rule (-1 if no such row)
    private int minRatioRule(int q) {
        int p = -1;
        for (int i = 0; i < numberOfConstraints; i++) {
            if (tableaux[i][q] <= 0)
                continue;
            else if (p == -1)
                p = i;
            else if ((tableaux[i][numberOfConstraints
                + numberOfOriginalVariables] / tableaux[i][q]) < (tableaux[p][numberOfConstraints
                + numberOfOriginalVariables] / tableaux[p][q]))
                p = i;
        }
        return p;
    }

    // pivot on entry (p, q) using Gauss-Jordan elimination
    private void pivot(int p, int q) {

        // everything but row p and column q
        for (int i = 0; i <= numberOfConstraints; i++)
            for (int j = 0; j <= numberOfConstraints
                + numberOfOriginalVariables; j++)
                if (i != p && j != q)
                    tableaux[i][j] -= tableaux[p][j] * tableaux[i][q]
                        / tableaux[p][q];

        // zero out column q
        for (int i = 0; i <= numberOfConstraints; i++)
            if (i != p)
                tableaux[i][q] = 0.0;

        // scale row p
        for (int j = 0; j <= numberOfConstraints + numberOfOriginalVariables; j++)
            if (j != q)
                tableaux[p][j] /= tableaux[p][q];
        tableaux[p][q] = 1.0;
    }

    // return optimal objective value
    public double value() {
        return -tableaux[numberOfConstraints][numberOfConstraints
            + numberOfOriginalVariables];
    }

    // return primal solution vector
    public double[] primal() {
        double[] x = new double[numberOfOriginalVariables];
        for (int i = 0; i < numberOfConstraints; i++)
            if (basis[i] < numberOfOriginalVariables)
                x[basis[i]] = tableaux[i][numberOfConstraints
                    + numberOfOriginalVariables];
        return x;
    }

    // print tableaux
    public void show() {
        System.out.println("M = " + numberOfConstraints);
        System.out.println("N = " + numberOfOriginalVariables);
        for (int i = 0; i <= numberOfConstraints; i++) {
            for (int j = 0; j <= numberOfConstraints
                + numberOfOriginalVariables; j++) {
                System.out.printf("%7.2f ", tableaux[i][j]);
            }
            System.out.println();
        }
        System.out.println("value = " + value());
        for (int i = 0; i < numberOfConstraints; i++)
            if (basis[i] < numberOfOriginalVariables)
                System.out.println("x_"
                    + basis[i]
                    + " = "
                    + tableaux[i][numberOfConstraints
                    + numberOfOriginalVariables]);
        System.out.println();
    }
}

class Modeler {
    private double[][] a; // tableaux
    private int numberOfConstraints; // number of constraints
    private int numberOfOriginalVariables; // number of original variables

    public Modeler(double[][] constraintLeftSide, double[] constraintRightSide, int[] limites, double[] objectiveFunction) {
        numberOfConstraints = constraintRightSide.length;
        numberOfOriginalVariables = objectiveFunction.length;
        a = new double[numberOfConstraints + 1][numberOfOriginalVariables
            + numberOfConstraints + 1];

        // initialize constraint
        for (int i = 0; i < numberOfConstraints; i++) {
            for (int j = 0; j < numberOfOriginalVariables; j++) {
                a[i][j] = constraintLeftSide[i][j];
            }
        }

        for (int i = 0; i < numberOfConstraints; i++)
            a[i][numberOfConstraints + numberOfOriginalVariables] = constraintRightSide[i];

        // initialize slack variable
        for (int i = 0; i < numberOfConstraints; i++) {
            //Caso seja maior igual será -1, caso seja igual sera 0 e caso seja menor igual sera 1
            int slack = limites[i];
            a[i][numberOfOriginalVariables + i] = slack;
        }

        // initialize objective function
        for (int j = 0; j < numberOfOriginalVariables; j++)
            a[numberOfConstraints][j] = objectiveFunction[j];
    }

    public double[][] getTableaux() {
        return a;
    }

    public int getNumberOfConstraint() {
        return numberOfConstraints;
    }

    public int getNumberOfOriginalVariable() {
        return numberOfOriginalVariables;
    }

}

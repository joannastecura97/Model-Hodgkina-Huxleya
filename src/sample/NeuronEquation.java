package sample;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

/**
 * Class used for computing derivatives.
 */
public class NeuronEquation implements FirstOrderDifferentialEquations {

    /** @hidden */
    private static int counter = 0;
    /** @hidden */
    private boolean isEven = false;

    /**
     * Voltage of neuron.
     */
    private double u = 0;

    /**
     * Current applied to the neuron after time delay.
     */
    private static Double I = 0.0;

    /**
     * Capacity of the neuron.
     */
    private static double c = 1.0;

    /**
     * Sodium canals potential difference.
     */
    private static double eNa = 115.0;

    /**
     * Potassium canals potential difference.
     */
    private static double eK = -12.0;

    /**
     * Flux potential difference.
     */
    private static double eL = 10.6;

    /**
     * Conductance of sodium canals.
     */
    private static double gNa = 120;

    /**
     * Conductance of potassium canals.
     */
    private static double gK = 36.0;

    /**
     * Conductance of flux.
     */
    private static double gL = 0.3;

    /**
     * Default constructor.
     */
    public NeuronEquation() {
    }


    /**
     * Method used for setting static fields values.
     * @param I Current applied to the neuron after time delay.
     * @param c Capacity of the neuron.
     * @param eNa Sodium canals potential difference.
     * @param eK Potassium canals potential difference.
     * @param eL Flux potential difference.
     * @param gNa Conductance of sodium canals.
     * @param gK Conductance of potassium canals.
     * @param gL Conductance of flux.
     */
    public void setValues (double I, double c,double eNa, double eK, double eL,double gNa, double gK, double gL){
        NeuronEquation.I=I;
        NeuronEquation.c=c;
        NeuronEquation.eNa=eNa;
        NeuronEquation.eK=eK;
        NeuronEquation.eL=eL;
        NeuronEquation.gNa=gNa;
        NeuronEquation.gK=gK;
        NeuronEquation.gL=gL;

    }



    //Random r = new Random();

    /**
     * Method used for gaining access to voltage.
     * @return voltage
     */
    public double getU() {
        return u;
    }

    /**
     * Method ised for setting the voltage.
     * @param u voltage
     */
    public void setU(double u) {
        this.u = u;
    }

    /**
     * Method sued for getting the value of current.
     * @return current
     */
    public static Double getI() {
        return I;
    }

    /**
     * Method used for setting the value of current.
     * @param i current
     */
    public static void setI(Double i) {
        I = i;
    }

    /**
     * Method used for calculating the sodium current.
     * @param m m parameter.
     * @param h h parameter.
     * @param u voltage.
     * @return sodium current.
     */
    public static double getINa(double m, double h, double u){
        return gNa*Math.pow(m, 3)*h*(u-eNa);
    }

    /**
     * Method used for calculating the potassium current.
     * @param n n parameter.
     * @param u voltage.
     * @return potassium current.
     */
    public static double getIK(double n, double u){
        return gK*Math.pow(n, 4)*(u-eK);
    }

    /**
     * Method used for calculating the flux current.
     * @param u voltage.
     * @return flux current.
     */
    public static double getIL(double u){
        return gL*(u-eL);
    }

    /**
     * Method used for calculating initial condition for parameter m.
     * @return initial condition for parameter m.
     */
    public double M0(){
        double alphaM = 0.1*(25.0-u)/(Math.exp((25.0-u)/10.0)-1.0);
        double betaM = 4*Math.exp(-u/18.0);
        return alphaM/(alphaM + betaM);
    }

    /**
     * Method used for calculating initial condition for parameter n.
     * @return initial condition for parameter n.
     */
    public double N0(){
        double alphaN = 0.01*(10.0 - u)/(Math.exp((10.0-u)/10.0)-1);
        double betaN = 0.125*Math.exp((-u)/80.0);
        return alphaN/(alphaN + betaN);
    }

    /**
     * Method used for calculating initial condition for parameter h.
     * @return initial condition for parameter h.
     */
    public double H0(){
        double alphaH =0.07*Math.exp(-u/20.0);
        double betaH = 1/(Math.exp((30.0-u)/10.0)+1);
        return alphaH/(alphaH + betaH);
    }

    /**
     * Method used for returning the length of the differential equation vector.
     * @return length of differential equation vector.
     */
    @Override
    public int getDimension() {
        return 4;
    }

    /**
     * Method used for defining neuron differential equations.
     * @param t current value of the independent time variable
     * @param x array containing the current value of the state vector
     * @param dxdt placeholder array where the time derivative of the state vector are present
     * @throws MaxCountExceededException if the number of functions evaluations is exceeded
     * @throws DimensionMismatchException if arrays dimensions do not match equations settings
     */
    @Override
    public void computeDerivatives(double t, double[] x, double[] dxdt) throws MaxCountExceededException, DimensionMismatchException {

        //Double I = (NeuronEquation.I != 0) ? NeuronEquation.I + r.nextDouble() : NeuronEquation.I;
        //http://worldcomp-proceedings.com/proc/p2013/BIC3207.pdf

        double alphaM, betaM, alphaN, betaN, alphaH, betaH;

        alphaM = 0.1 * (25.0 - x[0]) / (Math.exp((25.0 - x[0]) / 10.0) - 1.0);
        betaM = 4 * Math.exp(-x[0] / 18.0);

        alphaN = 0.01 * (10.0 - x[0]) / (Math.exp((10.0 - x[0]) / 10.0) - 1);
        betaN = 0.125 * Math.exp((-x[0]) / 80.0);

        alphaH = 0.07 * Math.exp(-x[0] / 20.0);
        betaH = 1 / (Math.exp((30.0 - x[0]) / 10.0) + 1);

        dxdt[0] = (-(gNa * Math.pow(x[1], 3) * x[3] * (x[0] - eNa) + gK * Math.pow(x[2], 4) * (x[0] - eK) + gL * (x[0] - eL)) + I) / c;
        dxdt[1] = alphaM * (1 - x[1]) - betaM * x[1];
        dxdt[2] = alphaN * (1 - x[2]) - betaN * x[2];
        dxdt[3] = alphaH * (1 - x[3]) - betaH * x[3];
    }
}

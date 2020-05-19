package sample;

import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.events.EventHandler;
import org.apache.commons.math3.ode.nonstiff.ClassicalRungeKuttaIntegrator;

import java.util.ArrayList;

/**
 * Class used for <code>NeuronEquation</code>'s differential equation integration.
 */
public class Integrator {


    /**
     * Current applied to the neuron after time delay.
     */
    private double i = 0.0;

    /**
     * Capacity of the neuron.
     */
    private double c = 1.0;

    /**
     * Sodium canals potential difference.
     */
    private double eNa = 115.0;

    /**
     * Potassium canals potential difference.
     */
    private double eK = -12.0;

    /**
     * Flux potential difference.
     */
    private double eL = 10.6;

    /**
     * Conductance of sodium canals.
     */
    private double gNa = 120;

    /**
     * Conductance of potassium canals.
     */
    private double gK = 36.0;

    /**
     * Conductance of flux.
     */
    private double gL = 0.3;


    /**
     * Default constructor.
     */
    public Integrator(){}

    /**
     * Constructor setting current value.
     * @param i current
     */
    public Integrator(double i) {
        this.i = i;
    }

    /**
     * Constructor setting all parameters.
     * @param i Current applied to the neuron after time delay.
     * @param c Capacity of the neuron.
     * @param eNa Sodium canals potential difference.
     * @param eK Potassium canals potential difference.
     * @param eL Flux potential difference.
     * @param gNa Conductance of sodium canals.
     * @param gK Conductance of potassium canals.
     * @param gL Conductance of flux.
     */
    public Integrator(double i, double c, double eNa, double eK, double eL, double gNa, double gK, double gL) {
        this.i = i;
        this.c = c;
        this.eNa = eNa;
        this.eK = eK;
        this.eL = eL;
        this.gNa = gNa;
        this.gK = gK;
        this.gL = gL;
    }

    /** @hidden */
    private FirstOrderIntegrator integrator = new ClassicalRungeKuttaIntegrator(0.05);


    /**
     * Method used for integrating neuron differential equation by the use of classical Runge-Kutta integration. Calculated values for each step are
     * being sent to <code>MyStepHandler</code> class, where they are available in static series.
     */
    public void integrate() {

        NeuronEquation neuronEquation = new NeuronEquation();
        neuronEquation.setValues(0, c, eNa, eK, eL, gNa, gK, gL);
        ArrayList<TurningPoint> ehAr = new ArrayList<>();

        ehAr.add(new TurningPoint(10, i));
        ehAr.add(new TurningPoint(80, 0.0));

        integrator.addEventHandler(ehAr.get(0), 0.001, 0.001, 2000);
        integrator.addEventHandler(ehAr.get(0 + 1), 0.001, 0.001, 2000);

        double[] xStart = {neuronEquation.M0(), neuronEquation.N0(), neuronEquation.H0(), 0};
        double[] xStop = {1, 1, 1, -150};

        integrator.addStepHandler(new MyStepHandler());
        integrator.integrate(neuronEquation, 0, xStart, 100, xStop);

    }


}

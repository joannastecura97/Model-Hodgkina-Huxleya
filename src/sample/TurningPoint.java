package sample;

import org.apache.commons.math3.ode.events.EventHandler;


/**
 * Class used for changing current after desired period of time.
 */
public class TurningPoint implements EventHandler {

    /**
     * Interval of time that is intended to pass before attaching the current.
     */
    private int i;

    /**
     * Value of current that is intended to be set after interval <code>i</code> has been passed.
     */
    private double j;

    /**
     * Constructor setting parameters.
     * @param i Interval of time that is intended to pass before attaching the current.
     * @param j Value of current that is intended to be set after interval <code>i</code> has been passed.
     */
    public TurningPoint(int i, double j) {
        this.i = i;
        this.j = j;
    }


    /** @hidden */
    @Override
    public void init(double t, double[] x, double dxdt) { }

    /**
     * Method calculating period between passed time and the period set.
     * @param t current value of the independent time variable
     * @param x array containing the current value of the state vector
     * @return value of the g switching function
     */
    @Override
    public double g(double t, double[] x) {
        return t-i;
    }

    /**
     * Method launched, when <code>public double g(double t, double[] x)</code> method returns 0.0.
     * @param t current value of the independent time variable
     * @param x array containing the current value of the state vector
     * @param b if true, the value of the switching function increases when times increases around event (note that increase is measured with respect to physical time, not with respect to integration which may go backward in time
     * @return <code>Action.RESET_STATE</code>
     */
    @Override
    public Action eventOccurred(double t, double[] x, boolean b) {
        return Action.RESET_STATE;
    }

    /**
     * Method launching, when <code>Action.RESET_STATE</code> is returned by the <code>public Action eventOccurred(double t, double[] x, boolean b)</code> method.
     * @param t current value of the independent time variable
     * @param x array containing the current value of the state vector the new state should be put in the same array
     */
    @Override
    public void resetState(double t, double[] x) {
        NeuronEquation.setI(j);
    }
}

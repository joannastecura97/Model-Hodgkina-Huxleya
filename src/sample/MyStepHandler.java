package sample;

import javafx.scene.chart.XYChart;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.sampling.StepHandler;
import org.apache.commons.math3.ode.sampling.StepInterpolator;

import java.util.ArrayList;

/**
 * Class used for collecting values after following steps of integration and putting them in appropriate series.
 */
public class MyStepHandler implements StepHandler {

    /**
     * Series storing voltage in time function values.
     */
    private static XYChart.Series<Double, Double> seriesUT = new XYChart.Series<>();

    /**
     * Series storing m parameter in time function values.
     */
    private static XYChart.Series<Double, Double> mSeriesT = new XYChart.Series<>();

    /**
     * Series storing n parameter in time function values.
     */
    private static XYChart.Series<Double, Double> nSeriesT = new XYChart.Series<>();

    /**
     * Series storing h parameter in time function values.
     */
    private static XYChart.Series<Double, Double> hSeriesT = new XYChart.Series<>();

    /**
     * Series storing sodium current in time function values.
     */
    private static XYChart.Series<Double, Double> NaSeries = new XYChart.Series<>();

    /**
     * Series storing potassium current in time function values.
     */
    private static XYChart.Series<Double, Double> KSeries = new XYChart.Series<>();

    /**
     * Series storing flux current in time function values.
     */
    private static XYChart.Series<Double, Double> LSeries = new XYChart.Series<>();


    /**
     * ArrayList storing time values.
     */
    private static ArrayList<Double> arrayT = new ArrayList<>();

    /**
     * ArrayList storing voltage values.
     */
    private static ArrayList<Double> arrayU = new ArrayList<>();


    /**
     * Method used for gaining access to sodium current in time series.
     * @return sodium current in time series.
     */
    public static XYChart.Series<Double, Double> getNaSeries() {
        return NaSeries;
    }

    /**
     * Method used for gaining access to potassium current in time series.
     * @return potassium current in time series.
     */
    public static XYChart.Series<Double, Double> getKSeries() {
        return KSeries;
    }

    /**
     * Method used for gaining access to flux current in time series.
     * @return flux current in time series.
     */
    public static XYChart.Series<Double, Double> getLSeries() {
        return LSeries;
    }

    /**
     * Method used for gaining access to voltage in time series.
     * @return voltage in time series.
     */
    public static XYChart.Series<Double, Double> getSeriesUT() {
        return seriesUT;
    }

    /**
     * Method used for gaining access to m parameter in time series.
     * @return m parameter in time series.
     */
    public static XYChart.Series<Double, Double> getmSeriesT() {
        return mSeriesT;
    }

    /**
     * Method used for gaining access to n parameter in time series.
     * @return n parameter in time series.
     */
    public static XYChart.Series<Double, Double> getnSeriesT() {
        return nSeriesT;
    }

    /**
     * Method used for gaining access to h parameter in time series.
     * @return h parameter in time series.
     */
    public static XYChart.Series<Double, Double> gethSeriesT() {
        return hSeriesT;
    }

    /**
     * Method used for gaining access to time array list.
     * @return time array list.
     */
    public static ArrayList<Double> getArrayT() {
        return arrayT;
    }

    /**
     * Method used for gaining access to voltage array list.
     * @return voltage array list.
     */
    public static ArrayList<Double> getArrayU() {
        return arrayU;
    }


    /**
     * Method used for clearing data from all containers.
     */
    public static void clearData(){
        seriesUT.getData().clear();
        mSeriesT.getData().clear();
        hSeriesT.getData().clear();
        nSeriesT.getData().clear();
        NaSeries.getData().clear();
        KSeries.getData().clear();
        LSeries.getData().clear();
        arrayT = new ArrayList<>();
        arrayU = new ArrayList<>();
    }

    @Override
    public void init(double v, double[] doubles, double v1) { }

    /**
     * Method used for getting values during integration and putting them in appropriate containers.
     * @param stepInterpolator object containing calculated values.
     * @param b true if the step is the last one
     * @throws MaxCountExceededException if the number of functions evaluations is exceeded
     */
    @Override
    public void handleStep(StepInterpolator stepInterpolator, boolean b) throws MaxCountExceededException {
        double t  = stepInterpolator.getCurrentTime();
        double[] x = stepInterpolator.getInterpolatedState();

        NaSeries.getData().addAll(new XYChart.Data<>(t, NeuronEquation.getINa(x[1], x[3], x[0])));
        KSeries.getData().addAll(new XYChart.Data<>(t, NeuronEquation.getIK(x[2], x[0])));
        LSeries.getData().addAll(new XYChart.Data<>(t, NeuronEquation.getIL(x[0])));


        hSeriesT.getData().addAll(new XYChart.Data<>(t, x[3]));
        nSeriesT.getData().addAll(new XYChart.Data<>(t, x[2]));
        mSeriesT.getData().addAll(new XYChart.Data<>(t, x[1]));
        seriesUT.getData().add(new XYChart.Data<>(t, x[0]));

        arrayT.add(t);
        arrayU.add(x[0]);

    }


}

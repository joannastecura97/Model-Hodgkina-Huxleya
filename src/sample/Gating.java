package sample;

import javafx.scene.chart.XYChart;

/**
 * Class used for calculating values for series present in the parameters m, n, h in function of voltage plot.
 */
public class Gating {

    /** @hidden */
    private NeuronEquation neuronEquation = new NeuronEquation();
    /** M parameter in function of voltage series.  */
    private XYChart.Series<Double, Double> mSeries = new XYChart.Series<>();
    /** N parameter in function of voltage series.  */
    private XYChart.Series<Double, Double> nSeries = new XYChart.Series<>();
    /** H parameter in function of voltage series.  */
    private XYChart.Series<Double, Double> hSeries = new XYChart.Series<>();

    /**
     * Method used for getting access to the m parameter series.
     * @return m parameter in function of voltage series
     */
    public XYChart.Series<Double, Double> getmSeries() {
        return mSeries;
    }

    /**
     * Method used for getting access to the n parameter series.
     * @return n parameter in function of voltage series
     */
    public XYChart.Series<Double, Double> getnSeries() {
        return nSeries;
    }

    /**
     * Method used for getting access to the h parameter series.
     * @return h parameter in function of voltage series
     */
    public XYChart.Series<Double, Double> gethSeries() {
        return hSeries;
    }

    /**
     * Method used for initializing series. It calculates values for parameters m, n, h for each 1mV change between -50mV to 150mV and
     * stores calculated values in appropriate series.
     */
    public void initSeries(){
        for(int i = -50; i <=150; i++){
            neuronEquation.setU(i);
            mSeries.getData().add(new XYChart.Data<>((double)i, neuronEquation.M0()));
            nSeries.getData().add(new XYChart.Data<>((double)i, neuronEquation.N0()));
            hSeries.getData().add(new XYChart.Data<>((double)i, neuronEquation.H0()));
        }
        mSeries.setName("m ");
        nSeries.setName("n ");
        hSeries.setName("h ");
    }

}

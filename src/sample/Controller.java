package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the main view. It is used for making interaction with the app possible.
 */
public class Controller implements Initializable {

    /** @hidden  */
    public NumberAxis bramkowanieYAxis;

    /** @hidden  */
    public NumberAxis mojaos;

    /**
     * Line chart of parameters m, n, h in function of voltage.
     */
    @FXML
    private LineChart<Double, Double> chartBramkowanieNapiecie;

    /**
     * Line chart of voltage in function of time.
     */
    @FXML
    private LineChart<Double, Double> chartNapiecieCzas;

    /**
     * Line chart of current in function of time.
     */
    @FXML
    private LineChart<Double, Double> chartPradyCzas;

    /**
     * Line chart of parameters m, n, h in function of time.
     */
    @FXML
    private LineChart<Double, Double> chartBramkowanieCzas;

    /**
     * Text field used for typing in parameter C.
     */
    @FXML
    private TextField textFieldC;

    /**
     * Text field used for typing in parameter eNa.
     */
    @FXML
    private TextField textFieldENa;

    /**
     * Text field used for typing in parameter eK.
     */
    @FXML
    private TextField textFieldEK;

    /**
     * Text field used for typing in parameter eL.
     */
    @FXML
    private TextField textFieldEL;

    /**
     * Text field used for typing in parameter gNa.
     */
    @FXML
    private TextField textFieldGNa;

    /**
     * Text field used for typing in parameter gK.
     */
    @FXML
    private TextField textFieldGK;

    /**
     * Text field used for typing in parameter gL.
     */
    @FXML
    private TextField textFieldGL;

    /**
     * Text field used for typing in parameter I.
     */
    @FXML
    private TextField textFieldI;

    /**
     * Label used for displaying calculated frequency of the signal.
     */
    @FXML
    private Label labelF;

    /**
     * Label used for displaying calculated max peak value.
     */
    @FXML
    private Label labelUmax;

    /**
     * Label used for displaying calculated mean peak value.
     */
    @FXML
    private Label laberLmean;

    /**
     * Label used for displaying calculated standard deviation of peak values.
     */
    @FXML
    private Label labelSd;

    /**
     * OnClick event function, executed after pressing button <b>Rysuj</b>.
     * @param event Event object
     */
    @FXML
    void buttonDrawClicked(ActionEvent event) {

        setParams();
        double i=-1, c=-1 , eNa=-1 , eK=-1 , eL=-1 , gNa=-1 , gK=-1 , gL=-1;

        boolean isOK = true;

        try{
            i = Double.parseDouble(textFieldI.getText());
            c = Double.parseDouble(textFieldC.getText());
            eNa = Double.parseDouble(textFieldENa.getText());
            eK = Double.parseDouble(textFieldEK.getText());
            eL = Double.parseDouble(textFieldEL.getText());
            gNa = Double.parseDouble(textFieldGNa.getText());
            gK = Double.parseDouble(textFieldGK.getText());
            gL = Double.parseDouble(textFieldGL.getText());
        }catch (NumberFormatException e){
            isOK = false;
            //tu tekst do lejby
        }

        if(isOK){
            Integrator integrator = new Integrator(i, c, eNa, eK, eL, gNa, gK, gL);
            integrator.integrate();

            Analyzer analyzer = new Analyzer(MyStepHandler.getArrayT(),MyStepHandler.getArrayU());

            labelF.setText(String.valueOf((double)((int)(analyzer.getFrequency()*100))/100));
            labelSd.setText(String.valueOf((double)((int)(analyzer.getPeakSD()*100))/100));
            labelUmax.setText(String.valueOf((double)((int)(analyzer.getHighestPeak()*100))/100));
            laberLmean.setText(String.valueOf((double)((int)(analyzer.getMeanPeakValue()*100))/100));


        }

    }

    /**
     * Method used for setting parameters for each chart and clearing series.
     */
    private void setParams(){
        MyStepHandler.clearData();

        chartPradyCzas.getData().clear();
        chartBramkowanieCzas.getData().clear();
        chartBramkowanieNapiecie.getData().clear();
        chartNapiecieCzas.getData().clear();


        chartNapiecieCzas.setAnimated(false);

        chartNapiecieCzas.getData().addAll(MyStepHandler.getSeriesUT());
        chartNapiecieCzas.setLegendVisible(false);


        Gating gating = new Gating();
        gating.initSeries();

        chartBramkowanieNapiecie.setAnimated(false);
        chartBramkowanieNapiecie.getData().addAll(gating.gethSeries(), gating.getmSeries(), gating.getnSeries());
        bramkowanieYAxis.setTickUnit(0.1);
        bramkowanieYAxis.setLowerBound(0);
        bramkowanieYAxis.setUpperBound(1);
        bramkowanieYAxis.setAutoRanging(false);


        chartBramkowanieCzas.setAnimated(false);
        chartBramkowanieCzas.getData().addAll(MyStepHandler.gethSeriesT(), MyStepHandler.getmSeriesT(), MyStepHandler.getnSeriesT());
        MyStepHandler.getmSeriesT().setName("m");
        MyStepHandler.getnSeriesT().setName("n");
        MyStepHandler.gethSeriesT().setName("h");
        chartPradyCzas.setAnimated(false);
        chartPradyCzas.getData().addAll(MyStepHandler.getNaSeries(), MyStepHandler.getKSeries(), MyStepHandler.getLSeries());
        MyStepHandler.getNaSeries().setName("INa");
        MyStepHandler.getKSeries().setName("IK");
        MyStepHandler.getLSeries().setName("IL");

    }


    /** @hidden */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setParams();


  /*      fq.selectPeriodicSignal();

        ArrayList<Double> val = fq.getPeriodValues();
        ArrayList<Double> tim = fq.getPeriodTime();


        double[] sth = new double[val.size()];

        for (int i = 0; i < sth.length; i++) {
            sth[i] = val.get(i);
        }


        FastFourierTransformer fft = new FastFourierTransformer();
        Complex[] fftData = fft.transform(sth);


        XYChart.Series<Double, Double> series = new XYChart.Series<>();

        for (int i = 0; i < fftData.length; i++) {
            series.getData().add(new XYChart.Data<>((1/(0.05))/fftData.length*i, fftData[i].getReal()));
        }

        chartPradyCzas.getData().clear();
        chartPradyCzas.getData().add(series);

        mojaos.setAutoRanging(false);
        mojaos.setLowerBound(-500);
        mojaos.setUpperBound(500);
*/
    }
}

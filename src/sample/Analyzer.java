package sample;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Instances of this class are used for analyzing voltage in time function. It provides methods used for calculating
 * the highest peak, frequency and mean peak value.
 */
public class Analyzer {

    /**
     * <code>ArrayList</code> containing time values.
     */
    private ArrayList<Double> time;

    /**
     * <code>ArrayList</code> containing voltage values.
     */
    private ArrayList<Double> val;

    /** @hidden  */
    private ArrayList<Double> maxValues = null;

    /** @hidden  */
    private ArrayList<Double> times = new ArrayList<>();


    /**
     * Constructor setting the <code>ArrayList</code> objects.
     * @param time <code>ArrayList</code> object containing time values.
     * @param val <code>ArrayList</code> object containing voltage values.
     */
    public Analyzer(ArrayList<Double> time, ArrayList<Double> val) {
        this.time = time;
        this.val = val;
    }

    /**
     * Method used for finding the highest peak value.
     * @return Highest voltage value present in <code>val</code> ArrayList.
     */
    public Double getHighestPeak() {
        double max = val.get(0);

        for (int i = 0; i < val.size(); i++) {
            if (val.get(i) > max) {
                max = val.get(i);
            }
        }
        return max;
    }


    /** @hidden */
    private void getPeaks() {

        if(maxValues == null) {
            maxValues = new ArrayList<>();
            double max = val.get(0);


            boolean shouldWait = false;

            for (int i = (int) (10.0 / 0.05); i < (int) (80 / 0.05); i++) {
                if (!shouldWait) {
                    if (val.get(i) > max) {
                        max = val.get(i);
                    } else if (val.get(i - 1) == max) {
                        maxValues.add(max);
                        times.add(time.get(i));
                        shouldWait = true;
                        max = 0;
                    }
                } else {
                    if (val.get(i - 1) <= 0 && val.get(i) >= 0) {
                        shouldWait = false;
                    }
                }
            }
        }
    }


    /**
     * Method used for calculating the frequency of the signal. It finds periods od time between
     * each peak and by the use of them calculates the frequency.
     * @return frequency of the signal.
     */
    public double getFrequency() {
        getPeaks();
        Iterator i = times.iterator();
        i.next();
        double period = 0;
        for (int j = 0; j < times.size() - 1; j++) {
            period += (Double) i.next() - times.get(j);
        }
        period /= (times.size() - 1);

        return (1.0 / period)*1000;
    }

    /**
     * Method used for calculating average peak value.
     * @return average peak value.
     */
    public double getMeanPeakValue(){
        getPeaks();
        double sum=0;
        for (int i = 0; i <maxValues.size() ; i++) {
            sum+=maxValues.get(i);
        }
        return sum/maxValues.size();
    }

    /**
     * Method used for calculating standard deviation of peak values.
     * @return standard deviation of peak values.
     */
    public double getPeakSD(){
        double mean = getMeanPeakValue();
        double sd = 0;
        for (Double d : maxValues) {
            sd += Math.pow((mean - d), 2);
        }
        sd/=maxValues.size()-1;
        return Math.sqrt(sd);
    }






























































    /*
    public void selectPeriodicSignal() {
        //Iterator timeInterator= time.iterator();
        //for (Double val : this.val) {
        //   if(timeInterator.next().equals(10.0)){

        //    }
        //}

        int counter = 0;
        double trueMin = -1;
        double min = val.get(0);
        double prog = 1.0;

        for (int i = 0; i < time.size(); i++) {

            if (counter == 512) {
                break;
            }

            if (time.get(i) > 10 && trueMin == -1) {
                if (val.get(i) < min) {
                    min = val.get(i);
                } else if (val.get(i - 1) == min) {
                    System.out.println("truemin: " + time.get(i) + " " + val.get(i));
                    trueMin = val.get(i);
                }
            } else if (time.get(i) > 10) {
                if (time.get(i) > 10.3 && time.get(i) < 60) {
                    System.out.println(time.get(i));
                    periodValues.add(val.get(i));
                    periodTime.add(time.get(i));
                    counter++;
                } else {
                    if (Math.abs(trueMin - val.get(i)) < prog) {
                        break;
                    } else {
                        periodValues.add(val.get(i));
                        periodTime.add(time.get(i));
                        counter++;
                    }
                }


            }
        }
    }
    */

}


package ru.itis.sa.arbiter.neurobc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class NeuralNetwork {

    private double w11,w12,w21,w22,v11,v12,v13,v21,v22,v23,w1,w2,w3;
    private double e;

    private static double x1[] = new double[100];
    private static double x2[] = new double[100];
    private static double y[] = new double[100];

    static {
        readTestData100();
    }

    public NeuralNetwork(double w11, double w12, double w21, double w22, double v11, double v12, double v13, double v21, double v22, double v23, double w1, double w2, double w3) {
        this.w11 = w11;
        this.w12 = w12;
        this.w21 = w21;
        this.w22 = w22;
        this.v11 = v11;
        this.v12 = v12;
        this.v13 = v13;
        this.v21 = v21;
        this.v22 = v22;
        this.v23 = v23;
        this.w1 = w1;
        this.w2 = w2;
        this.w3 = w3;
    }

    public NeuralNetwork(DataModel model) throws NumberFormatException {
        init(model);
    }

    public NeuralNetwork() {}

    public void init(DataModel model) throws NumberFormatException {
        this.w11 = Double.parseDouble(model.getW11());
        this.w12 = Double.parseDouble(model.getW12());
        this.w21 = Double.parseDouble(model.getW21());
        this.w22 = Double.parseDouble(model.getW22());
        this.v11 = Double.parseDouble(model.getV11());
        this.v12 = Double.parseDouble(model.getV12());
        this.v13 = Double.parseDouble(model.getV13());
        this.v21 = Double.parseDouble(model.getV21());
        this.v22 = Double.parseDouble(model.getV22());
        this.v23 = Double.parseDouble(model.getV23());
        this.w1 = Double.parseDouble(model.getW1());
        this.w2 = Double.parseDouble(model.getW2());
        this.w3 = Double.parseDouble(model.getW3());
    }

    private double f(double x) {
        return 1/(1 + Math.exp(-x));
    }

    public double g(double x1, double x2) {
        double h11 = f(x1*w11 + x2*w21);
        double h12 = f( x1*w12 + x2*w22);
        return f( f(h11*v11 + h12*v21)*w1 + f(h11*v12 + h12*v22)*w2 + f(h11*v13+h12*v23)*w3);
    }

    public double e() {
        double res = 0;
        for (int i = 0; i< 100; i++) {
            double yt = g(x1[i], x2[i]);
            res += (yt - y[i]) * (yt - y[i]);
        }
        return res;
    }

    private static void readTestData100() {
        List<String> lstData;
        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(NeuralNetwork.class.getClassLoader().getResourceAsStream("resources/test_data_100.csv")))) {
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String[] s = line.split(";");
                x1[i] = Double.parseDouble(s[0]);
                x2[i] = Double.parseDouble(s[1]);
                y[i] = Double.parseDouble(s[2]);
                //System.out.println(x1[i] +";" + x2[i] +";" + y[i]);
                i++;
            }
          } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        /*

        {"prevhash":"8742e222c76666f1800665d7dc693ecde3ae3ddb8c7cd663379292b794a47a3f","data":
        {"e":"0.028838416435","publickey":"30819f300d06092a864886f70d010101050003818d0030818902818100a811365d2f3642952751029edf87c8fa2aeb6e0feafcf800190a7dd2cf750c63262f6abd8ef52b251c0e10291d5e2f7e6682de1aae1d64d4f9b242050f898744ca300a44c4d8fc8af0e7a1c7fd9b606d7bde304b29bec01fbef554df6ba1b7b1ec355e1ff68bd37f3d40fb27d1aa233fe3dd6b63f7241e734739851ce8c590f70203010001",
        "v21":"0.539763062386","w11":"0.626983568090","w22":"0.975011598497","v12":"0.068179673962","v23":"0.044663739162",
        "w21":"0.789741745355","v11":"0.456878575526","v22":"0.219327808802","w12":"0.389684979191","v13":"0.148925601792",
        "w1":"0.012316998339","w2":"0.025533448980","w3":"0.487270385485"},"info":"Egorova Anastasiia 11-805"}
         */


//        NeuralNetwork nn = new NeuralNetwork(0.11, 0.21, 0.22, 1.4, 2.0, 0.0002, 2.0056, 0.017,0.934, 1.5, 1.0047, 0.12, 1.32);
//        DataModel dm = new DataModel("1.11", "0.11", "0.23", "1.41", "1.94", "0.0003", "2.016", "0.007", "0.904", "1.58", "1.017", "0.102", "1.2","","");
        DataModel dm = new DataModel("0.626983568090", "0.389684979191", "0.789741745355", "0.975011598497",
                "0.456878575526", "0.068179673962", "0.148925601792", "0.539763062386", "0.219327808802", "0.044663739162",
                "0.012316998339", "0.025533448980", "0.487270385485","","");
        System.out.println(dm.toString());
        //        NeuralNetwork nn = new NeuralNetwork(1.11, 0.11, 0.23, 1.41, 1.94, 0.0003, 2.016, 0.007,0.904, 1.58, 1.017, 0.102, 1.2);
        NeuralNetwork nn = new NeuralNetwork(dm);
        //nn.readTestData100();
        System.out.println(nn.e());

     }

}

package me.pfrison.polytimeima4.clicker;

public class ClickerStatics {
    public static final int INGREDIENTS_COUNT = 14;
    public static final int SUPPLEMENTS_COUNT = 33;

    static double[] ingredientsBaseCost = new double[]{
             15, // butter
            100, // sugar
            1.1*pow(1), // caramel
             12*pow(1), // jam
            130*pow(1), // chocolate
            1.4*pow(2), // frozen
             20*pow(2), // 2n2222
            330*pow(2), // lm748
            5.1*pow(3), // 74hc4040
             75*pow(3), // atmega328
                pow(4), // kernel
             14*pow(4), // network
            170*pow(4), // robotics
            2.1*pow(5) // earth
    };
    public static double[] ingredientsBaseMoneyPerSecond = new double[]{
            0.1, // butter
              1, // sugar
              8, // caramel
             47, // jam
            260, // chocolate
            1.4*pow(1), // frozen
            7.8*pow(1), // 2n2222
             44*pow(1), // lm748
            260*pow(1), // 74hc4040
            1.6*pow(2), // atmega328
             10*pow(2), // kernel
             65*pow(2), // network
            430*pow(2), // robotics
            2.9*pow(3) // earth
    };

    static double[][] ingredientsUpgradesNeeds = new double[][]{
            {1, 1, 10, 25, 50, 100, 150, 200, 250, 320, 400}, // butter
            {1, 5, 25, 50, 100, 150, 200, 250, 300, 350, 400}, // sugar
            {1, 5, 25, 50, 100, 150, 200, 250, 300, 350, 400}, // caramel
            {1, 5, 25, 50, 100, 150, 200, 250, 300, 350, 400}, // jam
            {1, 5, 25, 50, 100, 150, 200, 250, 300, 350, 400}, // chocolate
            {1, 5, 25, 50, 100, 150, 200, 250, 300, 350, 400}, // frozen
            {1, 5, 25, 50, 100, 150, 200, 250, 300, 350, 400}, // 2n2222
            {1, 5, 25, 50, 100, 150, 200, 250, 300, 350, 400}, // lm748
            {1, 5, 25, 50, 100, 150, 200, 250, 300, 350, 400}, // 74hc4040
            {1, 5, 25, 50, 100, 150, 200, 250, 300, 350, 400}, // atmega328
            {1, 5, 25, 50, 100, 150, 200, 250, 300, 350, 400}, // kernel
            {1, 5, 25, 50, 100, 150, 200, 250, 300, 350, 400}, // network
            {1, 5, 25, 50, 100, 150, 200, 250, 300, 350, 400}, // robotics
            {1, 5, 25, 50, 100, 150, 200, 250, 300, 350, 400} // earth
    };

    static double[][] ingredientsUpgradesCost = new double[][]{
            {100       ,  500       ,   10*pow(1),  100*pow(1),   10*pow(2),  100*pow(2),    1*pow(3),   10*pow(3) ,   10*pow(4) ,   10*pow(5) ,   10*pow(6) }, // butter
            {    pow(1),    5*pow(1),   50*pow(1),    5*pow(2),  500*pow(2),   50*pow(3),   50*pow(4),   50*pow(5) ,   50*pow(6) ,   50*pow(7) ,   50*pow(8) }, // sugar
            { 11*pow(1),   55*pow(1),  550*pow(1),   55*pow(2),  5.5*pow(3),  550*pow(3),  550*pow(4),  550*pow(5) ,  550*pow(6) ,  550*pow(7) ,  5.5*pow(9) }, // caramel
            {120*pow(1),  600*pow(1),    6*pow(2),  600*pow(2),   60*pow(3),    6*pow(4),    6*pow(5),    6*pow(6) ,    6*pow(7) ,    6*pow(8) ,    6*pow(9) }, // jam
            {1.3*pow(2),  6.5*pow(2),   65*pow(2),  6.5*pow(3),  650*pow(3),   65*pow(4),   65*pow(5),   65*pow(6) ,   65*pow(7) ,   65*pow(8) ,  650*pow(9) }, // chocolate
            { 14*pow(2),   70*pow(2),  700*pow(2),   70*pow(3),    7*pow(4),  700*pow(4),  700*pow(5),  700*pow(6) ,  700*pow(7) ,  700*pow(8) ,    7*pow(10)}, // frozen
            {200*pow(2),      pow(3),   10*pow(3),      pow(4),  100*pow(4),   10*pow(5),   10*pow(6),   10*pow(7) ,   10*pow(8) ,   10*pow(9) ,  100*pow(10)}, // 2n2222
            {3.3*pow(3), 16.5*pow(3),  165*pow(3), 16.5*pow(4), 1.65*pow(5),  165*pow(5),  165*pow(6),  165*pow(7) ,  165*pow(8) ,  165*pow(9) , 1.65*pow(11)}, // lm748
            { 51*pow(3),  255*pow(3), 2.55*pow(4),  255*pow(4), 25.5*pow(5), 2.55*pow(6), 2.55*pow(7), 2.55*pow(8) , 2.55*pow(9) , 2.55*pow(10), 25.5*pow(11)}, // 74hc4040
            {750*pow(3), 3.75*pow(4), 37.5*pow(4), 3.75*pow(5),  375*pow(5), 37.5*pow(6), 37.5*pow(7), 37.5*pow(8) , 37.5*pow(9) , 37.5*pow(10),  375*pow(11)}, // atmega328
            { 10*pow(4),   50*pow(4),  500*pow(4),   50*pow(5),    5*pow(6),  500*pow(6),  500*pow(7),  500*pow(8) ,  500*pow(9) ,  500*pow(10),    5*pow(12)}, // kernel
            {140*pow(4),  700*pow(4),    7*pow(5),  700*pow(5),   70*pow(6),    7*pow(7),    7*pow(8),    7*pow(9) ,    7*pow(10),    7*pow(11),   70*pow(12)}, // network
            {1.7*pow(5),  8.5*pow(5),   85*pow(5),  8.5*pow(6),  850*pow(6),   85*pow(7),   85*pow(8),   85*pow(9) ,   85*pow(10),   85*pow(11),  850*pow(12)}, // robotics
            { 21*pow(5),  105*pow(5), 1.05*pow(6),  105*pow(6), 10.5*pow(7), 1.05*pow(8), 1.05*pow(9), 1.05*pow(10), 1.05*pow(11), 1.05*pow(12), 10.5*pow(13)} // earth
    };

    public static double[] butterAddition = new double[]{
              0,
              0,
              0,
            0.1,
            0.5,
              5,
             50,
            500,
              5*pow(1),
             50*pow(1),
            500*pow(1),
              5*pow(2)
    };

    static double[] supplementsCost = new double[]{
            999999,
             10*pow(2),
            100*pow(2),
            100*pow(2),
              1*pow(3),
             10*pow(3),
            100*pow(3),
              1*pow(4),
             10*pow(4),
            100*pow(4),
              1*pow(5),
             10*pow(5),
            50*pow(5),
            100*pow(5),
              1*pow(6),
             10*pow(6),
             50*pow(6),
            100*pow(6),
              1*pow(7),
             10*pow(7),
             50*pow(7),
            100*pow(7),
              1*pow(8),
             10*pow(8),
            100*pow(8),
              1*pow(9),
             10*pow(9),
            100*pow(9),
              1*pow(10),
             10*pow(10),
            100*pow(10),
              1*pow(11),
             10*pow(11)
    };

    static double[] supplementsNeeds = new double[]{
             50*pow(1),
            500*pow(1),
              5*pow(2),
              5*pow(2),
             50*pow(2),
            500*pow(2),
              5*pow(3),
             50*pow(3),
            500*pow(3),
              5*pow(4),
             50*pow(4),
            500*pow(4),
              5*pow(5),
             25*pow(5),
             50*pow(5),
            250*pow(5),
            500*pow(5),
              5*pow(6),
             25*pow(6),
             50*pow(6),
            250*pow(6),
            500*pow(6),
              5*pow(7),
             50*pow(7),
            500*pow(7),
              5*pow(8),
             50*pow(8),
            500*pow(8),
              5*pow(9),
             50*pow(9),
            500*pow(9),
              5*pow(10),
             50*pow(10)
    };

    public static double[] supplementsMultiplications = new double[]{
            1.02,
            1.02,
            1.04,
            1.04,
            1.04,
            1.04,
            1.04,
            1.08,
            1.04,
            1.04,
            1.04,
            1.04,
            1.06,
            1.06,
            1.04,
            1.08,
            1.06,
            1.06,
            1.06,
            1.06,
            1.06,
            1.06,
            1.06,
            1.08,
            1.08,
            1.08,
            1.08,
            1.10,
            1.10,
            1.10,
            1.10,
            1.12,
            1.12
    };

    static double[] glovesNeeds = new double[]{
              1*pow(1),
            100*pow(1),
             10*pow(2),
              1*pow(3),
            100*pow(3),
             10*pow(4),
              1*pow(5),
            100*pow(5),
             10*pow(6),
              1*pow(7),
            100*pow(7),
             10*pow(8)
    };

    static double[] glovesCost = new double[]{
             50*pow(1),
              5*pow(2),
            500*pow(2),
             50*pow(3),
              5*pow(4),
            500*pow(4),
             50*pow(5),
              5*pow(6),
            500*pow(6),
             50*pow(7),
              5*pow(8),
            500*pow(8)
    };

    static double glovesMultiplication = 0.01d;

    public static double goldenCrepeMinTime = 300000; // in ms
    public static double goldenCrepeMaxTime = 900000; // in ms
    public static double goldenCrepeDespawnTime = 13000; // in ms
    public static double goldenCrepeLightDuration = 77000; // in ms
    public static double goldenCrepeHighDuration = 13000; // in ms

    static double goldenCrepeInstantGainCrepeMultiplier = 0.15;
    static double goldenCrepeInstantGainCrepePerSecMultiplier = 900;
    static double goldenCrepeInstantGainAddition = 13;

    static double goldenCrepeLightMultiplier = 7;
    static double goldenCrepeHighMultiplier = 777;

    static double[] goldenUpgradeCost = new double[]{
            777.777*pow(2),
            77.777*pow(3),
            7.777*pow(4)
    };
    static double[] goldenUpgradeNeeds = new double[]{
            7,
            21,
            77
    };
    public static double[] goldenUpgradeSpawnTimeMultiplier = new double[]{
            0.5,
            0.5,
            1
    };
    public static double[] goldenUpgradeDespawnTimeMultiplier = new double[]{
            2,
            2,
            1
    };
    public static double[] goldenUpgradeDurationMultiplier = new double[]{
            1,
            1,
            2
    };

    private static double pow(int i){
        if(i == 0)
            return 1;
        if(i == 1)
            return 1000;
        return Math.pow(1000, i);
    }
}

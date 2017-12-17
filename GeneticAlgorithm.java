package geneticAlgorithm;

import java.util.Random;
import javax.swing.JFrame;

public class GeneticAlgorithm {

    static final float MUTATION_RATE = 0.005f;
    static final int PAUSE_TIME = 750;
    
    public static void main(String[] args) {
        //Init
        JFrame frame;
        frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Genetic Algorithm !");

        int brickWidth = frame.getWidth() / 22;
        int brickHeight = frame.getHeight() / 51;

        Brick pop[] = getInitPop(brickWidth, brickHeight);
        Brick ref = new Brick(frame.getWidth() - (int) (1.75 * brickWidth), frame.getHeight() / 2, brickWidth, brickHeight, createDna(), true);

        Population population = new Population(pop, ref);
        frame.setContentPane(population);
        
        try {
            Thread.sleep(2 * PAUSE_TIME);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        
        go(population);
    }

    public static void go(Population population) {

        while (true) {
            
            population.setFitnesses();
            
            population.sortPopulation();
            
            if (population.getAverageFitness() >= 92.5){
                population.getRef().getDna().setGenome(createGenome());
                
                population.repaint();
                
                population.setFitnesses();
            
                population.sortPopulation();
            }
            
            population.killLessFits();
            
            population.reproduce();
        }

    }

    public static String formatRGBToBin(int[] colors) {
        String red = Integer.toBinaryString(colors[0]);
        String green = Integer.toBinaryString(colors[1]);
        String blue = Integer.toBinaryString(colors[2]);

        while (red.length() < 8) {
            red = "0" + red;
        }
        while (green.length() < 8) {
            green = "0" + green;
        }
        while (blue.length() < 8) {
            blue = "0" + blue;
        }

        return red + green + blue;
    }

    public static Brick[] getInitPop(int brickWidth, int brickHeight) {
        Brick population[] = new Brick[1000];
        int countEntities = 0;

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 50; j++) {
                population[countEntities] = new Brick(brickWidth * i, brickHeight * j, brickWidth, brickHeight, createDna(), false);
                countEntities++;
            }
        }
        return population;
    }

    public static int[] randomizeRGB() {
        Random random = new Random();

        int colors[] = new int[3];
        for (int l = 0; l < 3; l++) {
            colors[l] = random.nextInt(256);
        }

        return colors;
    }

    public static Dna createDna() {
        return new Dna(createGenome());
    }

    public static String createGenome() {
        return formatRGBToBin(randomizeRGB());
    }

}

package geneticAlgorithm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;

public class Population extends JPanel {

    private Brick[] population;
    private final Brick ref;
    private float maxFitness, minFitness;

    public Population(Brick[] population, Brick ref) {
        super();
        this.population = population;
        this.ref = ref;
        this.maxFitness = 0;
        this.minFitness = 0;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        for (Brick b : this.population) {
            if (b != null) {
                b.dessine(g);
            }
        }
        this.ref.dessine(g);
        //Infos
        g.setColor(Color.WHITE);
        g.setFont(new Font("default", Font.BOLD, 12));
        g.drawString("Max fitness : " + Math.round(this.maxFitness * 100.0) / 100.0, this.getWidth() - 125, 20);
        g.setFont(new Font("default", Font.BOLD, 12));
        g.drawString("Min fitness : " + Math.round(this.minFitness * 100.0) / 100.0, this.getWidth() - 125, 40);
        g.setFont(new Font("default", Font.BOLD, 12));
        g.drawString("Avg fitness : " + Math.round(this.getAverageFitness() * 100.0) / 100.0, this.getWidth() - 125, 60);
    }

    public Brick[] getPopulation() {
        return this.population;
    }

    public Brick getRef() {
        return this.ref;
    }

    public void sortPopulation() {
        try {
            Thread.sleep(GeneticAlgorithm.PAUSE_TIME);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        int startIndex = 0;
        for (int taille = this.population.length; taille > 1; taille--) {
            int maxIndex = getMax(this.population, startIndex);
            this.swap(maxIndex, startIndex);
            startIndex++;
        }

        this.maxFitness = this.population[0].getFitness();
        this.minFitness = this.population[this.population.length - 1].getFitness();

        this.setNextCoords();

        this.repaint();
    }

    private void setNextCoords() {
        int width = this.population[0].getWidth();
        int height = this.population[0].getHeight();

        int count = 0;
        for (int j = 0; j < 50; j++) {
            for (int i = 0; i < 20; i++) {
                this.population[count].setxPos(i * width);
                this.population[count].setyPos(j * height);
                count++;
            }
        }
    }

    public void killLessFits() {
        try {
            Thread.sleep(GeneticAlgorithm.PAUSE_TIME);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        for (int i = 500; i < this.population.length; i++) {
            this.population[i] = null;
        }

        this.repaint();
    }

    public void reproduce() {
        try {
            Thread.sleep(GeneticAlgorithm.PAUSE_TIME);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        int width = this.population[0].getWidth();
        int height = this.population[0].getHeight();

        int count = 500;
        for (int j = 25; j < 50; j++) {
            for (int i = 0; i < 20; i++) {
                this.population[count] = new Brick(i * width, j * height, width, height, new Dna(this.createChildGenome()), false);
                count++;
            }
        }

        this.repaint();
    }

    private String createChildGenome() {
        Brick father = this.getRandParent();
        Brick mother = this.getRandParent();

        String fatherGenome = father.getDna().getGenome();
        String motherGenome = mother.getDna().getGenome();

        String childGenome = crossover(fatherGenome, motherGenome);

        return childGenome;
    }

    private String crossover(String fatherGenome, String motherGenome) {
        String childGenome = "";

        Random r = new Random();

        for (int i = 0; i < 24; i++) {
            if (r.nextInt(2) == 1) {
                childGenome += fatherGenome.charAt(i);
            } else {
                childGenome += motherGenome.charAt(i);
            }
        }
        return childGenome;
    }

    private Brick getRandParent() {
        Random r = new Random();

        int rand1 = r.nextInt(500);
        int rand2 = r.nextInt(500);

        int index = Math.abs(rand1 - rand2);
        return this.population[index];
    }

    private void swap(int maxIndex, int startIndex) {
        Brick mem = this.population[startIndex];

        this.population[startIndex] = this.population[maxIndex];

        this.population[maxIndex] = mem;
    }

    private int getMax(Brick[] population, int startIndex) {
        int maxIndex = startIndex;

        for (int i = startIndex; i < population.length; i++) {
            if (population[i].getFitness() >= population[maxIndex].getFitness()) {
                maxIndex = i;
            }
        }

        return maxIndex;
    }

    void setFitnesses() {
        for (Brick b : this.population) {
            b.setFitness(calculateFitness(this.ref.getDna(), b.getDna()));
        }
    }

    private float calculateFitness(Dna ref, Dna brick) {
        int refRed = ref.getColor().getRed();
        int refGreen = ref.getColor().getGreen();
        int refBlue = ref.getColor().getBlue();

        int brickRed = brick.getColor().getRed();
        int brickGreen = brick.getColor().getGreen();
        int brickBlue = brick.getColor().getBlue();

        int difRed = Math.abs(refRed - brickRed);
        int difGreen = Math.abs(refGreen - brickGreen);
        int difBlue = Math.abs(refBlue - brickBlue);

        float fitRed = (float) (((255.0 - difRed) / 255.0) * (100.0 / 3.0));
        float fitGreen = (float) (((255.0 - difGreen) / 255.0) * (100.0 / 3.0));
        float fitBlue = (float) (((255.0 - difBlue) / 255.0) * (100.0 / 3.0));

        return fitRed + fitGreen + fitBlue;
    }

    public double getAverageFitness() {
        float total = 0;
        int count = 0;
        for (Brick b : this.population) {
            if (b != null) {
                total += b.getFitness();
                count++;
            }
        }
        return (total / (float) count);
    }
}

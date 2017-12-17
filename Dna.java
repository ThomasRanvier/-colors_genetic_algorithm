package geneticAlgorithm;

import java.awt.Color;
import java.util.Random;

public final class Dna {

    private String genome;

    public Dna(String genome) {
        this.genome = genome;
        this.mutate();
    }

    public String getGenome() {
        return this.genome;
    }

    public void setGenome(String genome) {
        this.genome = genome;
    }

    public Color getColor() {
        String redDna = this.genome.substring(0, 8);
        String greenDna = this.genome.substring(8, 16);
        String blueDna = this.genome.substring(16, 24);

        int red = Integer.parseInt(redDna, 2);
        int green = Integer.parseInt(greenDna, 2);
        int blue = Integer.parseInt(blueDna, 2);

        return new Color(red, green, blue);
    }

    public void mutate() {
        Random r = new Random();
        if (r.nextFloat() <= GeneticAlgorithm.MUTATION_RATE){
            int index = r.nextInt(24);

            String firstPart = this.genome.substring(0, index);
            String secondPart = this.genome.substring(index);

            this.genome = firstPart + r.nextInt(2) + secondPart;

            this.formatGenome();
        }
    }

    private void formatGenome() {
        if (this.genome.length() > 24) {
            Random r = new Random();

            int index = r.nextInt(23);

            String firstPart = this.genome.substring(0, index);
            String secondPart = this.genome.substring(index + 1);

            this.genome = firstPart + secondPart;
        }
    }

}

package geneticAlgorithm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Brick {

    private final Rectangle rect;
    private final boolean isRef;
    private final int height, width;
    private float fitness;
    private int xPos, yPos;
    private final Dna dna;
    
    public Brick(int xPos, int yPos, int width, int height, Dna dna, boolean isRef) {
        this.rect = new Rectangle(xPos, yPos, width, height);
        this.dna = dna;
        this.isRef = isRef;
        this.height = height;
        this.width = width;
        this.fitness = 0;
        this.xPos = xPos;
        this.yPos = yPos;
    }
    
    public void dessine(Graphics g) {
        if (this.isRef) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("default", Font.BOLD, 16));
            g.drawString("Ref : ", (int) (this.width * 20.25), this.height * 26);
            g.setColor(this.dna.getColor());
            g.fillRect((int) this.rect.getX(), (int) this.rect.getY(), (int) (this.rect.getWidth() * 1.25), (int) (this.rect.getHeight() * 1.25));
            g.setColor(Color.BLACK);
            g.drawRect((int) this.rect.getX(), (int) this.rect.getY(), (int) (this.rect.getWidth() * 1.25), (int) (this.rect.getHeight() * 1.25));
        } else {
            g.setColor(this.dna.getColor());
            g.fillRect(this.xPos, this.yPos, (int) this.rect.getWidth(), (int) this.rect.getHeight());
            g.setColor(Color.BLACK);
            g.drawRect(this.xPos, this.yPos, (int) this.rect.getWidth(), (int) this.rect.getHeight());
            //Infos
            //g.setFont(new Font("default", Font.ITALIC, 10));
            //g.drawString(Float.toString(this.fitness), this.xPos, this.yPos + 10);
        }
    }

    public float getFitness() {
        return this.fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public Dna getDna() {
        return this.dna;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
    
}

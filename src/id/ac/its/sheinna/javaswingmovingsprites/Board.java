package id.ac.its.sheinna.javaswingmovingsprites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private final int DELAY = 10;
    private Timer timer;
    private SpaceShip spaceShip;
    private Asteroid[] asteroid = new Asteroid[55];
    private Integer count;
    
    public Board() {

        initBoard();
    }

    protected void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);

        spaceShip = new SpaceShip(ICRAFT_X, ICRAFT_Y);
        Random rand = new Random();
//        count = rand.nextInt(15);
        count = 50;
        for(int i = 0; i < count; i++)
        {
            int y = rand.nextInt(300);
            int x = rand.nextInt(1000) + 300;
//            System.out.println(x + " " + y);
            asteroid[i] = new Asteroid(x, y);
        }
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    protected void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        
        g2d.drawImage(spaceShip.getImage(), spaceShip.getX(),
                spaceShip.getY(), this);
        for(int i = 0; i < count; i++)
        {
            g2d.drawImage(asteroid[i].getImage(), asteroid[i].getX(), asteroid[i].getY(), this);
        }

        List<Missile> missiles = spaceShip.getMissiles();

        for (Missile missile : missiles) {
            
            g2d.drawImage(missile.getImage(), missile.getX(),
                    missile.getY(), this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        updateMissiles();
        updateSpaceShip();
        updateAsteroid();
        repaint();
    }

    private void updateMissiles() {

        List<Missile> missiles = spaceShip.getMissiles();

        for (int i = 0; i < missiles.size(); i++) {

            Missile missile = missiles.get(i);

            if (missile.isVisible()) {

                missile.move();
            } else {

                missiles.remove(i);
            }
        }
    }

    private void updateAsteroid() {

        for (int i = 0; i < count; i++)
        {
            if (asteroid[i].isVisible()) 
            {
                asteroid[i].move();
            } 
            else 
            {
                asteroid[i].setVisible(false);
            }
        }
    }
    
    private void updateSpaceShip() {

        spaceShip.move();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            spaceShip.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            spaceShip.keyPressed(e);
        }
    }
}

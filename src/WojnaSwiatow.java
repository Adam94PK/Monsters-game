import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WojnaSwiatow extends Canvas implements Stage, KeyListener {
	private long usedTime;
	private BufferStrategy bufferStrategy;
	private SpriteCache spriteCache;
	private ArrayList<Actor> monsters;
	private ArrayList<Actor> bullets;
	private Player player;
	
	public WojnaSwiatow(){
		spriteCache = new SpriteCache();
		JFrame window = new JFrame(".:Wojna Œwiatów:.");
		setBounds(0, 0, Stage.WIDTH, Stage.HEIGHT);
		window.setPreferredSize(new Dimension(Stage.WIDTH, Stage.HEIGHT));
		window.setSize(Stage.WIDTH, Stage.HEIGHT);
		window.setLayout(null);
		window.add(this);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		createBufferStrategy(2);
		bufferStrategy = getBufferStrategy();
		requestFocus();
		addKeyListener(this);
	}
	
	public void initWorld(){
		player = new Player(this, new String[] {"player0.png", "playerL.png", "playerR.png"});
		bullets = new ArrayList<Actor>();
		monsters = new ArrayList<Actor>();
		for(int i=0; i<10; i++){
			Monster monster = new Monster(this, new String[] {"monster.png", "monster1.png", "monster2.png", "monster3.png"});
			monster.setX((int)(Math.random()*(Stage.WIDTH - monster.width)));
			monster.setY((int)(Math.random()*(Stage.HEIGHT - monster.height)));
			monster.setVx((int)(Math.random()*3) + 1);
			monsters.add(monster);
		}
	}
	
	public void paintWorld(){
		Graphics2D g2 = (Graphics2D)bufferStrategy.getDrawGraphics();
		g2.fillRect(0, 0, getWidth(), getHeight());
		for(Actor a : monsters){
			a.paint(g2);
		}
		for(Actor a : bullets)
			a.paint(g2);
		
		player.paint(g2);
		showFPS(g2);
		bufferStrategy.show();
	}
	
	private void showFPS(Graphics2D g2){
		g2.setColor(Color.WHITE);
		if(usedTime>0){
			g2.drawString((1000/usedTime) + " fps", 5, getHeight() - 50);
		}else{
			g2.drawString("--fps", 5, getHeight() - 50);
		}
	}
	
	public void updateWorld(){
		player.act();
		ArrayList<Actor> removalActors = new ArrayList<Actor>();
		for(Actor a : bullets){
			if(a.isMarkedForRemoval())
				removalActors.add(a);
			else
				a.act();
		}
		for(Actor a : monsters){
			if(a.isMarkedForRemoval())
				removalActors.add(a);
			else
				a.act();
		}
		for(Actor r : removalActors){
			monsters.remove(r);
			bullets.remove(r);
		}
	}
	
	public SpriteCache getSpriteCache() {
		return spriteCache;
	}
	
	public void checkCollisoins(){
		Rectangle2D playerBounds = player.getBounds();
		Rectangle2D monsterBounds;
		Rectangle2D bulletsBounds;
		for(Actor a : monsters){
			monsterBounds = a.getBounds();
			if(monsterBounds.intersects(playerBounds))
				player.collision(a);
			
			for(Actor b : bullets){
				bulletsBounds = b.getBounds();
				if(bulletsBounds.intersects(monsterBounds)){
					b.collision(a);
					a.collision(b);
					System.out.println("Kolizje z pociskami");
				}
			}
		}
	}
	
	public void game(){
		usedTime = 1000;
		initWorld();
		while(isVisible()){
			long startTime = System.currentTimeMillis();
			updateWorld();
			checkCollisoins();
			paintWorld();
			usedTime = System.currentTimeMillis() - startTime;
			try{
				Thread.sleep(Stage.FAST);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		player.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
	}

	public void keyTyped(KeyEvent e) {	}

	public void addActor(Actor actor) {
		if(actor instanceof Monster)
			monsters.add(actor);
		else
			bullets.add(actor);
	}

	public static void main(String[] args) throws InterruptedException {
		WojnaSwiatow gra = new WojnaSwiatow();
		gra.game();
	}
}

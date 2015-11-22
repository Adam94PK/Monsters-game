import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


public class Actor {
	protected int x, y;
	protected int width, height;
	protected String[] spriteNames;
	protected Stage stage;
	protected SpriteCache spriteCache;
	protected int currentFrame;
	protected int frameSpeed;
	protected int t;
	protected boolean markedForRemoval = false;
	
	public Actor(Stage stage){
		this.stage = stage;
		spriteCache = stage.getSpriteCache();
		currentFrame = 0;
		frameSpeed = 1;
		t = 0;
	}
	
	public void remove(){
		markedForRemoval = true;
	}
	public boolean isMarkedForRemoval(){
		return markedForRemoval;
	}
	
	public Rectangle2D getBounds(){
		return new Rectangle2D.Float(x, y, getWidth(), getHeight());
	}
	
	public void collision(Actor a){	}
	
	public int getFrameSpeed() { return frameSpeed; }
	public void setFrameSpeed(int frameSpeed) { this.frameSpeed = frameSpeed; }

	public void paint(Graphics2D g2){
		g2.drawImage(spriteCache.getSprite(spriteNames[currentFrame]), x, y, stage);
	}

	public int getX() {	return x; }
	public void setX(int x) { this.x = x; }
	
	public int getY() { return y; }
	public void setY(int y) { this.y = y; }
	
	public int getWidth() { return width; }
	public void setWidth(int width) { this.width = width; }
	
	public int getHeight() { return height;	}
	public void setHeight(int height) {	this.height = height; }
	
	public String [] getSpriteNames() {	return spriteNames;	}

	public void setSpriteNames(String [] names) {
		this.spriteNames = names;
		for(String s : spriteNames){
			BufferedImage image = spriteCache.getSprite(s);
			this.height = image.getHeight();
			this.width = image.getWidth();
		}
		
	}
	
	public void act(){	}
}

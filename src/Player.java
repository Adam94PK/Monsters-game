import java.awt.event.KeyEvent;


public class Player extends Actor{

	private static final int PLAYER_SPEED = 4;
	private int vx;
	private int vy;
	private boolean up, down, left, right;
	
	public Player(Stage stage, String [] names) {
		super(stage);
		setSpriteNames(names);
		up = false; down = false; left = false; right = false;
	}
	
	public void act(){
		x += vx;
		y += vy;
		if(x>Stage.WIDTH-getWidth())
			x=Stage.WIDTH-getWidth();
		else if(x<0)
			x=0;
		if(y>Stage.HEIGHT-getHeight())
			y=Stage.HEIGHT-getHeight();
		else if(y<0)
			y=0;
	}

	public void fire(){
		Bullet bullet = new Bullet(stage, new String [] {"bulletL.png", "bulletR.png"});
		//Jesli obrucony w lewo to pociski leca w lewo w przeciwnym wypadku leca w prawo
		if(currentFrame==1)
			bullet.setLeft(true);
		bullet.setX(getX());
		bullet.setY(getY()+10);
		stage.addActor(bullet);
	}
	
	public void collision(Actor a){
		System.out.println("Gracz pizda, duch go zjad³");
	}
	
	public void keyPressed(KeyEvent e){
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:	up = true; 		break;
		case KeyEvent.VK_DOWN:	down = true;	break;
		case KeyEvent.VK_LEFT:	left = true;	break;
		case KeyEvent.VK_RIGHT: right = true;	break;
		case KeyEvent.VK_SPACE: fire();			break;
		}
		updateSpeed();
	}
	
	public void keyReleased(KeyEvent e){
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:	up = false; 	break;
		case KeyEvent.VK_DOWN:	down = false;	break;
		case KeyEvent.VK_LEFT:	left = false;	break;
		case KeyEvent.VK_RIGHT: right = false;	break;
		}
		updateSpeed();
	}
	
	private void updateSpeed(){
		vx=0;
		vy=0;
		if(up) 		vy = -PLAYER_SPEED;
		if(down)	vy = PLAYER_SPEED;
		if(left){
			vx = -PLAYER_SPEED;
			currentFrame = 1;
		}
		else if(right){
			vx = PLAYER_SPEED;
			currentFrame = 2;
		}
	}
	
	public int getVx() { return vx; }
	public void setVx(int vx) {	this.vx = vx; }
	public int getVy() { return vy;	}
	public void setVy(int vy) { this.vy = vy; }
	
}

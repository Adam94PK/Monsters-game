
public class Bullet extends Actor {

	private final static int BULLET_SPEED = 10;
	private boolean left;
	public Bullet(Stage stage, String [] names) {
		super(stage);
		setSpriteNames(names);
		left = false;
	}
	
	public void collision(Actor a){
		remove();
	}
	
	public void act(){
		if(left){
			x += -BULLET_SPEED;
			currentFrame = 0;
		}else{
			x += BULLET_SPEED;
			currentFrame = 1;
		}
		if(x<0 || x>Stage.WIDTH-getWidth())
			remove();
		
	}

	public void setLeft(boolean left) { this.left = left; }
}

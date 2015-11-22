
public class Monster extends Actor {
	private int vx;
	public Monster(Stage stage, String [] name) {
		super(stage);
		setSpriteNames(name);
		vx=2;
		setFrameSpeed(10);
	}
	
	public void collision(Actor a){
		if(a instanceof Bullet)
			remove();
	}
	
	public void act(){
		t++;
		if(t % frameSpeed == 0){
			t=0;
			currentFrame = (currentFrame + 1) % spriteNames.length;	
		}
		if(x<0 || x>Stage.WIDTH - width)
			vx = -vx;
		x +=vx;
	}

	public int getVx() { return vx; }
	public void setVx(int vx) { this.vx = vx; }
}

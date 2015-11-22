import java.awt.image.ImageObserver;

public interface Stage extends ImageObserver{

	public static final int WIDTH = 1200;
	public static final int HEIGHT = 900;
	public static final int FAST = 10;
	public SpriteCache getSpriteCache();
	public void addActor(Actor actor);
}

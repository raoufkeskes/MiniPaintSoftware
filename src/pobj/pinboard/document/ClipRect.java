package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipRect extends AbstractClip 
{
	
	
	public ClipRect(double left, double top, double right, double bottom, Color color)
	{
		super(left, top, right, bottom, color);
	}
	@Override
	public void draw(GraphicsContext ctx)
	{
		ctx.setFill(getColor());
		ctx.fillRect(getLeft(),getTop(),getWidth(),getHeight()) ;
	}
	
	
	
	@Override
	public Clip copy()
	{
		return new ClipRect(getLeft(), getTop(), getRight(), getBottom(), getColor());
	}
	
	
}

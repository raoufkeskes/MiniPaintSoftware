package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class AbstractClip implements Clip
{
	private double left ;
	private double right ;
	private double top ;
	private double bottom ;
	private Color color ;

	public AbstractClip()
	{
		this.left = 0;
		this.right = 0;
		this.top = 0;
		this.bottom = 0;
		this.color = Color.BLACK ;
	}
	public AbstractClip(double left, double top, double right, double bottom, Color color)
	{
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
		this.color = color;
	}
	@Override
	public abstract void draw(GraphicsContext ctx) ;
	@Override
	public double getTop()
	{
		return top ;
	}
	@Override
	public double getLeft()
	{
		return left ;
	}
	@Override
	public double getBottom()
	{
		return bottom ;
	}
	@Override
	public double getRight()
	{
		return right ;
	}
	@Override
	public void setGeometry(double left, double top, double right, double bottom)
	{
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;	
	}
	@Override
	public void move(double x, double y)
	{
		this.left += x ;
		this.right += x ;
		this.top += y ;
		this.bottom += y ;	
	}
	@Override
	public boolean isSelected(double x, double y)
	{
		return ( x >= left  && x <= right && y >= top && y <= bottom  )  ;
	}
	@Override
	public void setColor(Color c)
	{
		color = c ;
	}
	@Override
	public Color getColor()
	{
		return color ;
	}
	@Override
	public abstract Clip copy();
	
	public double getWidth()
	{
		return right - left ;
	}
	
	public double getHeight()
	{
		return bottom - top ;
	}
	
}

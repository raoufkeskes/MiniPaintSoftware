package pobj.pinboard.document;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipGroup   implements Composite , Clip
{
	private List<Clip> clips_list ;
	private double rect_left ; 
	private double rect_right ; 
	private double rect_top ; 
	private double rect_bottom ; 

	public ClipGroup()
	{
		clips_list = new ArrayList<Clip> ( ) ;
	}

	@Override
	public List<Clip> getClips()
	{
		return clips_list ;
	}

	@Override
	public void addClip(Clip toAdd)
	{
		/*Updating rectangle */
		if (clips_list.isEmpty())
		{
			rect_left = toAdd.getLeft() ;
			rect_right = toAdd.getRight() ;
			rect_top = toAdd.getTop() ;
			rect_bottom = toAdd.getBottom() ;
		}
		else
		{
			//Update
			if ( toAdd.getLeft() < rect_left )
				rect_left =  toAdd.getLeft() ;
			if ( toAdd.getTop() < rect_top  )
				rect_top =   toAdd.getTop() ;
			if ( toAdd.getRight() > rect_right )
				rect_right =  toAdd.getRight() ;
			if ( toAdd.getBottom() > rect_bottom )
				rect_bottom =  toAdd.getBottom()  ;
		}
		
		clips_list.add(toAdd);
		
		
	}

	@Override
	public void removeClip(Clip toRemove)
	{
		clips_list.remove(toRemove) ;
		/*Updating rectangle */
		if (clips_list.isEmpty())
		{
			rect_left = 0 ;
			rect_right = 0 ;
			rect_top = 0 ;
			rect_bottom = 0 ;
		}
		else
		{
			double left = clips_list.get(0).getLeft() ;
			double top =  clips_list.get(0).getTop() ;
			double right = clips_list.get(0).getRight() ;
			double bottom =  clips_list.get(0).getBottom() ;
			
			for ( Clip clip : clips_list )
			{
				// Select Min 
				if ( left > clip.getLeft() ) 
					left = clip.getLeft() ;
				if ( top > clip.getTop() ) 
					top = clip.getTop() ;
				// Select max 	
				if ( right < clip.getRight() ) 
					right = clip.getRight() ;	
				if ( bottom < clip.getBottom() ) 
					bottom = clip.getBottom() ;
			}
			
			if ( left > rect_left )
				rect_left = left ;
			if ( top > rect_top )
				rect_top = top ;
			if ( right < rect_right  )
				rect_right = right ;
			if ( bottom < rect_bottom )
				rect_bottom = bottom ;
		}
		
	}

	@Override
	public void draw(GraphicsContext ctx)
	{
		for (Clip clip : clips_list )
			clip.draw(ctx);
	}

	@Override
	public Clip copy()
	{
		ClipGroup  group_copy = new ClipGroup( ) ;
		for (Clip clip : clips_list )
			group_copy.addClip(clip.copy()) ;
		
		return group_copy ;
	}
	
	@Override
	public void move(double x, double y)
	{
		for (Clip clip : clips_list )
			clip.move(x, y);
		
		this.rect_left += x ;
		this.rect_right += x ;
		this.rect_top += y ;
		this.rect_bottom  += y ;	
	}
	
	@Override
	public void setGeometry(double left, double top, double right, double bottom)
	{
		this.move( left-right , bottom-top );
	}
	
	@Override
	public boolean isSelected(double x, double y)
	{		
		return ( x >= rect_left  && x <= rect_right && y >= rect_top  && y <= rect_bottom  )  ;
	}
	


	@Override
	public double getTop()
	{
		return rect_top ;
	}

	@Override
	public double getLeft()
	{
		return rect_left ;
	}

	@Override
	public double getBottom()
	{
		return rect_bottom  ;
	}

	@Override
	public double getRight()
	{
		return rect_right ;
	}

	@Override
	public void setColor(Color c)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color getColor()
	{
		// TODO Auto-generated method stub
		return null;
	}



}

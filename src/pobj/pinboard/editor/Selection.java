package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;

public class Selection
{
	private List<Clip> selectedClips ;

	public Selection()
	{
		selectedClips = new ArrayList<Clip>();
	}
	
	public void select ( Board board , double x , double y )
	{
		selectedClips.clear();
		List<Clip> clip_list =  board.getContents();
		for (Clip clip : clip_list )
		{
			if(clip.isSelected(x, y))
			{
				selectedClips.add(clip);
				break ;
			}
		}
	}
	
	public  void toogleSelect ( Board board , double x , double y )
	{
		List<Clip> clip_list =  board.getContents();
		for (Clip clip : clip_list )
		{
			if( clip.isSelected(x, y) )
			{
				if ( ! selectedClips.contains(clip) )
					selectedClips.add(clip);
				else
					selectedClips.remove(clip);
				break ;
			}
		}
		

	}
	public void clear()
	{
		selectedClips.clear();
	}
	public List<Clip> getContents()
	{
		return selectedClips ;
	}
	public void draw(GraphicsContext ctx)
	{
		if ( !selectedClips.isEmpty() )
		{
			ctx.setStroke(Color.BLUE);
			// Init  
			double left = selectedClips.get(0).getLeft() ;
			double top =  selectedClips.get(0).getTop() ;
			double right = selectedClips.get(0).getRight() ;
			double bottom =  selectedClips.get(0).getBottom() ;
			
			for ( Clip clip : selectedClips )
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
			ctx.strokeRect( left , top  , right-left , bottom-top ) ;
		}
	}
}

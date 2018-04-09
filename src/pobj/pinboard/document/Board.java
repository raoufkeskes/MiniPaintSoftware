package pobj.pinboard.document;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Board
{
	List<Clip> clips ;

	public Board()
	{
		clips = new ArrayList<>();
	}
	
	public List<Clip> getContents()
	{
		return clips ;
	}
	
	public void addClip(Clip clip)
	{
		clips.add(clip) ;
	}
	public void addClip(List<Clip> clips) 
	{
		this.clips.addAll(clips);
	}
	
	public void removeClip(Clip clip)
	{
		clips.remove(clip) ;
	}
	public void removeClip(List<Clip> clips) 
	{
		this.clips.removeAll(clips);
	}
	
	public void draw(GraphicsContext gc)
	{
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		
		for ( Clip c : clips)
			c.draw(gc);
	}
	
}

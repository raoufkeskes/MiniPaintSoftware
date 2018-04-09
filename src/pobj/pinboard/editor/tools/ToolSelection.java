package pobj.pinboard.editor.tools;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandMove;

public class ToolSelection implements Tool
{
	double from_x ;
	double from_y ;
	
	@Override
	public void press(EditorInterface i, MouseEvent e)
	{
		from_x = e.getX();
		from_y = e.getY();
		
		if ( ! e.isShiftDown() )
				i.getSelection().select(i.getBoard(), e.getX() , e.getY() );
		else
				i.getSelection().toogleSelect(i.getBoard(), e.getX() , e.getY() );	
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e)
	{
		CommandMove cmdMove = null ;
		List<Clip> clips = i.getSelection().getContents() ;
		for ( Clip clip : clips )
		{
			cmdMove = new CommandMove(i, clip, e.getX() - from_x , e.getY() - from_y  ) ;
			cmdMove.execute();
		}
		from_x = e.getX();
		from_y = e.getY();
		
	}

	@Override
	public void release(EditorInterface i, MouseEvent e)
	{
		// Nothing 
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc)
	{
		i.getBoard().draw(gc);
	}

	@Override
	public String getName(EditorInterface editor)
	{
		return "Selection" ;
	}

}

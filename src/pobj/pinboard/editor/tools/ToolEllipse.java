package pobj.pinboard.editor.tools;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

public class ToolEllipse implements Tool
{

	private double from_x ;
	private double from_y ;
	private double to_x ;
	private double to_y ;
	private Color color ; 
	
	public ToolEllipse( )
	{
		color = randomColor() ;
	}
	
	public ToolEllipse(Color cc )
	{
		color = cc ;
	}
	
	@Override
	public void press(EditorInterface i, MouseEvent e)
	{
		from_x = e.getX();
		from_y = e.getY();
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e)
	{
		to_x = e.getX();
		to_y = e.getY();
	}

	@Override
	public void release(EditorInterface i, MouseEvent e)
	{
		to_x = e.getX();
		to_y = e.getY();
		CommandAdd cmdAdd = new CommandAdd(i, new ClipEllipse(Math.min(from_x, to_x), Math.min(from_y, to_y) ,
				  Math.max(from_x, to_x), Math.max(from_y, to_y) , color ) ) ;
		cmdAdd.execute();
		i.getUndoStack().addCommand(cmdAdd);
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc)
	{
		i.getBoard().draw(gc);
		gc.strokeOval(Math.min(from_x, to_x), Math.min(from_y, to_y)
			     , Math.abs(to_x - from_x),  Math.abs(to_y - from_y ) );
		
	}

	@Override
	public String getName(EditorInterface editor)
	{
		return "Ellipse" ;
	}
	
	


}

package pobj.pinboard.editor.tools;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.ClipImage;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

public class ToolImage implements Tool
{
	private double from_x ;
	private double from_y ;
	private File filename ;
	
	
	public ToolImage(File filename)
	{
		super();
		this.filename = filename;
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
		from_x = e.getX();
		from_y = e.getY();
	}

	@Override
	public void release(EditorInterface i, MouseEvent e)
	{
		from_x = e.getX();
		from_y = e.getY();
		CommandAdd cmdAdd = new CommandAdd(i,new ClipImage(from_x, from_y, filename )) ;
		cmdAdd.execute();
		i.getUndoStack().addCommand(cmdAdd);
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc)
	{
		i.getBoard().draw(gc);
		gc.drawImage(  new Image("file://" + filename.getAbsolutePath())  , from_x, from_y);
	}

	@Override
	public String getName(EditorInterface editor)
	{
		return "Image" ;
	}

}

package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class CommandMove implements  Command
{
	private EditorInterface editor ;
	private Clip tomove ;
	private double x ; 
	private double y ;
	
	
	public CommandMove(EditorInterface editor, Clip tomove, double x, double y)
	{
		super();
		this.editor = editor;
		this.tomove = tomove;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void execute()
	{
			tomove.move(x, y);
	}
	@Override
	public void undo()
	{
		tomove.move(-x, -y);
	}

}

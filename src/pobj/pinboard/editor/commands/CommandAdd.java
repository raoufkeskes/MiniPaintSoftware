package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class CommandAdd implements Command 
{
	private EditorInterface editor ;
	private List<Clip> list_clip_add ;
	
	
	public CommandAdd(EditorInterface editor, Clip toAdd)
	{
		this.editor = editor ;
		list_clip_add = new ArrayList<Clip>() ;
		list_clip_add.add(toAdd);
		
	}
	public CommandAdd(EditorInterface editor, List<Clip> toAdd)
	{
		this.editor = editor ;
		list_clip_add = toAdd ;
	}
	@Override
	public void execute()
	{
		editor.getBoard().addClip(list_clip_add);
			
	}
	@Override
	public void undo()
	{
		editor.getBoard().removeClip(list_clip_add);
	}
}

package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

public class CommandGroup implements Command
{
	private EditorInterface editor ;
	private List<Clip> list_clip_group; 
	private ClipGroup clipgroup ;
	
	
	
	public CommandGroup(EditorInterface editor, List<Clip> list_clip_group)
	{
		super();
		this.editor = editor;
		this.list_clip_group = list_clip_group ;
		clipgroup = new ClipGroup();
	}

	@Override
	public void execute()
	{
		editor.getBoard().removeClip(list_clip_group);
		for ( Clip clip : list_clip_group )
			clipgroup.addClip(clip);
			
		
		editor.getBoard().addClip(clipgroup);
		editor.getUndoStack().addCommand(this);
		
	}

	@Override
	public void undo()
	{
		editor.getBoard().removeClip(clipgroup);
		for ( Clip clip : list_clip_group )
			editor.getBoard().addClip(clip);
		
		clipgroup = new ClipGroup();
		
	}

}

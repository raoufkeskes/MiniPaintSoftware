package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

public class CommandUngroup implements Command
{
	private EditorInterface editor ;
	private List<Clip> list_clip_group; 
	private ClipGroup clipgroup ;
	
	
	
	public CommandUngroup(EditorInterface editor, ClipGroup clipgroup)
	{
		super();
		this.editor = editor;
		this.list_clip_group = new ArrayList<>()  ;
		this.clipgroup = clipgroup ;
	}



	@Override
	public void execute()
	{
		this.list_clip_group = clipgroup.getClips() ;
		editor.getBoard().removeClip(this.clipgroup);
		editor.getBoard().addClip( list_clip_group );
		editor.getUndoStack().addCommand(this);
	}

	@Override
	public void undo()
	{
		editor.getBoard().removeClip(list_clip_group);
		editor.getBoard().addClip( this.clipgroup );
	}

}

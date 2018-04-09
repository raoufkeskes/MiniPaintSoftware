package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;

public class Clipboard
{
	private static Clipboard clipboard = new Clipboard()  ;
	private List<Clip> copied_elements ;
	private List<ClipboardListener> listeners  ; 
	
	private Clipboard()
	{
		copied_elements = new ArrayList<Clip>() ; 
		listeners = new ArrayList<ClipboardListener>() ;
		notifyListeners();
	}
	public void copyToClipboard(List<Clip> clips)
	{
		for ( Clip clip : clips )
			copied_elements.add(clip.copy()) ;
		notifyListeners();
		
	}
	public List<Clip> copyFromClipboard()
	{
		List<Clip> copyList = new ArrayList<Clip>() ;
		for ( Clip clip : copied_elements )
			copyList.add(clip.copy()) ;
		
		return copyList ;
	}
	public void clear()
	{
		copied_elements.clear();
		notifyListeners();
	}
	public boolean isEmpty()
	{
		return copied_elements.isEmpty() ;
	}
	
	public static Clipboard getInstance()
	{
		return clipboard ;
	}
	public void addListener(ClipboardListener listener)
	{
		listeners.add(listener);
	}
	public void removeListener(ClipboardListener listener)
	{
		listeners.remove(listener);
	}
	public void notifyListeners()
	{
		for ( ClipboardListener listener : listeners )
			listener.clipboardChanged();
	}
	
}

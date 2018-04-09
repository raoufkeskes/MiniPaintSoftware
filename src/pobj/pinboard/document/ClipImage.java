package pobj.pinboard.document;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ClipImage extends AbstractClip
{
	private File filename ;
	
	public ClipImage(double left, double top, File filename )
	{
		super(left, top, left + new Image("file://" + filename.getAbsolutePath()).getWidth() 
			  , top + left + new Image("file://" + filename.getAbsolutePath()).getHeight()  , Color.WHITE);
		
		this.filename = filename ;
	}

	
	
	@Override
	public void draw(GraphicsContext ctx)
	{
		ctx.drawImage(  new Image("file://" + filename.getAbsolutePath()) , getLeft() , getTop() ) ;
	} 

	@Override
	public Clip copy()
	{
		return new ClipImage( getLeft() , getTop() , filename ) ;
	}

}

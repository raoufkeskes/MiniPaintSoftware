package pobj.pinboard.editor.tools;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.editor.EditorInterface;

public interface Tool {
	public void press(EditorInterface i, MouseEvent e);
	public void drag(EditorInterface i, MouseEvent e);
	public void release(EditorInterface i, MouseEvent e);
	public void drawFeedback(EditorInterface i, GraphicsContext gc);
	public String getName(EditorInterface editor);
	
	/* Utilisé avant de faire la palette de couleur */
    public default Color randomColor()
    {
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return Color.rgb(r, g, b);
    }
}

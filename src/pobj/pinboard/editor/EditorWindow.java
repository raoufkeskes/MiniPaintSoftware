package pobj.pinboard.editor;



import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.commands.CommandAdd;
import pobj.pinboard.editor.commands.CommandGroup;
import pobj.pinboard.editor.commands.CommandUngroup;
import pobj.pinboard.editor.tools.Tool;
import pobj.pinboard.editor.tools.ToolEllipse;
import pobj.pinboard.editor.tools.ToolImage;
import pobj.pinboard.editor.tools.ToolRect;
import pobj.pinboard.editor.tools.ToolSelection;

public class EditorWindow implements EditorInterface , ClipboardListener 
{
	
	final static int CANVAS_WIDTH = 640 ;
    final static int CANVAS_HEIGHT = 540 ;
	private Board board  ;
	private Selection selection = null ;
	private MenuItem paste = new MenuItem("Paste");
	private Color currentColor = Color.DEEPPINK ;
	private Tool default_tool = new ToolRect(currentColor) ;
	private Tool current_tool = default_tool  ;
	private CommandStack cmdStack = null ;
	
	
	
	public EditorWindow( Stage stage )
	{
		board = new Board() ;
		selection = new Selection();
		cmdStack = new CommandStack();
		Clipboard.getInstance().addListener(this);
		
		stage.setTitle("PinBoard <untitled>");
		VBox vbox = new VBox(10) ;
		Label label = new Label(current_tool.getName(this)) ;
		Canvas canvas = new Canvas( CANVAS_WIDTH , CANVAS_HEIGHT );
		
		
		Menu file =  new Menu("File");
		MenuItem new_file = new MenuItem("Open");
		MenuItem close_file = new MenuItem("Close");
		MenuItem save_file = new MenuItem("Save"); 
		save_file.setOnAction((e)-> 
		{
			FileChooser fileChooser = new FileChooser();
			
			 //Set extension filter
	        FileChooser.ExtensionFilter extFilter = 
	                new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
			fileChooser.getExtensionFilters().add(extFilter);
	      
	        //Show save file dialog
	        File filee = fileChooser.showSaveDialog(stage);
	        
	        if(filee != null){
	            try {
	                WritableImage writableImage = new WritableImage( CANVAS_WIDTH , CANVAS_HEIGHT ) ;
	                canvas.snapshot(null, writableImage);
	                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
	                ImageIO.write(renderedImage, "png" , filee);
	            } catch (IOException ex) {
	                System.out.println(ex.getMessage());
	            }
	        }
		});
		new_file.setOnAction((e)-> { EditorWindow window_new = new EditorWindow(new Stage()); });
		close_file.setOnAction((e)-> { stage.close(); });
		file.getItems().addAll(new_file ,new SeparatorMenuItem() , save_file , new SeparatorMenuItem() , close_file ) ;
		
		Menu edit =  new Menu("Edit");
		MenuItem copy  = new MenuItem("Copy");
		MenuItem delete = new MenuItem("Delete");
		MenuItem group  = new MenuItem("group");
		MenuItem ungroup  = new MenuItem("Ungroup");
		MenuItem redo  = new MenuItem("Redo");
		MenuItem undo  = new MenuItem("Undo");
		
		edit.getItems().addAll(copy , new SeparatorMenuItem() , paste , new SeparatorMenuItem() 
				, delete , new SeparatorMenuItem() , group , new SeparatorMenuItem()  , ungroup , 
				 new SeparatorMenuItem()  , undo ,  new SeparatorMenuItem()  , redo ) ;
		copy.setOnAction((e)-> 
		{
			Clipboard.getInstance().copyToClipboard(selection.getContents());
		} );
		paste.setOnAction((e)-> 
		{
			List<Clip> pasted_clips  = Clipboard.getInstance().copyFromClipboard();
			board.addClip(pasted_clips);
			selection.clear();
			Clipboard.getInstance().clear();
			draw(canvas) ;
			
		} );
		paste.setDisable(true);
		delete.setOnAction((e)-> 
		{
			board.removeClip(selection.getContents());
			selection.clear();
			draw(canvas) ;
		} );
		group.setOnAction((e)-> 
		{
			if ( ! selection.getContents().isEmpty() )
			{
				CommandGroup cmdGrp =  new CommandGroup(this, selection.getContents() ) ;
				cmdGrp.execute();
				this.getUndoStack().addCommand(cmdGrp);
				selection.clear();
				draw(canvas) ;
			}
			
			
		} );
		ungroup.setOnAction((e)-> 
		{
			if ( ! selection.getContents().isEmpty() )
			{
				ClipGroup clip_group = (ClipGroup) selection.getContents().get(0) ;
				CommandUngroup CmdUngrp = new CommandUngroup(this, clip_group) ;
				CmdUngrp.execute();
				this.getUndoStack().addCommand(CmdUngrp);
				selection.clear();
				draw(canvas) ;
			}
			
		} );
		redo.setOnAction((e)-> { cmdStack.redo(); selection.clear(); draw(canvas) ; } ) ;
		undo.setOnAction((e)-> { cmdStack.undo(); selection.clear(); draw(canvas) ; } ) ;
		Menu tools =  new Menu("Tools");
		MenuItem rectangle  = new MenuItem("Rectangle");
		MenuItem ellipse = new MenuItem("Elipse");
		MenuItem Image = new MenuItem("Image"); 

		Button box_btn = new Button("Box");
		box_btn.setOnAction((e)-> 
		{
			ClipOnClick(new ToolRect(currentColor) , label);
		} );
		Button ellipse_btn = new Button("Ellipse");
		ellipse_btn.setOnAction((e)->
		{
			ClipOnClick(new ToolEllipse(currentColor) , label);
		} );
		Button img_btn = new Button("Img...");
		img_btn.setOnAction((e)->
		{
			
			FileChooser fileChooser = new FileChooser() ;
			FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
			fileChooser.getExtensionFilters().add(imageFilter);
			File f = fileChooser.showOpenDialog(stage);
			if ( f != null )
			{
				ClipOnClick(new ToolImage(f) , label);
			}
		} );
		Button Selection_btn = new Button("Selection");
		Selection_btn.setOnAction((e)->
		{
			ClipOnClick(new ToolSelection() , label  );
		} );
		final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue( currentColor );
        colorPicker.setOnAction((e)->
		{
			currentColor = colorPicker.getValue() ;
			
			if ( current_tool instanceof ToolRect )   box_btn.fire() ; 
			else if ( current_tool instanceof ToolEllipse  ) ellipse_btn.fire();
		} );
       
		rectangle.setOnAction((e)-> { img_btn.requestFocus() ;  img_btn.fire();  });
		ellipse.setOnAction((e)-> {  ellipse_btn.requestFocus() ; ellipse_btn.fire(); });
		Image.setOnAction((e)-> {  img_btn.requestFocus() ; img_btn.fire(); });
		tools.getItems().addAll(rectangle , new SeparatorMenuItem() , ellipse , new SeparatorMenuItem() , Image ) ;
		
		
		
        
		MenuBar menubar = new MenuBar(file , edit , tools );
		
		
		
		
		ToolBar toolbar = new ToolBar ( box_btn , ellipse_btn , img_btn  , Selection_btn , colorPicker ) ;
		
		
		canvas.setOnMousePressed( (e)-> 
		{ 	
			press(e);
			draw(canvas) ;
		} );
		canvas.setOnMouseDragged( (e)-> { 
			drag(e); 
			current_tool.drawFeedback(this,canvas.getGraphicsContext2D());
			} );
		canvas.setOnMouseReleased( (e)-> { release(e); draw(canvas) ;  } );
		
		board.draw(canvas.getGraphicsContext2D());
		Separator separator = new Separator();
		vbox.getChildren().addAll(menubar,toolbar,canvas,separator,label);
		Scene scene = new Scene(vbox) ;
		stage.setScene(scene);
		stage.show();
	}
	@Override
	public Board getBoard()
	{
		return board ;
	}
	
	
	public void press(MouseEvent e )
	{
		current_tool.press(this , e) ;
	}
	
	public void drag(MouseEvent e )
	{
		current_tool.drag(this , e) ;
	}
	
	public void release(MouseEvent e )
	{
		current_tool.release(this , e) ;
	}
	
	public void draw(Canvas canvas )
	{
		board.draw(canvas.getGraphicsContext2D());
		selection.draw(canvas.getGraphicsContext2D());
	}
	
	public void ClipOnClick(Tool t , Label l )
	{
		current_tool = t ; 
		l.setText(current_tool.getName(this));
	}
	@Override
	public Selection getSelection()
	{
		return selection;
	}
	@Override
	public void clipboardChanged()
	{
		if (Clipboard.getInstance().isEmpty())
			paste.setDisable(true);
		else
			paste.setDisable(false);
	}
	@Override
	public CommandStack getUndoStack()
	{
		return cmdStack  ;
	}
	
	
	

}

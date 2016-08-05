package de.tarasiks.Editor;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.text.DateFormat;

public class Editor extends Frame implements ActionListener, ItemListener {

	private List list;
	private TextField details;
	private Panel buttons;
	private Button up, close;
	private File currentDir;
	private FilenameFilter filter;
	private String[] files;
	private DateFormat dateFormatter = DateFormat
			.getDateInstance(DateFormat.SHORT);

	public Editor(String directory, FilenameFilter filter) {
		super("File Lister");
		this.filter = filter;

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		list = new List(12, false);
		list.setFont(new Font("MonoSpaced", Font.PLAIN, 14));
		list.addActionListener(this);
		list.addItemListener(this);

		details = new TextField();
		details.setFont(new Font("MonoSpaced", Font.PLAIN, 12));
		details.setEditable(false);

		buttons = new Panel();
		buttons.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 5));
		buttons.setFont(new Font("SansSerif", Font.BOLD, 14));

		up = new Button("Up Directory");
		close = new Button("Close");
		up.addActionListener(this);
		close.addActionListener(this);

		buttons.add(up);
		buttons.add(close);

		this.add(list, "Center");
		this.add(details, "North");
		this.add(buttons, "South");
		this.setSize(500, 350);

		listDirectory(directory);

	}

	public void listDirectory(String directory) {

		File dir = new File(directory);
		if (!dir.isDirectory())
			throw new IllegalArgumentException("FileLister: no such directory"
					+ directory);

		files = dir.list();
		java.util.Arrays.sort(files);

		list.removeAll();
		list.add("Up to Parent Directory");
		for (int i = 0; i < files.length; i++) {
			list.add(files[i]);
		}

		this.setTitle(directory);
		details.setText(directory);

		currentDir = dir;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		int i = list.getSelectedIndex() - 1;
		if (i < 0)
			return;
		String filename = files[i];
		File f = new File(currentDir, filename);
		if (!f.exists())
			throw new IllegalArgumentException("FileLister: no such directory"
					+ f.getName());

		String info = filename;
		if(f.isDirectory())
			info += File.separator;
	
		info += " " + f.length() + "bytes ";
		info += dateFormatter.format(new java.util.Date(f.lastModified()));
		if(f.canRead())
			info += " Read";
		if(f.canWrite())
			info += " Write";
		
		details.setText(info);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

			if(e.getSource() == close)
				this.dispose();
			else if(e.getSource() == up)
				up();
			else if(e.getSource() == list){
				int i = list.getSelectedIndex();
				if(i==0)
					up();
				else{
					String name = files[i-1];
					File f = new File(currentDir, name);
					String fullname = f.getAbsolutePath();
					if(f.isDirectory())
						listDirectory(fullname);
				//	else
					//	new FileViewer(fullname).setVisible(true);
				}
			}
	}
	
	protected void up(){
		String parent = currentDir.getParent();
		if(parent == null)
			return;
		listDirectory(parent);
	}
	
	public static void main(String[] args) {
		Editor f;
		String directory = null;
		
		directory = System.getProperty("user.dir");
		f = new Editor(directory, null);
		f.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		f.setVisible(true);
	}
}

class FileViewer extends Frame implements ActionListener{

	String directory;
	TextArea textarea;
	
	/**
	 * 
	 */
	public FileViewer(){this(null,null);}
	public FileViewer(String filename){this(null,filename);}
	
	public FileViewer(String directory, String filenmae){
		super();
		//Test
		//priintff
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		//test
		//test2
		//
	}
}









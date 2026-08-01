package searchEngine;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;


public class GUI implements ActionListener {
	String field;
	String question;
	JFrame frame;
	JMenuItem artist;
	JMenuItem title;
	JMenuItem album;
	JMenuItem year;
	JMenuItem date;
	JMenuItem lyrics;
	JMenuItem all;
	JTextField searchField;
	JButton button;
	
	
	public GUI() {
		frame = new JFrame();
		frame.setTitle("searchEngine");
		frame.setLayout(new FlowLayout());
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar jmb = new JMenuBar();
		frame.setJMenuBar(jmb);
		
		JMenu fields = new JMenu("Search By");
		jmb.add(fields);
		
		artist = new JMenuItem("Artist");
		title = new JMenuItem("Title");
		album = new JMenuItem("Album");
		year = new JMenuItem("Year");
		date = new JMenuItem("Date");
		lyrics = new JMenuItem("Lyrics");
		all = new JMenuItem("All");
		
		
		artist.addActionListener(this);
		title.addActionListener(this);
		album.addActionListener(this);
		year.addActionListener(this);
		date.addActionListener(this);
		lyrics.addActionListener(this);
		all.addActionListener(this);
		
		fields.add(artist);
		fields.add(title);
		fields.add(album);
		fields.add(year);
		fields.add(date);
		fields.add(lyrics);
		fields.add(all);
		
		button = new JButton("Search");	
		button.addActionListener(this);
		
		searchField = new JTextField();
		searchField.setPreferredSize(new Dimension(250,20));
		
		frame.add(button);
		frame.add(searchField);
		frame.setVisible(true);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == artist) {
			field = "artist";
		}
		if(e.getSource() == title) {
			field = "title";
		}
		if(e.getSource() == album) {
			field = "album";
		}
		if(e.getSource() == year) {
			field = "year";
		}
		if(e.getSource() == date) {
			field = "date";
		}
		if(e.getSource() == lyrics) {
			field = "lyrics";
		}
		if(e.getSource() == all) {
			field = "all";
		}
		if(e.getSource() == button) {
			question = searchField.getText();
			frame.dispose();
			try {
				searchResults results = new searchResults(field,question);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new GUI();
	}
}
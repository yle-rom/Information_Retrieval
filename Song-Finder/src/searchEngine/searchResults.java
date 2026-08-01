package searchEngine;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;
import org.apache.lucene.document.Document;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class searchResults implements ActionListener{
	JFrame frame;
	JScrollPane js;
	JTable jt;
	JMenuItem artists;
	String field;
	String question;
	List<Document> results = new ArrayList<>();
	String[] col = new String[]{"Artist","Title","Album","Year","Date","Lyrics"};
	Object[][] data;
	JButton button;
	Object[][] newData;
	int totalResults;
	int currentIndex;
	
	
	public searchResults(String field,String question) throws IOException {
		frame = new JFrame();
		frame.setTitle("searchResults");
		frame.setLayout(new FlowLayout());
		frame.setSize(500,500);
		
		JMenuBar jmb = new JMenuBar();
		frame.setJMenuBar(jmb);
		JMenu fields = new JMenu("Group By");
		jmb.add(fields);
		artists = new JMenuItem("Artists");
		artists.addActionListener(this);
		fields.add(artists);
		
		
		this.field = field;
		this.question = question;
		
		searchEngine myEngine = new searchEngine();
		results = myEngine.search(field,question);
		totalResults = results.size();
		data = new Object[totalResults][6];
        int count = 0;
        for (Document result : results) {
            String artist = result.get("artist");
            String title = result.get("title");
            String album = result.get("album");
            String year = result.get("year");
            String date = result.get("date");
            String lyrics = result.get("lyrics");
            data[count][0] = artist;
            data[count][1] = title;
            data[count][2] = album;
            data[count][3] = year;
            data[count][4] = date;
            data[count][5] = lyrics;
            count ++;
        }
        
        currentIndex = Math.min(10, totalResults);
		newData = new Object[totalResults][6];
		for(int i = 0; i<currentIndex; i++) {
        	newData[i][0] = data[i][0];
        	newData[i][1] = data[i][1];
        	newData[i][2] = data[i][2];
        	newData[i][3] = data[i][3];
        	newData[i][4] = data[i][4];
        	newData[i][5] = data[i][5];
        }
		
		
        if(totalResults<10) {
        	jt = new JTable(data,col);
        }
        else {
        	button = new JButton("Load 10 More");
        	button.addActionListener(this);
        	frame.add(button);
            jt = new JTable(newData,col);
        }
        
        
        
        if(field == "artist") {
        	TableColumn column = jt.getColumnModel().getColumn(0);
            column.setCellRenderer(new HighlightWordRenderer(question));
        }
        else if(field == "title") {
        	TableColumn column = jt.getColumnModel().getColumn(1);
            column.setCellRenderer(new HighlightWordRenderer(question));
        }else if(field == "album") {
        	TableColumn column = jt.getColumnModel().getColumn(2);
            column.setCellRenderer(new HighlightWordRenderer(question));
        }
        else if(field == "year") {
        	TableColumn column = jt.getColumnModel().getColumn(3);
            column.setCellRenderer(new HighlightWordRenderer(question));
        }else if(field == "date") {
        	TableColumn column = jt.getColumnModel().getColumn(4);
            column.setCellRenderer(new HighlightWordRenderer(question));
        }
        else if(field == "lyrics") {
        	TableColumn column = jt.getColumnModel().getColumn(5);
            column.setCellRenderer(new HighlightWordRenderer(question));
        }
        
        js = new JScrollPane(jt);
       
       
        frame.add(js);
        frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button) {
			int temp = Math.min(currentIndex + 10, totalResults);
			for(int i =currentIndex; i<temp; i++) {
				newData[i][0] = data[i][0];
	        	newData[i][1] = data[i][1];
	        	newData[i][2] = data[i][2];
	        	newData[i][3] = data[i][3];
	        	newData[i][4] = data[i][4];
	        	newData[i][5] = data[i][5];
			}
			currentIndex = temp;
			JTable jtNew = new JTable(newData,col);
			js.add(jtNew);
			frame.add(js);
			frame.setVisible(true);
		}
		if(e.getSource() == artists) {
			Set<String> uniqueArtists = new HashSet<>();
			for(int i = 0; i < totalResults; i++) {
				String artist = data[i][0].toString();
				if(!uniqueArtists.contains(artist)) {
					uniqueArtists.add(artist);
				}
			}
			String[] artists = uniqueArtists.toArray(new String[uniqueArtists.size()]);
			for(int i = 0; i < artists.length; i++) {
				Object[][] temp = new Object[totalResults][6];
				int counter = 0;
				for(int j = 0; j < totalResults; j ++) {
					if(data[j][0].equals(artists[i])){
						temp[counter][0] = data[j][0];
						temp[counter][1] = data[j][1];
						temp[counter][2] = data[j][2];
						temp[counter][3] = data[j][3];
						temp[counter][4] = data[j][4];
						temp[counter][5] = data[j][5];
						counter ++;
					}
				}
				
				JFrame newFrame = new JFrame();
				JTable tempJT = new JTable(temp,col);
				if(field == "artist") {
		        	TableColumn column = tempJT.getColumnModel().getColumn(0);
		            column.setCellRenderer(new HighlightWordRenderer(question));
		        }
		        else if(field == "title") {
		        	TableColumn column = tempJT.getColumnModel().getColumn(1);
		            column.setCellRenderer(new HighlightWordRenderer(question));
		        }else if(field == "album") {
		        	TableColumn column = tempJT.getColumnModel().getColumn(2);
		            column.setCellRenderer(new HighlightWordRenderer(question));
		        }
		        else if(field == "year") {
		        	TableColumn column = tempJT.getColumnModel().getColumn(3);
		            column.setCellRenderer(new HighlightWordRenderer(question));
		        }else if(field == "date") {
		        	TableColumn column = tempJT.getColumnModel().getColumn(4);
		            column.setCellRenderer(new HighlightWordRenderer(question));
		        }else if(field == "lyrics") {
		        	TableColumn column = jt.getColumnModel().getColumn(5);
		            column.setCellRenderer(new HighlightWordRenderer(question));
		        }
				JScrollPane tempJS = new JScrollPane(tempJT);
				newFrame.setTitle(artists[i]);
				newFrame.setLayout(new FlowLayout());
				newFrame.setSize(500,500);
				newFrame.add(tempJS);
				newFrame.setVisible(true);
			}
		}
	}
}
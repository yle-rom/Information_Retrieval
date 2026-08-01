package searchEngine;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.store.ByteBuffersDirectory;



public class searchEngine {
	
    public List<Document> search(String field,String q) throws IOException{
        Analyzer analyzer = new StandardAnalyzer();
        Directory directory = new ByteBuffersDirectory();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter iwriter = new IndexWriter(directory, config);
        String[] fields = {"artist","title","album","year","date","lyrics"};
        
        try {
            CSVParser csvParser = new CSVParser(new FileReader("D:\\Uni\\6th semester\\Information Retrieval\\various_artists.csv"), CSVFormat.EXCEL.withDelimiter(',').withHeader("Artist","Title","Album","Year","Date","Lyric"));
            for (CSVRecord record : csvParser) {
            	String artist = record.get("Artist");
                String title = record.get("Title");
                String album = record.get("Album");
                String year = record.get("Year");
                String date = record.get("Date");
                String lyrics = record.get("Lyric");
                Document doc = new Document();
                doc.add(new TextField("artist", artist, Field.Store.YES));
                doc.add(new TextField("title", title, Field.Store.YES));
                doc.add(new TextField("album", album, Field.Store.YES));
                doc.add(new TextField("year", year, Field.Store.YES));
                doc.add(new TextField("date", date, Field.Store.YES));
                doc.add(new TextField("lyrics", lyrics, Field.Store.YES));
                iwriter.addDocument(doc);
                
            }
            iwriter.commit();
            iwriter.close();
            IndexReader ireader = DirectoryReader.open(directory);
            IndexSearcher isearcher = new IndexSearcher(ireader);
            QueryParser parser = new QueryParser(field,analyzer);
            MultiFieldQueryParser multiParser = new MultiFieldQueryParser(fields,analyzer);
            if(field == "all") {
            	parser = multiParser;
            }
            Query query = parser.parse(q);
            TopDocs hits = isearcher.search(query, 1000);
            ScoreDoc[] scoreDocs = hits.scoreDocs;
            List<Document> searchResults = new ArrayList<>();
            for(ScoreDoc scoreDoc : scoreDocs) {
        	   Document resultDoc = isearcher.doc(scoreDoc.doc);
        	   searchResults.add(resultDoc);
            }
            ireader.close();
            directory.close();
            return searchResults;
        }
        catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
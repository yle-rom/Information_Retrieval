# Information Retrieval Project

![Language](https://img.shields.io/badge/Language-Java-blue.svg)
![Library](https://img.shields.io/badge/Library-Apache_Lucene-green.svg)

## Project Overview

This repository contains a Java-based Information Retrieval system designed to index and search a large dataset of songs and artists. The application processes a Kaggle dataset containing musical metadata and lyrics, allowing users to query specific fields through a custom graphical user interface.

## Core Features

*   **Targeted Search:** Users can search across all fields simultaneously or narrow their queries to specific attributes: Artist, Title, Album, Year, Date, or Lyrics.
*   **Interactive GUI:** Built with Java Swing, featuring a dropdown menu for field selection and a dedicated search bar.
*   **Result Pagination:** Search results are initially limited to the top 10 hits, with a "Load 10 More" button to dynamically append subsequent results to the data table.
*   **Keyword Highlighting:** Query terms are automatically highlighted in yellow within the results table for quick visual scanning.
*   **Data Grouping:** Users can group search results by "Artist," which generates separate viewing windows containing the specific discography hits for each unique artist in the search results.

## Technical Implementation

*   **Indexing and Searching:** Powered by Apache Lucene. The application reads from a CSV file and creates a searchable index in memory using a ByteBuffersDirectory.
*   **Text Analysis:** Utilizes Lucene's StandardAnalyzer to tokenize text, convert strings to lowercase, and remove common stop-words, ensuring high-quality search matching.
*   **Query Parsing:** Uses QueryParser for single-field searches and MultiFieldQueryParser for cross-field queries.
*   **User Interface:** Implemented using standard javax.swing components (JFrame, JTable, JMenuBar) with custom cell rendering for HTML-based text highlighting.

## Known Limitations

*   The index is built in-memory upon application startup. As a result, search history is not persistently saved between sessions, which prevents the implementation of query expansion or alternative search suggestions based on past user behavior.

## What was Learned

*   How to build and query an inverted index using the Apache Lucene framework.
*   The fundamental principles of text analysis, including tokenization and stop-word filtering using standard analyzers.
*   How to parse large external CSV datasets and map their contents to searchable document fields in memory.
*   Designing and managing an interactive desktop application using Java Swing.
*   Implementing custom table cell renderers to dynamically alter UI components, such as applying HTML to highlight specific text strings.
## How to Run

```bash
# Clone the repository
git clone [https://github.com/YourUsername/Information_Retrieval.git](https://github.com/YourUsername/Information_Retrieval.git)

# Navigate to the directory
cd Information_Retrieval

# Important: Update the absolute file path in searchEngine.java to point to your local various_artists.csv file before compiling.
# Ensure Apache Lucene and Apache Commons CSV are added to your classpath.

# Compile the Java files
javac searchEngine/*.java

# Run the graphical interface
java searchEngine.GUI

package unit;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestMarketComprehension {

    @Before
    public void setup(){
        SearchService _searchService = mock(SearchService.class);
        when(_searchService.search(any())).thenReturn(generateDocumentList());
        DocumentHandler _docHandler= new DocumentHandler();

    }

    @Test
    public void testSearchReturnDocuments(){
        String[] keywords = {"Ponsonby", "Dog", "Walking"};
        SearchQuery query = new SearchQuery(keywords);
        ArrayList<Document> resultDocs = _searchService.search(query);

        Assert.assertTrue(resultDocs.size() == 10);
        Assert.assertTrue(resultDocs.get(0).name().equals("Ponsonby Dog Walking Inc"));
        fail();
    }

    @Test
    public void testDocumentClustering(){
        String[] keywords = {"Ponsonby", "Dog", "Walking"};
        SearchQuery query = new SearchQuery(keywords);
        ArrayList<Document> resultDocs = _searchService.search(query);

        ArrayList<Category> categorisedDocs = _docHandler.categorise(resultDocs);

        /* E.g. Documents were categorised into three separate categories - Dog/Cat/Goat Walking */
        Assert.assertTrue(categorisedDocs.size() == 3);
        Assert.assertTrue(categorisedDocs.get(0).name.equals("Dog Walking"));

        fail();
    }

    @Test
    public void testCategoryLabelExtraction(){
        String[] keywords = {"Ponsonby", "Dog", "Walking"};
        SearchQuery query = new SearchQuery(keywords);
        ArrayList<Document> resultDocs = _searchService.search(query);

        ArrayList<Category> categorisedDocs = _docHandler.categorise(resultDocs);

        for(Category c : categorisedDocs){
            c.extractLabel();
        }
        Assert.assertTrue(categorisedDocs.get(0).label().equals("Dog"));
        fail();
    }

    @Test
    public void testCategorySummaryGeneration(){
        String[] keywords = {"Ponsonby", "Dog", "Walking"};
        SearchQuery query = new SearchQuery(keywords);
        ArrayList<Document> resultDocs = _searchService.search(query);

        ArrayList<Category> categorisedDocs = _docHandler.categorise(resultDocs);

        for(Category c : categorisedDocs){
            c.extractSummary();
        }
        Assert.assertTrue(categorisedDocs.get(0).summary().equals("Dog Walking"));
        fail();
    }

    private ArrayList<Document> generateDocumentList() {
        ArrayList<Document> docs = new ArrayList<Document>();
        docs.add(new Document("Ponsonby Dog Walking Inc"));
        docs.add(new Document("Newmarket Dog Walking Inc"));
        docs.add(new Document("Parnell Dog Walking Inc"));
        docs.add(new Document("Mount Eden Cat Walking Inc"));
        docs.add(new Document("Sandringham Cat Walking Inc"));
        docs.add(new Document("Grafton Cat Walking Inc"));
        docs.add(new Document("Eden Terrace Goat Walking Inc"));
        docs.add(new Document("Albany Goat Walking Inc"));
        docs.add(new Document("Kingsland Goat Walking Inc"));
        docs.add(new Document("Epsom Goat Walking Inc"));
        return docs;
    }
}

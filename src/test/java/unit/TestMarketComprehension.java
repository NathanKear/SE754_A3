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
        fail();
    }

    @Test
    public void testCategoryLabelExtraction(){
        fail();
    }

    @Test
    public void testCategorySummaryGeneration(){
        fail();
    }

    private ArrayList<Document> generateDocumentList() {
        ArrayList<Document> docs = new ArrayList<Document>();
        docs.add(new Document("Ponsonby Dog Walking Inc"));
        docs.add(new Document("Newmarket Dog Walking Inc"));
        docs.add(new Document("Parnell Dog Walking Inc"));
        docs.add(new Document("Mount Eden Dog Walking Inc"));
        docs.add(new Document("Sandringham Dog Walking Inc"));
        docs.add(new Document("Grafton Dog Walking Inc"));
        docs.add(new Document("Eden Terrace Dog Walking Inc"));
        docs.add(new Document("Albany Dog Walking Inc"));
        docs.add(new Document("Kingsland Dog Walking Inc"));
        docs.add(new Document("Epsom Dog Walking Inc"));
        return docs;
    }
}

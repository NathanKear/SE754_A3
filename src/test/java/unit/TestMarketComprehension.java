package unit;

import Result.Category;
import Result.Document;
import Result.DocumentHandler;
import Search.*;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestMarketComprehension {
    private SearchService _searchService;
    private DocumentHandler _docHandler;
    private NaturalLanguageProcessor _langProcessor;

    @Before
    public void setup(){
        _docHandler = new DocumentHandler();

        _searchService = mock(SearchService.class);
        when(_searchService.search(any(SearchQuery.class))).thenReturn(generateDocumentList());

        _langProcessor = mock(NaturalLanguageProcessor.class);
        when(_langProcessor.extractCategoryLabel(any(Category.class))).thenReturn("Dog");
        when(_langProcessor.extractCategorySummary(any(Category.class))).thenReturn("Dog Walking");
    }


    @Test
    public void testSearchReturnDocuments(){
        String[] keywords = {"Auckland", "Pet", "Walking"};
        SearchQuery query = new SearchQuery(keywords);
        ArrayList<Document> resultDocs = _searchService.search(query);

        Assert.assertTrue(resultDocs.size() == 10);
        Assert.assertTrue(resultDocs.get(0).name().equals("Dog Walking Ponsonby Inc"));

    }

    @Test
    public void testDocumentClustering(){
        String[] keywords = {"Auckland", "Pet", "Walking"};
        SearchQuery query = new SearchQuery(keywords);
        ArrayList<Document> resultDocs = _searchService.search(query);

        ArrayList<Category> categorisedDocs = _docHandler.categorise(resultDocs);

        /* E.g. Documents were categorised into three separate categories (Dog/Cat/Goat) */
        Assert.assertTrue(categorisedDocs.size() == 3);
    }

    @Test
    public void testCategoryLabelExtraction(){
        String[] keywords = {"Auckland", "Pet", "Walking"};
        SearchQuery query = new SearchQuery(keywords);
        ArrayList<Document> resultDocs = _searchService.search(query);

        ArrayList<Category> categorisedDocs = _docHandler.categorise(resultDocs);

        for(Category c : categorisedDocs) {
            c.setLabel(_langProcessor.extractCategoryLabel(c));
        }
        Assert.assertTrue(categorisedDocs.get(0).label().equals("Dog"));
    }

    @Test
    public void testCategorySummaryGeneration(){
        String[] keywords = {"Auckland", "Pet", "Walking"};
        SearchQuery query = new SearchQuery(keywords);
        ArrayList<Document> resultDocs = _searchService.search(query);

        ArrayList<Category> categorisedDocs = _docHandler.categorise(resultDocs);
        for(Category c : categorisedDocs) {
            c.setSummary(_langProcessor.extractCategorySummary(c));
        }
        Assert.assertTrue(categorisedDocs.get(0).summary().equals("Dog Walking"));
    }

    private ArrayList<Document> generateDocumentList() {
        ArrayList<Document> docs = new ArrayList<Document>();
        docs.add(new Document("Dog Walking Ponsonby Inc"));
        docs.add(new Document("Dog Walking Newmarket Inc"));
        docs.add(new Document("Dog Walking Parnell Inc"));
        docs.add(new Document("Cat Walking Mount Eden Inc"));
        docs.add(new Document("Cat Walking Sandringham Inc"));
        docs.add(new Document("Cat Walking Grafton Inc"));
        docs.add(new Document("Goat Walking Eden Terrace Inc"));
        docs.add(new Document("Goat Walking Albany Inc"));
        docs.add(new Document("Goat Walking Kingsland Inc"));
        docs.add(new Document("Goat Walking Epsom Inc"));
        return docs;
    }
}

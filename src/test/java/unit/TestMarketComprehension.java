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
        when(_langProcessor.findCategoryLabel(any(Category.class))).thenReturn("Dog");
        when(_langProcessor.findCategorySummary(any(Category.class))).thenReturn("Dog Walking in Auckland");
    }

    @Test
    public void testEmptySearch(){
        String[] keywords = {"Waiheke Island", "Pet", "Care"};
        SearchQuery query = new SearchQuery(keywords);
        ArrayList<Document> resultDocs = new ArrayList<Document>();

        Assert.assertTrue(resultDocs.isEmpty());

        ArrayList<Category> categorisedDocs = _docHandler.categorise(resultDocs);
        Assert.assertTrue(categorisedDocs.isEmpty());
    }

    @Test
    public void testSearchReturnDocuments(){
        String[] keywords = {"Auckland", "Pet", "Care"};
        SearchQuery query = new SearchQuery(keywords);
        ArrayList<Document> resultDocs = _searchService.search(query);

        // 10 auckland pet-care related documents found
        Assert.assertTrue(resultDocs.size() == 10);
        Assert.assertTrue(resultDocs.get(0).name().equals("Dog Walking Ponsonby Inc"));
    }

    @Test
    public void testDocumentClustering(){
        String[] keywords = {"Auckland", "Pet", "Care"};
        SearchQuery query = new SearchQuery(keywords);
        ArrayList<Document> resultDocs = _searchService.search(query);

        ArrayList<Category> categorisedDocs = _docHandler.categorise(resultDocs);

        // Documents clustered into three separate categories based on the pet(Dog/Cat/Goat)
        Assert.assertTrue(categorisedDocs.size() == 3);

        Assert.assertTrue(categorisedDocs.get(0).getDocuments().size() == 3);
        Assert.assertTrue(categorisedDocs.get(1).getDocuments().size() == 4);
        Assert.assertTrue(categorisedDocs.get(2).getDocuments().size() == 3);
    }

    @Test
    public void testCategoryLabelExtraction(){
        String[] keywords = {"Auckland", "Pet", "Care"};
        SearchQuery query = new SearchQuery(keywords);
        ArrayList<Document> resultDocs = _searchService.search(query);

        ArrayList<Category> categorisedDocs = _docHandler.categorise(resultDocs);

        for(Category c : categorisedDocs) {
            c.setLabel(_langProcessor.findCategoryLabel(c));
        }

        // Category labels identified by the pet
        Assert.assertTrue(categorisedDocs.get(0).label().equals("Dog"));
    }

    @Test
    public void testCategorySummaryGeneration(){
        String[] keywords = {"Auckland", "Pet", "Care"};
        SearchQuery query = new SearchQuery(keywords);
        ArrayList<Document> resultDocs = _searchService.search(query);

        ArrayList<Category> categorisedDocs = _docHandler.categorise(resultDocs);
        for(Category c : categorisedDocs) {
            c.setSummary(_langProcessor.findCategorySummary(c));
        }

        // Category summaries identified by the pet, activity and location
        Assert.assertTrue(categorisedDocs.get(0).summary().equals("Dog Walking in Auckland"));
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

    private ArrayList<Document> generateEmptyList(){
       return new ArrayList<Document>();
    }
}

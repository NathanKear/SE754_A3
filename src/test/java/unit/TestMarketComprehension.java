package unit;

import Result.Category;
import Result.Document;
import Result.DocumentHandler;
import Result.LanguageProcessor;
import Search.*;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestMarketComprehension {
    private SearchService _searchService;
    private DocumentHandler _docHandler;
    private LanguageProcessor _langProcessor;

    @Before
    public void setup(){
        _searchService = mock(SearchService.class);
        when(_searchService.search(any(SearchQuery.class))).thenReturn(generateDocumentList());

        _docHandler= mock(DocumentHandler.class);
        when(_docHandler.categorise(any(ArrayList.class))).thenReturn(generateCategorisedList());

        _langProcessor = mock(LanguageProcessor.class);
        when(_langProcessor.extractCategoryLabel(any(Category.class))).thenReturn("Dog");
        when(_langProcessor.extractCategorySummary(any(Category.class))).thenReturn("Dog Walking");
    }


    @Test
    public void testSearchReturnDocuments(){
        String[] keywords = {"Auckland", "Pet", "Walking"};
        SearchQuery query = new SearchQuery(keywords);
        ArrayList<Document> resultDocs = _searchService.search(query);

        Assert.assertTrue(resultDocs.size() == 10);
        Assert.assertTrue(resultDocs.get(0).name().equals("Ponsonby Dog Walking Inc"));

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

        for(Category c : categorisedDocs){
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

        for(Category c : categorisedDocs){
            c.setSummary(_langProcessor.extractCategorySummary(c));
        }
        Assert.assertTrue(categorisedDocs.get(0).summary().equals("Dog Walking"));
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

    private ArrayList<Category> generateCategorisedList() {
        ArrayList<Category> categorisedDocs = new ArrayList<Category>();

        Category category1 = new Category(1);
        category1.addDocument(new Document("Ponsonby Dog Walking Inc"));
        category1.addDocument(new Document("Newmarket Dog Walking Inc"));
        category1.addDocument(new Document("Parnell Dog Walking Inc"));
        categorisedDocs.add(category1);

        Category category2 = new Category(2);
        category2.addDocument(new Document("Mount Eden Cat Walking Inc"));
        category2.addDocument(new Document("Sandringham Cat Walking Inc"));
        category2.addDocument(new Document("Grafton Cat Walking Inc"));
        categorisedDocs.add(category2);

        Category category3 = new Category(3);
        category3.addDocument(new Document("Eden Terrace Goat Walking Inc"));
        category3.addDocument(new Document("Albany Goat Walking Inc"));
        category3.addDocument(new Document("Kingsland Goat Walking Inc"));
        category3.addDocument(new Document("Epsom Goat Walking Inc"));
        categorisedDocs.add(category3);

        return categorisedDocs;
    }

}

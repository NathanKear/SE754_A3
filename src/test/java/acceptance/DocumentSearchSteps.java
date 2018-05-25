package acceptance;

import Result.DocumentHandler;
import Result.Category;
import Result.Document;
import Search.NaturalLanguageProcessor;
import Search.SearchQuery;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import java.util.ArrayList;

import Search.SearchService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

public class DocumentSearchSteps {

    SearchService _searchService;
    ArrayList<Document> _returnedDocs;
    NaturalLanguageProcessor _langProcessor;
    DocumentHandler _docHandler;

    @Given("The search API is connected")
    public void connectSearchAPI() {
        _docHandler = new DocumentHandler();

        _searchService = mock(SearchService.class);
        when(_searchService.search(any(SearchQuery.class))).thenReturn(generateDocumentList()).thenReturn(new ArrayList<Document>());


        _langProcessor = mock(NaturalLanguageProcessor.class);
        when(_langProcessor.findCategoryLabel(any(Category.class))).thenReturn("Dog");
        when(_langProcessor.findCategorySummary(any(Category.class))).thenReturn("Dog Walking in Auckland");
    }

    @When("I input the Search Query with keywords \"$keywords\"")
    public void inputSearchQuery(String keywords) {
        String[] query = keywords.split("\\s+");
        SearchQuery sq = new SearchQuery(query);
        _returnedDocs = _searchService.search(sq);
    }

    @Then("The set of documents returned totals $docCount, is split into $categoryCount categories, with the first category labelled $label and summarised as $summary")
    public void documentsReturned(int docCount, int categoryCount, String label, String summary) {
        Assert.assertEquals(_returnedDocs.size(), docCount);

        ArrayList<Category> categorisedDocs = _docHandler.categorise(_returnedDocs);
        Assert.assertEquals(categorisedDocs.size(), categoryCount);

        for(Category c : categorisedDocs){
            c.setLabel(_langProcessor.findCategoryLabel(c));
            c.setSummary(_langProcessor.findCategorySummary(c));
        }

        Assert.assertEquals(categorisedDocs.get(0).label(), label);

        Assert.assertEquals(categorisedDocs.get(0).summary(), summary);

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

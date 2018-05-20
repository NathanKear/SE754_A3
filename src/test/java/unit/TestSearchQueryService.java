package unit;

import static org.mockito.Mockito.*;
import org.junit.*;

public class TestSearchQueryService {

    /*
    SearchQuery
    + keywords : Map<String, Double>
    + addKeyword(keyword : String, weight : Double) : void
    + removeKeyword(keyword : String) : boolean
    + adjustKeywordWeighting(keyword : String, double : adjustment)
    + getKeywordWeighting(keyword : String) : double
    + getKeywords() : List<String>
     */

    /*
    SearchQueryService
    + createSearchQuery(phrase : String) : SearchQuery
      */

    private final String shortPhrase = "The quick brown fox";
    private final String longPhrase = "The quick brown fox jumps over the lazy dog";

    private SearchQueryService _searchQueryService;

    @Before
    public void Setup() {
        NaturalLanguageProcessor naturalLanguageProcessor = mock(NaturalLanguageProcessor.class);
        _searchQueryService = new SearchQueryService(naturalLanguageProcessor);

        when(naturalLanguageProcessor.findKeywords(anyString())).thenReturn(new String[] {});
        when(naturalLanguageProcessor.findKeywords(shortPhrase)).thenReturn(new String[] { "fox" });
        when(naturalLanguageProcessor.findKeywords(longPhrase)).thenReturn(new String[] { "fox", "jumps", "dog" });
    }

    @Test
    public void TestKeyWordExtractionWhenEmptyPhrase() {
        SearchQuery searchQuery = _searchQueryService.createSearchQuery("");

        Assert.assertTrue("", searchQuery.getKeywords().isEmpty());
    }

    @Test
    public void TestKeyWordExtractionOnShortPhrase() {
        SearchQuery searchQuery = _searchQueryService.createSearchQuery(shortPhrase);

        Assert.assertFalse("", searchQuery.getKeywords().length() == 1);
        Assert.assertTrue("", searchQuery.getKeywords().contains("fox"));
    }

    @Test
    public void TestKeyWordExtractionOnLongPhrase() {
        SearchQuery searchQuery = _searchQueryService.createSearchQuery(longPhrase);

        Assert.assertFalse("", searchQuery.getKeywords().length() == 3);
        Assert.assertTrue("", searchQuery.getKeywords().contains("fox"));
        Assert.assertTrue("", searchQuery.getKeywords().contains("jumps"));
        Assert.assertTrue("", searchQuery.getKeywords().contains("dog"));
    }
}

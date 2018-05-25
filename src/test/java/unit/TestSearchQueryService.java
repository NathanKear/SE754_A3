package unit;

import static org.mockito.Mockito.*;

import Search.NaturalLanguageProcessor;
import Search.SearchQuery;
import Search.SearchQueryService;
import org.junit.*;

public class TestSearchQueryService {

    private final String shortPhrase = "The quick brown fox";
    private final String mediumString = "The quick brown fox jumps";
    private final String longPhrase = "The quick brown fox jumps over the lazy dog";

    private SearchQueryService _searchQueryService;

    @Before
    public void Setup() {
        NaturalLanguageProcessor naturalLanguageProcessor = mock(NaturalLanguageProcessor.class);
        _searchQueryService = new SearchQueryService(naturalLanguageProcessor);

        when(naturalLanguageProcessor.findKeywords(anyString())).thenReturn(new String[] {});
        when(naturalLanguageProcessor.findKeywords(shortPhrase)).thenReturn(new String[] { "fox" });
        when(naturalLanguageProcessor.findKeywords(mediumString)).thenReturn(new String[] { "fox", "jumps" });
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

        Assert.assertTrue("", searchQuery.getKeywords().size() == 1);
        Assert.assertTrue("", searchQuery.getKeywords().contains("fox"));
    }

    @Test
    public void TestKeyWordExtractionOnLongPhrase() {
        SearchQuery searchQuery = _searchQueryService.createSearchQuery(longPhrase);

        Assert.assertTrue("", searchQuery.getKeywords().size() == 3);
        Assert.assertTrue("", searchQuery.getKeywords().contains("fox"));
        Assert.assertTrue("", searchQuery.getKeywords().contains("jumps"));
        Assert.assertTrue("", searchQuery.getKeywords().contains("dog"));
    }
}

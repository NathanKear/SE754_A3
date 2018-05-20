package Search;

public class SearchQueryService {

    private final NaturalLanguageProcessor _naturalLanguageProcessor;

    public SearchQueryService(NaturalLanguageProcessor naturalLanguageProcessor) {
        this._naturalLanguageProcessor = naturalLanguageProcessor;
    }

    public SearchQuery createSearchQuery(String phrase) {
        return new SearchQuery(_naturalLanguageProcessor.findKeywords(phrase));
    }
}

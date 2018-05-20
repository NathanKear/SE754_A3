package Search;

import java.util.ArrayList;
import java.util.List;

public class Category {

    String _name;
    List<Document> _documents;
    double _popularityWeighting;
    Relevance _relevance;

    public Category (String name) {
        _name = name;
        _documents = new ArrayList<Document>();
    }

    public void setRelevance (Relevance relevance) {
        _relevance = relevance;
    }

    public Relevance getRelevanceType () {
        return _relevance;
    }

    public double getWeightedRelevance () {
        return (double) _relevance.ordinal()/Relevance.NUMBER_OF_RELEVANCE_TYPES;
    }

    public void addDocument (Document d) {
        _documents.add(d);
    }

    public void calculatePopularityWeighting (int totalNumberOfDocumentsFromSearch) {
        _popularityWeighting = (double) _documents.size()/totalNumberOfDocumentsFromSearch;
    }

    public double getPopularityWeighting () {
        return _popularityWeighting;
    }
}

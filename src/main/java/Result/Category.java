package Result;

import java.util.ArrayList;
import java.util.List;

public class Category {

    int _id;
    List<Document> _documents;
    double _popularityWeighting;
    Relevance _relevance;
    String _summary;
    String _label;

    public Category (int id) {
        _id = id;
        _documents = new ArrayList<Document>();
    }

    public String summary(){ return _summary; }

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

    public void setSummary(String summary){ _summary = summary; }

    public void setLabel(String label) { _label = label; }

    public String label() { return _label; }
}

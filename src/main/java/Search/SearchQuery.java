package Search;

import java.util.*;
import java.util.Map.Entry;

public class SearchQuery {

    /*
    SearchQuery
    + keywords : Map<String, Double>
    + addKeyword(keyword : String, weight : Double) : void
    + removeKeyword(keyword : String) : boolean
    + adjustKeywordWeighting(keyword : String, double : adjustment)
    + getKeywordWeighting(keyword : String) : double
    + getKeywords() : List<String>
     */

    private final Map<String, Double> _keywords = new HashMap<String, Double>();

    public SearchQuery(String[] keywords) {

    }

    public boolean addKeyword(String keyword) {
        if (_keywords.containsKey(keyword)) {
            return false;
        }

        _keywords.put(keyword, 1.0);

        return true;
    }

    public boolean removeKeyword(String keyword) {
        if (_keywords.containsKey(keyword)) {
            _keywords.remove(keyword);

            return true;
        }

        return false;
    }

    public void adjustKeywordWeighting(String keyword, double adjustment) {

        double newWeight = _keywords.get(keyword) + adjustment;

        if (newWeight > 0.0) {
            _keywords.put(keyword, newWeight);
        } else {
            _keywords.remove(keyword);
        }
    }

    public double getKeywordWeighting(String keyword) {
        return _keywords.get(keyword);
    }

    public String[] getKeywords() {
        List<Entry<String, Double>> list = new ArrayList<Entry<String, Double>>(_keywords.entrySet());
        java.util.Collections.sort(list, new KeywordComparator());

        String[] keywords = new String[list.size()];

        for (int i = 0; i < keywords.length; i++) {
            keywords[i] = list.get(i).getKey();
        }

        return keywords;
    }

    class KeywordComparator implements Comparator<Entry<String, Double>> {
        public int compare(Entry<String, Double> a, Entry<String, Double> b) {
            if (a.getValue() >= b.getValue()) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}

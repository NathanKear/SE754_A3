package Search;

import Result.Document;

import java.util.ArrayList;

public interface SearchService {
    ArrayList<Document> search(SearchQuery query);
}

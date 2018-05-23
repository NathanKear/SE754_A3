package Result;

import java.util.ArrayList;
import java.util.HashMap;

public class DocumentHandler {

    public ArrayList<Category> categorise(ArrayList<Document> resultDocs) {

        HashMap<String, Category> docsByTopic = new HashMap<String, Category>();
        int count = 0;

        for (Document d : resultDocs){
            String topic = findTopic(d.name());

            if (docsByTopic.containsKey(topic)){
                docsByTopic.get(topic).addDocument(d);
            }else {
                Category c = new Category(count);
                c.addDocument(d);
                docsByTopic.put(topic, c);
            }
        }

        ArrayList<Category> categorisedDocs = new ArrayList<Category>(docsByTopic.values());
        return categorisedDocs;
    }

    private String findTopic(String title){
        String[] potentialTopics = title.split("\\s+");

        return potentialTopics[0];
    }
}

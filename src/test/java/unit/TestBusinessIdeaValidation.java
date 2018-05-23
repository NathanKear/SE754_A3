package unit;

import static org.junit.Assert.*;

import Result.Document;
import Result.DocumentHandler;
import Result.Category;
import Result.UnassignedRelevanceException;
import Result.Relevance;
import BusinessValidation.IdeaValidator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestBusinessIdeaValidation {

    @Test
    public void testWeightingOfSearchCategories() {
        Category c = new Category(1);

        for (int i = 0; i < 25; i++) {
            c.addDocument(new Document(i + ""));
        }

        c.calculatePopularityWeighting(100);
        assertEquals(0.25, c.getPopularityWeighting(), 0);
    }

    @Test
    public void testCalculatingWeightingWhenLessDocumentsThanInCategory() {
        Category c = new Category(1);

        for (int i = 0; i < 40; i++) {
            c.addDocument(new Document(i + ""));
        }

        c.calculatePopularityWeighting(35);
        assertEquals(1, c.getPopularityWeighting(), 0);
    }

    @Test
    public void testWeightingOfCategoriesFromSearchResults () {
        DocumentHandler documentHandler = new DocumentHandler();
        ArrayList<Document> docs = new ArrayList<Document>();

        docs.add(new Document("Restaurant"));
        docs.add(new Document("Restaurant"));
        docs.add(new Document("Restaurant"));

        docs.add(new Document("Cafe"));
        docs.add(new Document("Cafe"));

        List<Category> categorisedDocuments = documentHandler.categorise(docs);

        Category restaurant = categorisedDocuments.get(0);
        Category cafe = categorisedDocuments.get(1);

        assertEquals(0.6, restaurant.getPopularityWeighting(), 0);
        assertEquals(0.4, cafe.getPopularityWeighting(), 0);
    }

    @Test
    public void testCalculatingWeightedRelevancyWhenNull() {
        Category c = new Category(1);

        try {
            assertEquals(c.getWeightedRelevance(), 0, 0);
            fail();
        } catch (UnassignedRelevanceException ex) {
            assertEquals("Relevance for Category 1 has not been set", ex.getMessage());
        }
    }

    @Test
    public void testAssigningNotRelevantToCategory() {
        Category c = new Category(1);

        c.setRelevance(Relevance.NOT_RELEVANT);

        assertEquals(c.getRelevanceType(), Relevance.NOT_RELEVANT);

        try {
            assertEquals(c.getWeightedRelevance(), 0, 0);
        } catch (UnassignedRelevanceException ex) {
            fail();
        }
    }

    @Test
    public void testAssigningWeakRelevanceToCategory() {
        Category c = new Category(1);

        c.setRelevance(Relevance.WEAK_RELEVANT);

        assertEquals(c.getRelevanceType(), Relevance.WEAK_RELEVANT);

        try {
            assertEquals(c.getWeightedRelevance(), 0.25, 0);
        } catch (UnassignedRelevanceException ex) {
            fail();
        }
    }

    @Test
    public void testAssigningNormalRelevanceToCategory() {
        Category c = new Category(1);

        c.setRelevance(Relevance.RELEVANT);

        assertEquals(c.getRelevanceType(), Relevance.RELEVANT);

        try {
            assertEquals(c.getWeightedRelevance(), 0.5, 0);
        } catch (UnassignedRelevanceException ex) {
            fail();
        }
    }

    @Test
    public void testAssigningVeryRelevantToCategory() {
        Category c = new Category(1);

        c.setRelevance(Relevance.VERY_RELEVANT);

        assertEquals(c.getRelevanceType(), Relevance.VERY_RELEVANT);

        try {
            assertEquals(c.getWeightedRelevance(), 0.75, 0);
        } catch (UnassignedRelevanceException ex) {
            fail();
        }
    }

    @Test
    public void testAssigningTheSameRelevaneToCategory() {
        Category c = new Category(1);

        c.setRelevance(Relevance.THE_SAME);

        assertEquals(c.getRelevanceType(), Relevance.THE_SAME);

        try {
            assertEquals(c.getWeightedRelevance(), 1, 0);
        } catch (UnassignedRelevanceException ex) {
            fail();
        }
    }

    @Test
    public void testAssigningRelevanceOfSearchCategoriesMultipleTimes() {
        Category c = new Category(1);

        c.setRelevance(Relevance.NOT_RELEVANT);
        assertEquals(c.getRelevanceType(), Relevance.NOT_RELEVANT);

        try {
            assertEquals(c.getWeightedRelevance(), 0, 0);
        } catch (UnassignedRelevanceException ex) {
            fail();
        }

        c.setRelevance(Relevance.WEAK_RELEVANT);
        assertEquals(c.getRelevanceType(), Relevance.WEAK_RELEVANT);

        try {
            assertEquals(c.getWeightedRelevance(), 0.25, 0);
        } catch (UnassignedRelevanceException ex) {
            fail();
        }

        c.setRelevance(Relevance.RELEVANT);
        assertEquals(c.getRelevanceType(), Relevance.RELEVANT);

        try {
            assertEquals(c.getWeightedRelevance(), 0.5, 0);
        } catch (UnassignedRelevanceException ex) {
            fail();
        }

        c.setRelevance(Relevance.VERY_RELEVANT);
        assertEquals(c.getRelevanceType(), Relevance.VERY_RELEVANT);

        try {
            assertEquals(c.getWeightedRelevance(), 0.75, 0);
        } catch (UnassignedRelevanceException ex) {
            fail();
        }

        c.setRelevance(Relevance.THE_SAME);
        assertEquals(c.getRelevanceType(), Relevance.THE_SAME);

        try {
            assertEquals(c.getWeightedRelevance(), 1, 0);
        } catch (UnassignedRelevanceException ex) {
            fail();
        }
    }

    @Test
    public void testCalculatingBusinessMaturityScore() {
        List<Category> categories = new ArrayList<Category>();

        Category c = new Category(1);
        c.setRelevance(Relevance.NOT_RELEVANT);
        addTestDocuments(c, 2);
        c.calculatePopularityWeighting(10);
        categories.add(c);

        c = new Category(2);
        c.setRelevance(Relevance.WEAK_RELEVANT);
        addTestDocuments(c, 4);
        c.calculatePopularityWeighting(10);
        categories.add(c);

        c = new Category(3);
        c.setRelevance(Relevance.RELEVANT);
        addTestDocuments(c, 3);
        c.calculatePopularityWeighting(10);
        categories.add(c);

        c = new Category(4);
        c.setRelevance(Relevance.VERY_RELEVANT);
        addTestDocuments(c, 1);
        c.calculatePopularityWeighting(10);
        categories.add(c);

        IdeaValidator ideaValidator = new IdeaValidator();
        double ideaMaturity = ideaValidator.calculateIdeaMaturity(categories);

        assertEquals(0.325, ideaMaturity, 0);
    }

    @Test
    public void testCalculatingBusinessMaturityScoreWhenUserHasNotSetRelevance () {
        List<Category> categories = new ArrayList<Category>();

        Category c = new Category(1);
        addTestDocuments(c, 2);
        c.calculatePopularityWeighting(10);

        categories.add(c);

        IdeaValidator ideaValidator = new IdeaValidator();
        double ideaMaturity = ideaValidator.calculateIdeaMaturity(categories);

        assertEquals(0, ideaMaturity, 0);
    }

    public void addTestDocuments(Category c, int numOfDocumentsToAdd) {
        for (int i = 0; i < numOfDocumentsToAdd; i++) {
            c.addDocument(new Document(i + ""));
        }
    }
}

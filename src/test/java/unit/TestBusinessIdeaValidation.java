package unit;

import static org.junit.Assert.*;

import Result.Document;
import Result.Relevance;
import Result.Category;
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
    public void testAssigningNotRelevantToCategory() {
        Category c = new Category(1);

        c.setRelevance(Relevance.NOT_RELEVANT);

        assertEquals(c.getRelevanceType(), Relevance.NOT_RELEVANT);
        assertEquals(c.getWeightedRelevance(), 0, 0);
    }

    @Test
    public void testAssigningWeakRelevanceToCategory() {
        Category c = new Category(1);

        c.setRelevance(Relevance.WEAK_RELEVANT);

        assertEquals(c.getRelevanceType(), Relevance.WEAK_RELEVANT);
        assertEquals(c.getWeightedRelevance(), 0.25, 0);
    }

    @Test
    public void testAssigningNormalRelevanceToCategory() {
        Category c = new Category(1);

        c.setRelevance(Relevance.RELEVANT);

        assertEquals(c.getRelevanceType(), Relevance.RELEVANT);
        assertEquals(c.getWeightedRelevance(), 0.5, 0);
    }

    @Test
    public void testAssigningVeryRelevantToCategory() {
        Category c = new Category(1);

        c.setRelevance(Relevance.VERY_RELEVANT);

        assertEquals(c.getRelevanceType(), Relevance.VERY_RELEVANT);
        assertEquals(c.getWeightedRelevance(), 0.75, 0);
    }

    @Test
    public void testAssigningTheSameRelevaneToCategory() {
        Category c = new Category(1);

        c.setRelevance(Relevance.THE_SAME);

        assertEquals(c.getRelevanceType(), Relevance.THE_SAME);
        assertEquals(c.getWeightedRelevance(), 1, 0);
    }

    @Test
    public void testAssigningRelevanceOfSearchCategoriesMultipleTimes() {
        Category c = new Category(1);

        c.setRelevance(Relevance.NOT_RELEVANT);
        assertEquals(c.getRelevanceType(), Relevance.NOT_RELEVANT);
        assertEquals(c.getWeightedRelevance(), 0, 0);

        c.setRelevance(Relevance.WEAK_RELEVANT);
        assertEquals(c.getRelevanceType(), Relevance.WEAK_RELEVANT);
        assertEquals(c.getWeightedRelevance(), 0.25, 0);

        c.setRelevance(Relevance.RELEVANT);
        assertEquals(c.getRelevanceType(), Relevance.RELEVANT);
        assertEquals(c.getWeightedRelevance(), 0.5, 0);

        c.setRelevance(Relevance.VERY_RELEVANT);
        assertEquals(c.getRelevanceType(), Relevance.VERY_RELEVANT);
        assertEquals(c.getWeightedRelevance(), 0.75, 0);

        c.setRelevance(Relevance.THE_SAME);
        assertEquals(c.getRelevanceType(), Relevance.THE_SAME);
        assertEquals(c.getWeightedRelevance(), 1, 0);
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

    }

    public void addTestDocuments(Category c, int numOfDocumentsToAdd) {
        for (int i = 0; i < numOfDocumentsToAdd; i++) {
            c.addDocument(new Document(i + ""));
        }
    }
}

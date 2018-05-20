package unit;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import Search.Document;
import Search.Relevance;
import Search.Category;
import BusinessValidation.IdeaValidator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestBusinessIdeaValidation {

    @Test
    public void testWeightingOfSearchCategories() {
        Category c = new Category("Test Category");

        for (int i = 0; i < 25; i++) {
            c.addDocument(new Document());
        }

        c.calculatePopularityWeighting(100);
        assertEquals(0.25, c.getPopularityWeighting(), 0);
    }

    @Test
    public void testAssigningRelevanceOfSearchCategories() {
        Category c = new Category("Test Category");

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

        Category c = mock(Category.class);
        when(c.getWeightedRelevance()).thenReturn(0.0);
        when(c.getPopularityWeighting()).thenReturn(0.2);
        categories.add(c);

        c = mock(Category.class);
        when(c.getWeightedRelevance()).thenReturn(0.25);
        when(c.getPopularityWeighting()).thenReturn(0.4);
        categories.add(c);

        c = mock(Category.class);
        when(c.getWeightedRelevance()).thenReturn(0.5);
        when(c.getPopularityWeighting()).thenReturn(0.3);
        categories.add(c);

        c = mock(Category.class);
        when(c.getWeightedRelevance()).thenReturn(0.75);
        when(c.getPopularityWeighting()).thenReturn(0.1);
        categories.add(c);

        IdeaValidator ideaValidator = new IdeaValidator();
        double ideaMaturity = ideaValidator.calculateIdeaMaturity(categories);

        assertEquals(ideaMaturity, 0.325, 0);
    }
}

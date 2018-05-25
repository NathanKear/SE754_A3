
import Account.*;
import BusinessValidation.IdeaValidator;
import Result.Category;
import Result.Document;
import Result.DocumentHandler;
import Result.Relevance;
import Search.NaturalLanguageProcessor;
import Search.SearchQuery;
import Search.SearchQueryService;
import Search.SearchService;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Test.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Program {

    enum State {
        SignUp,
        EnterSearch,
        AlterSearch,
        Search,
        Relevance,
        IdeaMaturity
    }

    private static State _currentState;
    private static BufferedReader _br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {

        System.out.println("Welcome to the Business Idea Validator");
        boolean exit = false;
        _currentState = State.SignUp;
        User user = null;
        SearchQuery searchQuery = null;
        List<Category> categories = null;

        while (!exit) {
            switch (_currentState) {
                case SignUp:
                    signUp();
                    break;
                case EnterSearch:
                    searchQuery = enterSearch();
                    break;
                case AlterSearch:
                    searchQuery = alterSearch(searchQuery);
                    break;
                case Search:
                    categories = search(searchQuery);
                    break;
                case Relevance:
                    editRelevance(categories);
                    break;
                case IdeaMaturity:
                    exit = calculateIdeaMaturity(categories);
            }
        }
    }

    private static void signUp() {
        UserDatabase userDb = mock(UserDatabase.class);
        SearchDatabase searchDb = mock(SearchDatabase.class);

        when(userDb.get(anyString(), anyString())).thenReturn(null);

        when(userDb.add("user", "password", new User("user", UserType.BASIC))).thenReturn(true);
        when(userDb.get("user", "password")).thenReturn(new User("user", UserType.BASIC));

        AuthenticationService auth = new AuthenticationService(userDb);
        UserManager userManager = new UserManager(userDb, searchDb);

        boolean signedUp = false;

        while (!signedUp) {

            clearScreen();
            printTitle("Sign Up");
            printSpace();
            System.out.println("(Note: Use 'user' as username and 'password' as the password)");
            printSpace();
            String username = readInput("Enter a username: ");
            String password = readInput("Enter a password: ");

            User newUser = auth.signUp(username, password, UserType.BASIC);

            if (newUser == null) {
                _currentState = State.SignUp;
                signedUp = false;
            } else {
                _currentState = State.EnterSearch;
                signedUp = true;
            }
        }
    }

    private static SearchQuery enterSearch() {
        NaturalLanguageProcessor naturalLanguageProcessor = mock(NaturalLanguageProcessor.class);
        SearchQueryService searchQueryService = new SearchQueryService(naturalLanguageProcessor);
        SearchQuery searchQuery = null;

        String longPhrase = "Pet walking Auckland";

        when(naturalLanguageProcessor.findKeywords(anyString())).thenReturn(new String[] {});
        when(naturalLanguageProcessor.findKeywords(longPhrase)).thenReturn(new String[] { "pet", "walking", "Auckland" });

        boolean searchSuccess = false;

        while (!searchSuccess) {

            clearScreen();
            printTitle("Search");
            printSpace();
            System.out.println("(Note: Enter 'Pet walking Auckland' as your business idea)");
            printSpace();
            String phrase = readInput("Enter your business idea: ");

            if (phrase.equals(longPhrase)) {
                _currentState = State.AlterSearch;
                searchQuery = searchQueryService.createSearchQuery(phrase);
                searchSuccess = true;
            } else {
                _currentState = State.EnterSearch;
                searchSuccess = false;
            }
        }

        return searchQuery;
    }

    private static SearchQuery alterSearch(SearchQuery searchQuery) {

        boolean finishedAlteringSearch = false;

        while (!finishedAlteringSearch) {

            clearScreen();
            printTitle("Refine Search");
            printSpace();
            System.out.println("Enter:");
            System.out.println("    [A] - Add keyword");
            System.out.println("    [R] - Remove keyword");
            System.out.println("    [P] - Change keyword priority");
            System.out.println("    [X] - Finished refining search");
            printSpace();
            System.out.println("Current search:");
            printSpace();

            System.out.println("| Keyword     | Priority      |");
            System.out.println("+-------------+---------------+");
            for (String keyword : searchQuery.getKeywords()) {
                System.out.println(String.format("| %11s | %13s |", keyword, searchQuery.getKeywordWeighting(keyword)));
            }
            printSpace();
            String command = readInput("Enter a command: ");
            if (command.toUpperCase().equals("A")) {
                String keyword = readInput("Enter a keyword to add: ");
                searchQuery.addKeyword(keyword);
            } else if (command.toUpperCase().equals("R")) {
                String keyword = readInput("Enter a keyword to remove: ");
                searchQuery.removeKeyword(keyword);
            } else if (command.toUpperCase().equals("P")) {
                String keyword = readInput("Enter a keyword to change priority: ");
                double weightingAdjustment = 0;
                weightingAdjustment = Double.parseDouble(readInput("Enter change in priority [+ve or -ve decimal]: "));
                searchQuery.adjustKeywordWeighting(keyword, weightingAdjustment);
            } else if (command.toUpperCase().equals("X")) {
                finishedAlteringSearch = true;
                _currentState = State.Search;
            } else {
                finishedAlteringSearch = false;
                _currentState = State.AlterSearch;
            }
        }

        return searchQuery;
    }

    private static List<Category> search(SearchQuery searchQuery) {

        clearScreen();
        printTitle("Search Results");
        printSpace();
        System.out.println("Search results, grouped by category:");

        DocumentHandler _docHandler = new DocumentHandler();

        SearchService _searchService = mock(SearchService.class);
        when(_searchService.search(any(SearchQuery.class))).thenReturn(generateDocumentList());

        NaturalLanguageProcessor _langProcessor = mock(NaturalLanguageProcessor.class);
        when(_langProcessor.findCategoryLabel(any(Category.class))).thenReturn("Cat", "Goat", "Dog");
        when(_langProcessor.findCategorySummary(any(Category.class))).thenReturn("Cat Walking in Auckland", "Goat Walking in Auckland", "Dog Walking in Auckland");

        ArrayList<Document> resultDocs = _searchService.search(searchQuery);
        List<Category> categorisedDocs = _docHandler.categorise(resultDocs);

        System.out.println();
        for (Category category : categorisedDocs) {

            category.setSummary(_langProcessor.findCategorySummary(category));
            category.setLabel(_langProcessor.findCategoryLabel(category));

            System.out.println(String.format(category.label()));
            System.out.println(String.format(category.summary()));

            for (Document doc : category.getDocuments()) {
                System.out.println("    - " + doc.name());
            }
        }

        _currentState = State.Relevance;
        return categorisedDocs;
    }

    private static void editRelevance (List<Category> categories) {
        boolean finshedEditingRelevance = false;

        while (!finshedEditingRelevance) {
            printTitle("Edit Relevance of categories");
            printSpace();
            System.out.println("Enter:");
            System.out.println("    [C] - Change Relevancy");
            System.out.println("    [X] - Finished changing relevance");
            printSpace();
            printCurrentCategoryInformation(categories);

            String command = readInput("Enter a command: ");

            if (command.toUpperCase().equals("C")) {
                String categoryToChange = readInput("Enter the name of the category to change: ");
                Category c = getCategoryFromName(categoryToChange, categories);

                String relevanceType = readInput("Enter the relevance of the category. Options: NOT_RELEVANT, WEAK_RELEVANT, RELEVANT, VERY_RELEVANT, THE_SAME: ");
                Relevance relevance = Relevance.valueOf(relevanceType.toUpperCase());

                if (relevance == null || c == null) {
                    System.out.println("Invalid relevance or category option. Please choose a valid option.");
                } else {
                    changeCategoryRelevance(c, relevance);
                }
            } else if (command.toUpperCase().equals("X")) {
                finshedEditingRelevance = true;
                _currentState = State.IdeaMaturity;
            } else {
                System.out.println("Invalid command. Please choose a valid option.");
            }
        }

    }

    public static Category getCategoryFromName(String name, List<Category> categories) {
        for (Category c : categories) {
            if (c.label().toUpperCase().equals(name.toUpperCase())) {
                return c;
            }
        }

        return null;
    }

    public static void changeCategoryRelevance(Category c, Relevance relevance) {
        c.setRelevance(relevance);
    }

    public static void printCurrentCategoryInformation (List<Category> categories) {
        for (Category c : categories) {
            Relevance relevance = c.getRelevanceType();
            System.out.println("Name: " + c.label());
            System.out.println("Summary: " + c.summary());
            System.out.println("Relevance: " + (relevance == null ? "Not set" : relevance.toString()));
            printSpace();
        }
    }

    public static boolean calculateIdeaMaturity (List<Category> categories) {
        printTitle("Business Idea Maturity");
        IdeaValidator ideaValidator = new IdeaValidator();
        double ideaMaturity = ideaValidator.calculateIdeaMaturity(categories);
        System.out.println("Business idea maturity score ranges from 0 - 1");
        System.out.println("Your idea has a business maturity score of: " + ideaMaturity);

        String input = readInput("Press enter to exit or any other key to search again");
        boolean shouldProgramExit = input.length() == 0;

        if (!shouldProgramExit) {
            _currentState = State.EnterSearch;
        }

        return shouldProgramExit;
    }

    private static ArrayList<Document> generateDocumentList() {
        ArrayList<Document> docs = new ArrayList<Document>();
        docs.add(new Document("Dog Walking Ponsonby Inc"));
        docs.add(new Document("Dog Walking Newmarket Inc"));
        docs.add(new Document("Dog Walking Parnell Inc"));
        docs.add(new Document("Cat Walking Mount Eden Inc"));
        docs.add(new Document("Cat Walking Sandringham Inc"));
        docs.add(new Document("Cat Walking Grafton Inc"));
        docs.add(new Document("Goat Walking Eden Terrace Inc"));
        docs.add(new Document("Goat Walking Albany Inc"));
        docs.add(new Document("Goat Walking Kingsland Inc"));
        docs.add(new Document("Goat Walking Epsom Inc"));
        return docs;
    }

    private static String readInput(String promptText) {
        System.out.print(promptText);

        try {
            String s = _br.readLine();
            return s;
        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
        }

        return null;
    }

    private static void printTitle(String title) {
        System.out.println(String.format("+====================================================================+"));
        System.out.println("                             " + title);
        System.out.println(String.format("+====================================================================+"));
        System.out.println();
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void printSpace() {
        printSpace(1);
    }

    private static void printSpace(int lines) {
        for (int i = 0; i < lines; i++) {
            System.out.println();
        }
    }
}

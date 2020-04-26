import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {

    private static String start_state;
    private static String goal_state;
    private static String[] forbidden_state;

    @BeforeAll
    static void setupState(){

        // Seting up the state for testing (no forbidden test for now)
        start_state = "320";
        goal_state = "110";
        forbidden_state = new String[]{};
    }


    @Test
    public void canCreateSolverWithRightParam() {
        Exception ex = null;
        try {
            new Solver(start_state, goal_state, forbidden_state);
        } catch (Exception e) {
            ex = e;
        }
        assertEquals(null,ex);
    }

    @Test
    public void breadthFirstSearchIsCorrect(){
        String search_type = "BFS";
        // Excepted Output from the search
        String solution_result = "320,220,210,110";
        String expanded_result = "320,220,420,310,330,321,210,230,221,410,430,421,210,410,311,230,430,331,221,421,311,331,110";
        String real_result = solution_result + "\n" + expanded_result;

        Solver solver = new Solver(start_state, goal_state, forbidden_state);
        String estimated_result = solver.solve(search_type);

        assertResultIsCorrect(search_type, estimated_result, real_result);

    }

    @Test
    public void depthFirstSearchIsCorrect(){
        String search_type = "DFS";
        // Excepted Output from the search
        String solution_result = "320,220,210,110";
        String expanded_result = "320,220,210,110";
        String real_result = solution_result + "\n" + expanded_result;

        Solver solver = new Solver(start_state, goal_state, forbidden_state);
        String estimated_result = solver.solve(search_type);

        assertResultIsCorrect(search_type, estimated_result, real_result);
    }

    @Test
    public void iterativeDeepeningSearchIsCorrect(){
        String search_type = "IDS";
        // Excepted Output from the search
        String solution_result = "320,220,210,110";
        String expanded_result = "320,320,220,420,310,330,321,320,220,210,230,221,420,410,430,421,310,210,410,311,330,230,430,331,321,221,421,311,331,320,220,210,110";
        String real_result = solution_result + "\n" + expanded_result;

        Solver solver = new Solver(start_state, goal_state, forbidden_state);
        String estimated_result = solver.solve(search_type);

        assertResultIsCorrect(search_type, estimated_result, real_result);
    }

    @Test
    public void manhattanDistanceHeuristicIsCorrect(){
        int[] digits1 = {0,0,0};
        int[] digits2 = {3,4,5};
        State state1 = new State(digits1);
        State state2 = new State(digits2);

        int expected_distance = 3 + 4 + 5;

        int estimated_distance = State.manhattan_distance(state1, state2);

        assertEquals(expected_distance, estimated_distance);
    }

    @Test
    public void greedySearchIsCorrect(){
        String search_type = "GDS";
        // Excepted Output from the search
        String solution_result = "320,310,210,211,111,110";
        String expanded_result = "320,310,210,211,111,110";
        String real_result = solution_result + "\n" + expanded_result;

        Solver solver = new Solver(start_state, goal_state, forbidden_state);
        String estimated_result = solver.solve(search_type);

        assertResultIsCorrect(search_type, estimated_result, real_result);
    }

    @Test
    public void hillClimbingSearchIsCorrect(){
        String search_type = "HCS";
        // Excepted Output from the search
        String solution_result = "No solution found.";
        String expanded_result = "320,310,210";
        String real_result = solution_result + "\n" + expanded_result;

        Solver solver = new Solver(start_state, goal_state, forbidden_state);
        String estimated_result = solver.solve(search_type);

        assertResultIsCorrect(search_type, estimated_result, real_result);
    }

    @Test
    public void aStartIsCorrect(){
        String search_type = "ASS";
        // Excepted Output from the search
        String solution_result = "320,220,210,110";
        String expanded_result = "320,310,210,220,210,110";
        String real_result = solution_result + "\n" + expanded_result;

        Solver solver = new Solver(start_state, goal_state, forbidden_state);
        String estimated_result = solver.solve(search_type);

        assertResultIsCorrect(search_type, estimated_result, real_result);
    }


    /**
     * Assert Result Is Correct is a helper function to check if the estimated result is the same as the real result.
     * Both are strings. It will also output the two for comparison and testing purposes.
     * @param search_type : The type of search algorithm tested (DFS,BFS,IDS,GDS,HCS,ASS)
     * @param estimated_result : The result as found by the implemented version of the algorithm
     * @param real_result : The result that should be outputed
     */
    public void assertResultIsCorrect(String search_type, String estimated_result, String real_result){
        System.out.println("Search Type: " + search_type);
        System.out.println("Estimated Result: \n" + estimated_result);
        System.out.println("---");
        System.out.println("Real Result: \n" + real_result);
        assertEquals(real_result, estimated_result);
    }
}


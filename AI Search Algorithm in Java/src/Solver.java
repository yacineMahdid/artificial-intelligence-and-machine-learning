// ArrayList Imports
import java.util.ArrayList;
    public class Solver {

        // Search Variables
        int MAX_ITERATION = 1000;
        int current_iteration;

        public State end_state;

        public ArrayList<State> forbidden_state;
        public State search_tree;
        public ArrayList<State> fringe;

        // Output Variables
        public ArrayList<State> expanded_states;

        public Solver(String start_state, String goal_state, String[] forbidden_state) {
            this.forbidden_state = to_forbidden(forbidden_state);
            this.search_tree = new State(to_digits(start_state), null, -1, this.forbidden_state);
            this.end_state = new State(to_digits(goal_state),  null, -1, this.forbidden_state);

        }

        /**
         * Solve is the main function of the Solver class which will setup the data structures needed for the search
         * algorithm and then will launch the search for the goal state. It will then construct the right output string
         * which will represent the path found and the total state path taken
         * @param search_type : The type of search algorithm we want to run (BFS, DFS, IDS, GDS, HCS, ASS)
         * @return : Return a string representation of the path found and the expanded node to find that path
         */
        public String solve(String search_type) {

            // Init the solver data structure
            this.current_iteration = 0;
            this.fringe = new ArrayList<>();
            this.expanded_states = new ArrayList<>();
            this.search_tree.set_all_children();

            // Run one of the possible search algorithm to find the goal node
            State goal_state = null;
            switch (search_type) {
                case "BFS":
                    goal_state = BFS(this.search_tree);
                    break;
                case "DFS":
                    goal_state = DFS(this.search_tree, Integer.MAX_VALUE);
                    break;
                case "IDS":
                    goal_state = IDS();
                    break;
                case "GDS":
                    goal_state = GDS(this.search_tree);
                    break;
                case "HCS":
                    goal_state = HCS(this.search_tree);
                    break;
                case "ASS":
                    goal_state = ASS(this.search_tree);
                    break;
            }


            // Generate the right solution path from the goal state to the start state
            String solution_result = generate_solution_path(goal_state);
            // Print out all the nodes that were expanded during the search process
            String expanded_result = state_array_to_string(this.expanded_states);

            return solution_result + "\n" + expanded_result;

        }

        /**
         * Depth First Search (DFS) is a search algorithm that will follow the next state available (the next children)
         * @param current_state : This is the current state we are in
         * @param max_depth : This is a stoping condition (is used for Iterative Deepening Search)
         * @return : Will return a state if it found the goal state otherwise it will return null
         */
        private State DFS(State current_state, int max_depth) {

            // Increase the iteration counter and look add this state to the expanded states
            this.current_iteration++;
            this.expanded_states.add(current_state);

            // This means we have hit the goal state
            if (State.manhattan_distance(current_state, this.end_state) == 0) {
                return current_state;
            }
            // Here we check if we should exit the recursion if we hit a stoping condition
            else if (this.current_iteration == MAX_ITERATION || max_depth <= 0) {
                return null;
            }

            // Iterate through all the children of the current state and check if we can expand them
            for (int i = 0; i < current_state.children.size(); i++) {

                // Get the next children and set it up
                State next_node = current_state.children.get(i);
                next_node.set_all_children();

                // Check if we can expand this node
                if (next_node.is_state_expandable(this.expanded_states)) {
                    State goal_state = DFS(next_node, max_depth - 1);

                    // If we hit the max iteration we need to get out
                    if (this.current_iteration == MAX_ITERATION) {
                        return null;
                    }

                    // If this state is not null it means we are good to go
                    if (goal_state != null) {
                        return goal_state;
                    }
                }
            }

            // Here we depleted all the possible children so we need to get out of this state
            return null;
        }

        /**
         * Iterative Deepening Search (IDS) is a search method which will run multiple run of Depth First Search (DFS)
         * But with a restricted limit in the depth
         * @return : will return a state if it found the goal state otherwise it will return null.
         */
        private State IDS() {

            // Will keep track of the array
            int lvl = 0;
            State goal_state = null;
            ArrayList<State> full_expanded_states = new ArrayList<>();
            while (this.current_iteration < MAX_ITERATION) {

                // Reset some data structure we need
                this.search_tree.children = new ArrayList<>();
                this.expanded_states = new ArrayList<>();
                this.search_tree.set_all_children();

                // Do a depth first search with a limit at the level provided
                goal_state = DFS(this.search_tree, lvl);

                // Append the expanded state from this level to the full expanded states
                full_expanded_states.addAll(this.expanded_states);

                // If we found an answer we get out
                if (goal_state != null) {
                    break;
                }

                lvl++;
            }

            // Set the expanded states to the actual full expanded states
            this.expanded_states = full_expanded_states;
            return goal_state;
        }


        /**
         * Breadth First Search (BFS) is a search method which will look at all the children states of a given
         * state before moving to another state
         * @param current_state : This is the state we are starting on
         * @return : We return the goal state if we were able to attain it otherwise we return null
         */
        private State BFS(State current_state) {
            this.current_iteration++;
            this.expanded_states.add(current_state);

            // This means we have hit the goal state
            if (State.manhattan_distance(current_state, this.end_state) == 0) {
                return current_state;
            }
            // Here we check if we should exit the recursion if we hit a stoping condition
            else if (this.current_iteration >= MAX_ITERATION) {
                return null;
            }


            // We look at all the children of this current state and we add to the fringe the one that are expandable
            add_children_to_fringe(current_state);
            // Here we make sure the fringe is up to date and doesn't contain anything that is not expandable
            fix_fringe();


            // We pop the next state scheduled to be reviewed and we call BFS on it
            if (this.fringe.size() != 0) {
                State next_state = this.fringe.remove(0);
                return BFS(next_state);
            };

            return null;
        }


        /**
         * Greedy Search (GDS) is a search method which will look at the next best state available given the
         * heuristic we set up (here it's manhattan distance from one state to another)
         * @param current_state : This is the state we are starting the search on (don't forget it's recursive though)
         * @return : We return the goal state if we were able to attain it otherwise we simply return null
         */
        private State GDS(State current_state){
            this.current_iteration++;
            this.expanded_states.add(current_state);

            // This means we have hit the goal state
            if (State.manhattan_distance(current_state, end_state) == 0) {
                return current_state;
            }
            // Here it means we it the stopping condition
            else if (this.current_iteration >= MAX_ITERATION) {
                return null;
            }

            // We look at all the children of this current state and we add to the fringe the one that are expandable
            add_children_to_fringe(current_state);
            // Here we make sure the fringe has maintained its integrity
            fix_fringe();

            // Here we select the best node given the heuristic and we reiterate on it
            if (this.fringe.size() != 0) {
                int closest_index = select_best_state(this.fringe, false);
                State next_node = this.fringe.remove(closest_index);
                return GDS(next_node);
            }

            return null;
        }


        /**
         * Steepest Hill-Climbing Search is a search algorithm that will always try to take the next best state
         * given the current state and as defined by the heuristic. It might not find the optimum solution though.
         * @param current_state : this the current state we are in for the current iteration
         * @return : return the goal state if found or null
         */
        private State HCS(State current_state) {
            this.current_iteration++;
            this.expanded_states.add(current_state);

            // This means we have hit the goal state
            if (State.manhattan_distance(current_state, this.end_state) == 0) {
                return current_state;
            }
            // Here it means we it the stopping condition
            else if (this.current_iteration >= MAX_ITERATION || current_state.children.size() == 0) {
                return null;
            }

            // Get the current distance and the next best state available in the children
            int current_distance = State.manhattan_distance(current_state, this.end_state);
            int closest_index = select_best_state(current_state.children, false);
            State next_state = current_state.children.get(closest_index);

            // Check if the next state exist and if it is better than the current state recurse on it
            if (next_state != null) {
                int next_distance = State.manhattan_distance(next_state, this.end_state);
                if (next_distance < current_distance) {
                    next_state.set_all_children();
                    return HCS(next_state);
                }
            }

            return null;
        }


        /**
         * A* is a search algorithm that uses the power of the greedy search while keeping track of the bacward
         * cost as we move toward a solution in the state tree.
         * @param current_state  : this is the state for the current iteration
         * @return : will return either the goal node or null if not reached
         */
        private State ASS(State current_state) {
            // increment the iteration and add the state to those already expanded
            this.current_iteration++;
            this.expanded_states.add(current_state);

            // This means we have hit the goal state
            if (State.manhattan_distance(current_state, end_state) == 0) {
                return current_state;
            }
            // Here it means we it the stopping condition
            else if (this.current_iteration >= MAX_ITERATION) {
                return null;
            }

            // We look at all the children of this current state and we add to the fringe the one that are expandable
            add_children_to_fringe(current_state);
            // Here we make sure the fringe has maintained its integrity
            fix_fringe();

            // If nothing is left in the fringe we return null
            if (this.fringe.size() == 0){
                return null;
            }

            // We get the next best state and iterate
            int closest_index = select_best_state(this.fringe, true);
            State next_state = this.fringe.remove(closest_index);
            return ASS(next_state);
        }


        /**
         * Generate Solution Path is a helper function that will take a goal state and move backward through
         * the parent until it found the null state (which is the initial start_state)
         * @param goal_state : The goal state that was found by one of the search algorithm
         * @return : Return a string representation of the path that was taken from the start state to the goal state
         */
        private String generate_solution_path(State goal_state){

            // If the goal_state wasn't found we return an error string
            if (goal_state == null) {
                return "No solution found.";
            }

            // Creating the solution path by going backward from the goal state we add it to the solution
            // path in reverse order (always at the front)
            ArrayList<State> solution_path = new ArrayList<State>();
            State current_state = goal_state;
            while (current_state != null) {
                solution_path.add(0, current_state);
                current_state = current_state.parent;
            }

            return  state_array_to_string(solution_path);
        }

        /**
         * Fix Fringe is an helper function to periodically make sure that the node inside the fringe can still be
         * expanded. It can happen that a state was expandable when it was put on the fringe, but after a few iteration
         * It was already visited via another state.
         */
        private void fix_fringe(){
            ArrayList<State> new_fringe = new ArrayList<>();
            while (this.fringe.size() != 0) {
                State possible_state = this.fringe.remove(0);
                if (possible_state.is_state_expandable(this.expanded_states)){
                    new_fringe.add(possible_state);
                }
            }
            this.fringe = new_fringe;
        }

        /**
         * Add Children to Fringe is an helper function that will iterate over the current state children
         * and verify if they are expandable or not. If yes, then it add them to the global fringe.
         * @param current_state : This is the parent state where the children are located
         */
        private void add_children_to_fringe(State current_state){
            for (int i = 0; i < current_state.children.size(); i++) {
                State next_state = current_state.children.get(i);
                next_state.set_all_children();

                if (next_state.is_state_expandable(this.expanded_states)) {
                    this.fringe.add(next_state);
                }
            }
        }


        /**
         * Select Best State is a helper function that will select the states that has the best score as given by
         * the heuristic (here it is manhattan distance)
         * @param possible_states : This is the states we want to iterate over
         * @return : We return a index which correspond to it's location in the possible states arralist
         */
        public int select_best_state(ArrayList<State> possible_states, boolean is_backward_cost_relevant) {

            // Setup to capture the closest value and the closest index
            int closest_value = Integer.MAX_VALUE;
            int closest_index = -1;

            // Iterate over all the possible states
            for (int i = 0; i < possible_states.size(); i++) {
                // We get the next possible state and we calculate it's distance with the end state
                State possible_state = possible_states.get(i);

                // Adding the possible distance (if backward cost is relevant we add the backward cost)
                int possible_dist = State.manhattan_distance(possible_state, this.end_state);
                if (is_backward_cost_relevant) {
                    possible_dist = possible_dist + possible_state.backward_cost;
                }

                // Need to put an equal here to choose the last added node in the case of a tie
                if (possible_dist <= closest_value) {
                    closest_value = possible_dist;
                    closest_index = i;
                }
            }

            return closest_index;
        }


        /**
         * To Digits takes a three digits string and transform it into a 3 integer array
         * @param number : a three digit string
         * @return : a three integer array
         */
        private int[] to_digits(String number) {
            int[] digits = new int[3];

            for (int i = 0; i < digits.length; i++) {
                digits[i] = Character.getNumericValue(number.charAt(i));
            }
            return digits;
        }


        /**
         * To Forbidden take an array of string and create an array list of forbidden state
         * @param numbers : 3 digits numbers array
         * @return : An arraylist of state representing these digits
         */
        private ArrayList<State> to_forbidden(String[] numbers) {

            ArrayList<State> forbidden_digits = new ArrayList<State>();
            for (int i = 0; i < numbers.length; i++) {
                State new_state = new State(to_digits(numbers[i]));
                forbidden_digits.add(new_state);
            }

            return forbidden_digits;
        }

        /**
         * Digits to String will take an array of integer representing digit and will turn it into a string
         * @param digits : an integer array representing three digits
         * @return : a string representing the digits
         */
        private String digits_to_string(int[] digits) {
            String number = "";
            for (int i = 0; i < digits.length; i++) {
                number = number + digits[i];
            }
            return number;
        }


        /**
         * State Array to String: Will take an array of states and will construct a string which will have this form
         * XXX,YYY,ZZZ,etc
         * @param states : an array of state which contains digits
         * @return : a string representing the array of state
         */
        private String state_array_to_string(ArrayList<State> states) {
            String string_states = "";

            for (int i = 0; i < states.size() - 1; i++) {
                string_states = string_states + digits_to_string(states.get(i).digits) + ",";
            }

            return string_states + digits_to_string(states.get(states.size() - 1).digits);
        }
    }

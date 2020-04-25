import java.util.ArrayList;

public class State {

    // Main information on the State
    int digits[];
    int digit_changed_index;
    int backward_cost;
    State parent;
    ArrayList<State> children;

    // Extra information on the puzzle limitation
    ArrayList<State> forbidden_state;

    /**
     * State Constructor, this one is used to make a state detached from the tree
     * @param digits : The only information we need is the three digits array of integer
     */
    public State(int digits[]){
        this.digits = digits;
        this.digit_changed_index = -1;
        this.parent = null;
    }

    /**
     * State Constructor, This is used to make a State that is attached to the tree for most search.
     * @param digits : The three integer array representing the digits
     * @param parent : The parent of this State
     * @param digit_changed_index : The index that was changed from the parent to be in this state
     * @param forbidden_state : This is the limitation on the state that are not allowed
     */
    public State(int digits[], State parent, int digit_changed_index, ArrayList<State> forbidden_state) {
        this(digits);
        this.parent = parent;
        this.children = new ArrayList<>();
        this.digit_changed_index = digit_changed_index;
        this.forbidden_state = forbidden_state;
        this.backward_cost = 0; // we hardcode this one to 0
    }

    /**
     * State Constructor, this one is used for the A* search algorithm which require the backward cost
     * @param digits : The three integer array representing the digits
     * @param parent : The parent of this State
     * @param digit_changed_index : The index that was changed from the parent to be in this state
     * @param forbidden_state : This is the limitation on the state that are not allowed
     * @param backward_cost : Accumulated cost since the start state
     */
    public State(int digits[], State parent, int digit_changed_index, ArrayList<State> forbidden_state, int backward_cost) {
        this(digits, parent, digit_changed_index, forbidden_state);
        this.backward_cost = backward_cost;
    }


    /**
     * Set All Children is used to setup all the children for a given State that are legit
     * There are 6 children possible at most, but for most node we will be down to 4 since we cannot change the same
     * index twice in a row
     */
    public void set_all_children() {

        for (int i = 0; i < 3; i++) {

            // Doing a substraction on digit i
            int[] digits_sub = this.digits.clone();
            digits_sub[i] = digits_sub[i] - 1;
            State node_sub = new State(digits_sub, this, i, this.forbidden_state);

            if (is_child_legit(node_sub)) {
                node_sub.backward_cost = backward_cost + 1;
                this.children.add(node_sub);
            }

            // Doing a addition on digit i
            int[] digits_add = this.digits.clone();
            digits_add[i] = digits_add[i] + 1;
            State node_add = new State(digits_add, this, i, this.forbidden_state);

            if (is_child_legit(node_add)) {
                node_add.backward_cost = backward_cost + 1;
                this.children.add(node_add);
            }
        }
    }


    /**
     * Is Child Legit Will take a child state and check if this node is legit
     * @param child : The child of the current state that we want to check for legal status
     * @return : Return true if the child is legit and false otherwise
     */
    public boolean is_child_legit(State child) {

        // Checking if the node is in the forbidden state
        for (int i = 0; i < forbidden_state.size(); i++) {

            if (manhattan_distance(child, forbidden_state.get(i)) == 0) {
                return false;
            }
        }

        // Check if all digits are fine
        for (int i = 0; i < child.digits.length; i++) {
            if (child.digits[i] > 9 || child.digits[i] < 0) {
                return false;
            }
        }

        // Check if the digit_changed_index is legit (can't change the same digit twice rule)
        if (child.digit_changed_index == this.digit_changed_index) {
            return false;
        }

        // Otherwise we are all good this is a legit node to add to the pool
        return true;

    }

    /**
     * Manhattan Distance will calculate the manhattan distance between two state by comparing their digits. This
     * is the heuristic that is used throughout the puzzle solving tasks
     * @param state1 : This is the first state that we are comparing
     * @param state2 : This is the other state that we are comparing
     * @return : We return an integer that correspond to the manhattan distance
     */
    public static int manhattan_distance(State state1, State state2) {
        int[] number1 = state1.digits;
        int[] number2 = state2.digits;
        int distance = 0;

        for (int i = 0; i < number1.length; i++) {
            distance += Math.abs(number1[i] - number2[i]);
        }
        return distance;
    }


    /**
     * Is State Expandable will check if the current state is expandable or if it has been expanded before
     * A state is expandable if a state with the same digits AND the same children hasn't been previously expanded.
     * @param expanded_states : An arraylist of already expanded states
     * @return : true if the state was never expanded before and false if it found it
     */
    public boolean is_state_expandable(ArrayList<State> expanded_states) {
        // Check if node was already expanded
        for (int i = 0; i < expanded_states.size(); i++) {
            State expanded_node = expanded_states.get(i);

            if (this.equals(expanded_node)) {
                return false;
            }
        }

        return true;
    }


    /**
     * Equals checks for equality between this state and an other state. Two state are said to be equal if they have exactly
     * the same digits and the same children. If they have only the same digits they are not equals.
     * @param other : Another state that we want to test the equality with
     * @return true if equals false if not
     */
    public boolean equals(State other) {

        // If we don't have a manhattan distance of 0 we are not the same
        // or if we don't have the same amount of children
        if (manhattan_distance(this, other) != 0 || this.children.size() != other.children.size()) {
            return false;
        }


        // Here we check each of the child for one state if they have the same child in the other state
        for (int i = 0; i < this.children.size(); i++) {

            // We take a child out of this state
            State this_child = this.children.get(i);
            boolean is_found = false;

            // And try to find correspondance in the other state
            for (int j = 0; j < other.children.size(); j++) {
                State other_child = other.children.get(j);

                if (manhattan_distance(this_child, other_child) == 0) {
                    is_found = true;
                    break;
                }
            }

            // If we didn't found it then we return false
            if (!is_found) {
                return false;
            }
        }

        // If we get there it means that everything matches
        return true;


    }
}

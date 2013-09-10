//package sv_esc_solutions;

abstract class BeanCan {

    public static final boolean WHITE = false;
    public static final boolean BLACK = true;

    //@ public static invariant WHITE == false;
    //@ public static invariant BLACK == true;

    /*@ spec_public */ private int num_black; //@ public invariant num_black >= 0;
    /*@ spec_public */ private int num_white; //@ public invariant num_white >= 0;



    /*@ requires M >= 0;
        requires N >= 0;
        requires M + N >= 1;
        ensures num_black == M;
        ensures num_white == N;
        assignable \everything;
    */
    BeanCan(int M, int N) {
    	num_black = M;
    	num_white = N;
    }

    /*@ normal_behavior
        requires (color == WHITE) ==> num_white > 0;
        requires (color == BLACK) ==> num_black > 0;
        requires num_black + num_white >= 1;
        modifies num_black, num_white;
        ensures num_white == 
                    ((color == WHITE) ? \old(num_white) - 1 : \old(num_white));
        ensures num_black == 
                    ((color == BLACK) ? \old(num_black) - 1 : \old(num_black));
        ensures num_white >= 0;
        ensures num_black >= 0;
    */
    public void remove(boolean color) {
	if (color == WHITE) {
	    num_white --;
	}
	else {
	    num_black --;
	}
    }

    /*@ requires num_black + num_white >= 1;
        ensures (num_white > 0 && num_black > 0) ==> 
                     (\result == WHITE || \result == BLACK);
        ensures num_white == 0 ==> \result == BLACK;
        ensures num_black == 0 ==> \result == WHITE;
     */
    public abstract /*@ pure @*/ boolean pick_random();

    /*@ normal_behavior
          requires num_black + num_white >= 1;
          requires num_black >= 0 && num_white >= 0;
          ensures \result == BLACK || \result == WHITE;
          ensures num_black + num_white == 1;
    */
    public boolean play_game() {
        //@ loop_invariant num_black + num_white >= 1;
        //@ loop_invariant num_black >= 0;
        //@ loop_invariant num_white >= 0;
        //@ decreases num_black + num_white;
	while (num_black + num_white > 1) {
	    boolean bean1 = pick_random();
        //@ assert (bean1 == WHITE) ==> num_white > 0;
        //@ assert (bean1 == BLACK) ==> num_black > 0;
        //@ assert num_black + num_white >= 1;
        //@ assert num_black >= 0;
        //@ assert num_white >= 0;
	    remove(bean1);
	    boolean bean2 = pick_random();
	    remove(bean2);
            //@ assert num_black + num_white >= 0;
            //@ assert num_black >= 0;
            //@ assert num_white >= 0;
	    if (bean1 == bean2) {
		num_black ++;
	    }
	    else {
		num_white ++;
	    }
	}
            //@ assert num_black + num_white >= 1;
            //@ assert num_black >= 0;
            //@ assert num_white >= 0;
	return (num_black == 1);
    }
}
    
    
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfayieldsexpression;

/**
 *
 * @author aedemirsen
 */
public class DFAYieldsExpression {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        char[] states = {'s','p','q'};
        
        DFA dfa = new DFA(states);
        
        dfa.setStartState('s');
        dfa.setFinalState('s');
        dfa.setCondition(0, 0, 'p');
        dfa.setCondition(0, 1, '-');
        dfa.setCondition(1, 0, '-');
        dfa.setCondition(1, 1, 's');
        dfa.setCondition(2, 0, 's');
        dfa.setCondition(2, 1, '-');
        
        DFAtoYields.run("01010", dfa);
    }
    
}


class DFA {
    
    char[] states; //DFA nın state leri
    private char startState,finalState;
    char dfaTable[][]; //DFA nın tablo gösterimi için tutulan matris.
    
    public DFA(char c[]){
        this.states = c;
        dfaTable = new char[c.length][2];
    }
    
    public void setStartState(char c){
        for (char state : states) {
            if (state == c) startState = state;
        }
    }
    
    private int getIndex(char c){
        for (int i = 0; i < states.length; i++) {
            if (states[i] == c) {
                return i;
            }
        }
        return -1;
    }
    
    public char getStartState(){
        return startState;
    }
        
    public void setCondition(int x,int y,char c){
        dfaTable[x][y] = c;
    }
    
    public void setFinalState(char c){
        for (char state : states) {
            if (state == c) finalState = state;              
        }
    }
    
    public char getDestinationState(char src,int cond){
        return dfaTable[getIndex(src)][cond];
    }
    
    public char getFinalState(){
        return finalState;
    }
    
}

class DFAtoYields{
    
    static void run(String s,DFA d){
        String str = s;
        char state = d.getStartState();
        System.out.print("("+state+","+str+")");//başlangıç olarak start state ve string yazdırıldı.
        state = d.getDestinationState(state, 
                Integer.parseInt(str.charAt(0)+"")); //state değişiyor, stringe bakarak next state bulundu
        str = str.substring(1); //next state e gidebilmek için condition (0 ve ya 1)        
        for (int i = 0; i < s.length(); i++) {
            if (i == s.length() - 1) {
                if (!(d.getFinalState() == state)) {//eğer final state değil ise 
                    err("This string isn't in this language!");
                    break;
                }
                System.out.println(" --| ("+state+",ε)"); //final durumu
                break;
            }
            System.out.print(" --| ("+state+","+str+")");//next gösterim yazdırıldı.
            state = d.getDestinationState(state, Integer.parseInt(str.charAt(0)+""));
            str = str.substring(1);  
            if (state == '-') { //eğer gösterimde yok ise
                err("This string isn't in this language!");
                System.out.println(" --| X");
                break;
            }
        }
    }
    
    private static void err(String s){
        System.err.println(s);
    }
}
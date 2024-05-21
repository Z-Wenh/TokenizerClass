import java.util.*;
import java.io.*;

public class ToyTokenizer {
  static String assign;
  static int input_index;
  static char input_token;

  //method to call the next token in a string
  static void next_token() {
    if(input_index == assign.length()) 
      input_token = '$'; //End of string
    else {
      input_token = assign.charAt(input_index);
      input_index++;
      //skip over white spaces
      if(input_token == ' ') {
        input_token = assign.charAt(input_index);
        input_index++;
      }
    }
  }
  
  //determine if input token matches expected token
  static void match(char expected_token) {
    if(input_token == expected_token)
      next_token();
    else 
      error(expected_token + "expected got " + input_token);
  }
  
  //take in a string and begin analyzing each tokens
  static void program(String str) {
    assign = str;
    if(assign.charAt(assign.length()-1) != ';') {
      error("Invalid syntax ; expected");
    }
    input_index = 0;
    next_token();
    assignment();
  }

  static void assignment() {
    identifier();
  }

  //making sure an equal sign is present to show proper assignment
  static void Eq() {
    switch(input_token) {
      case '=':
        next_token();
        exp();
        return;
      default:
        error("Improper assignment");
    }
  }

  //expression
  static void exp() {
    term(); 
    exp_prime(); 
  }

  //expression*
  static void exp_prime() {
    switch(input_token) {
      case '+':
      case '-':
        next_token();
        term();
        exp_prime();
        return;
      default:
        return;
    }
  }

  //term
  static void term() {
    factor();
    term_prime();
  }

  //term*
  static void term_prime() {
    switch(input_token) {
      case '*':
      case '/':
        next_token();
        factor();
        term_prime();
        return;
      default:
        return;
    }
  }

  //factor
  static void factor() {
    switch(input_token) {
      case '(':
        next_token();
        exp();
        match(')');
        return;
      case '0':
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9':
        next_token();
        return;
      default:
        error("an operand of ( expected");
    }
  }

  static void identifier() {
    switch(input_token) {
      case ' ': 
      case 'A':
      case 'B':
      case 'C':
      case 'D':
      case 'E':
      case 'F':
      case 'G':
      case 'H':
      case 'I':
      case 'J':
      case 'K':
      case 'L':
      case 'M':
      case 'N':
      case 'O':
      case 'P':
      case 'Q':
      case 'R':
      case 'S':
      case 'T':
      case 'U':
      case 'V':
      case 'W':
      case 'X':
      case 'Y':
      case 'Z':
      case 'a':
      case 'b':
      case 'c':
      case 'd':
      case 'e':
      case 'f':
      case 'g':
      case 'h':
      case 'i':
      case 'j':
      case 'k':
      case 'l':
      case 'm':
      case 'n':
      case 'o':
      case 'p':
      case 'q':
      case 'r':
      case 's':
      case 't':
      case 'u':
      case 'v':
      case 'w':
      case 'x':
      case 'y':
      case 'z':
      case '_':
        next_token();
        Eq();
        return;
      default:
        return;
    }
  }

  static void error (String msg)
  {
    throw new RuntimeException (msg);
  }

  public static void main(String[] args) throws FileNotFoundException{
    if(args.length != 1) {
      System.out.println("Provide a java file");
      System.exit(0);
    }
    //using a scanner to read each line and strip trailing white space of each line.
    Scanner input = new Scanner(new FileReader(args[0]));
    while(input.hasNext()) {
      String line = input.nextLine();
      String[] segment = line.split(" \t");
      for(String seg : segment) {
        seg.stripTrailing();
        program(seg);
        System.out.println(assign + " valid");
      }
    }
    input.close();
  }
}
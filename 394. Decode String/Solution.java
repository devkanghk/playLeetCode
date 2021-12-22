class MySolution {
    public String decodeString(String s) {
        String testStr = s;
        String resultStr = makeStr(testStr);
        
        System.out.println("end...");
        System.out.println(resultStr);
        return resultStr;
    }
  
  public String makeStr(String myStr){
    System.out.println("start...");
    System.out.println(myStr);
  
    if(myStr.lastIndexOf("[") == -1){
    	return myStr;
    }
  	//System.out.println(myStr.lastIndexOf("["));
    //System.out.println(myStr.charAt(4));
    
    int lastBracketStart = myStr.lastIndexOf("[");
    int lastBracketEnd = myStr.indexOf("]", lastBracketStart);
    int numberStartIndex = lastBracketStart - 1;
      
    System.out.println(numberStartIndex);
    while(true){
      
      try {
          Integer.parseInt(String.valueOf(myStr.charAt(numberStartIndex)));
      } catch (NumberFormatException nfe) {
      	  numberStartIndex = numberStartIndex + 1;
          break;
      }
        
      if(numberStartIndex == 0){
          break;
      }else{
          numberStartIndex = numberStartIndex - 1;
      }
      
    }
    //System.out.println(numberStartIndex);
    
    //System.out.println(myStr.substring(0, numberStartIndex));
    
    //System.out.println(myStr.substring(lastBracketEnd+1, myStr.length()));
    
    String numberStr = "";
    for(int i=numberStartIndex; i<lastBracketStart; i++){
    	numberStr += myStr.charAt(i);
    }
    System.out.println("numberStr="+numberStr);
    int multipleNumber = Integer.parseInt(numberStr);
    
    String repeatStr = "";
    for(int i=lastBracketStart+1; i<lastBracketEnd; i++){
      repeatStr += myStr.charAt(i);
    }
    
    System.out.println("repeatStr="+repeatStr);
    
    String repeatResultStr = "";
    for(int i=0; i<multipleNumber; i++){
      repeatResultStr += repeatStr;
    }
    
    System.out.println(repeatResultStr);
    
    String newStr = myStr.substring(0, numberStartIndex) + repeatResultStr + myStr.substring(lastBracketEnd+1, myStr.length());
    
    System.out.println(newStr);
    
    return makeStr(newStr);
  }

}



public class BestSolution {
    public String decodeString(String s) {
        String res = "";
        Stack<Integer> countStack = new Stack<>();
        Stack<String> resStack = new Stack<>();
        int idx = 0;
        while (idx < s.length()) {
            if (Character.isDigit(s.charAt(idx))) {
                int count = 0;
                while (Character.isDigit(s.charAt(idx))) {
                    // s.charAt(idx) - '0' 이거 진짜 찢었다.. '0'을 빼줌으로서 char 형에서 int로 자연스럽게 변경해주고 있음 
                    // 예를 들어 3이었으면 아스키 코드는 51, '0'의 아스키 코드는 48이다. 51 - 48 = 3 소름 ㄷㄷ..
                    // 야 이거 이미 고전적인 수법이었구나 나만 몰랐구나..
                    // 10 * count 해서 더해주는 것도 경이로움. 32였으면 처음엔 10*0+3 으로 3 만들어 놓고
                    // 다음에는 10 * 3 + 2 = 32 이렇게 만들어주고 있음 이 부분도 와.. 놀랍다.
                    count = 10 * count + (s.charAt(idx) - '0'); 
                    idx++;
                }
                countStack.push(count);
            }
            else if (s.charAt(idx) == '[') {
                // 반복된 글자를 여기에 쌓아놓는구나. 처음에는 공백일테고. 다음 부터는 그동안 반복된 문자들을 쌓아놓는다.
                resStack.push(res);
                res = "";
                idx++;
            }
            else if (s.charAt(idx) == ']') {
                // 반복된 글자를 가져와서, 거기에 append하는 방식으로 문자열을 만든다..
                StringBuilder temp = new StringBuilder (resStack.pop());
                int repeatTimes = countStack.pop();
                for (int i = 0; i < repeatTimes; i++) {
                    temp.append(res);
                }
                // 이게 리턴할 최종 문자열.. temp에 계속 붙이니까 이 값을 그대로 가져가는구나..
                res = temp.toString();
                idx++;
            }
            else {
                res += s.charAt(idx++);
            }
        }
        return res;
    }

    // 와 진짜 이거 보니까 내 코드 원시인이 만든거 같네 ㅋㅋㅋㅋ
}
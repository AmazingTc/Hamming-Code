public class HaimingCode {
    private int checkRule = 0;//当前规则
    private boolean isError = false;//是否出错
    private Integer errorPosition=null;//出错位置
    public HaimingCode(int rule) {
        this.checkRule=rule;
    }
    public HaimingCode() {
        this.checkRule=0;
    }
    //发送
    public String generateCode(String strs) {
        if(inputError(strs)){return "输入错误";}
        String[] strArr = strs.trim().split("");
        int r=0;//校验位
        int m=strs.length();//数据位
        while (m + r>(1<<r)-1){
            r++;
        }
        int resLength=m+r;
        int[] resultArray = new int[resLength];
        //原数据：[1011]
        //新数组： [x x x x x x x]
        //        [x x 1 x 0 1 1]
        for (int i = 0, j = 0, num = 1; i < resLength; i++) {
            if ( i == num-1) {
                num = num << 1;
            } else {
                resultArray[i] = Integer.parseInt(strArr[j++]);
            }
        }

        // 填充校验位
        // [x x 1 x 0 1 1]
        //i=1, 3 5 7
        //i=2, 3 6 7
        for (int i = 1; i < resLength; i = i << 1) {
            int code = checkRule;
            for (int j = i + 1; j <= resLength; j++) {
                if ((i & j) != 0) {
                    code = code ^ resultArray[j - 1];
                }
            }
            resultArray[i - 1] = code;
        }
        StringBuffer result = new StringBuffer();
        for (int i : resultArray) {result.append(i);}
        return result.toString();
    }
    //接收
    public String decoding(String strs) {
        if(inputError(strs)){return "输入错误";}
        String[] strArr = strs.trim().split("");
        int totalLength = strs.length();
        int x = 0;//校验位位数
        while ((1 << x) < totalLength- 1 ) x++;

        int[] checkArr = new int[x];
        int checkIndex=0;
        //通过校验位检验
        //[ 0 1 1 0 0 1 1]  [1 0 1 1 0 1 1]
        // 0+1+0+1=0   1+1+0+1=1
        // 1+1+1+1=0   0+1+1+1=1
        // 0+0+1+1=0   1+0+1+1=1
        for (int i = 1; i < totalLength; i = i << 1) {
            int code = Integer.parseInt(strArr[i - 1]);//校验位数据
            for (int j = i + 1; j <= totalLength; j++) {
                if ((i & j) != 0) {
                    code = code ^ Integer.parseInt(strArr[j - 1]);
                }
            }
            checkArr[checkIndex++] = code == checkRule ? 0 : 1;
        }
        //[0,1,0]  [1,1,0]
        //num:1   num:3
        int num = 0;
        for (int i = 0; i < x; i++) {
            if (checkArr[i] != 0) {
                num += (1 << i);
            }
        }

        if (num > 0) {
            isError = true;
            errorPosition = num;
            String current = strArr[num - 1];
            strArr[num-1]=current.equals("0")?"1":"0";
        }

        StringBuffer result = new StringBuffer();
        for (int i = 0, j = 1; i < totalLength; i++) {
            if (i == j - 1) {
                j = j << 1;
            } else {
                result.append(strArr[i]);
            }
        }
        return result.toString();
    }
    public boolean inputError(String strs){
        if (strs == null || strs.equals("")) {return true;}
        String[] srcArray = strs.trim().split("");
        for (String str : srcArray) {
            if (!(str.equals("0")||str.equals("1"))) {
                return true;
            }
        }
        return false;
    }
    public boolean isErrorCode() {
        return isError;
    }
    public Integer getErrorIndex() {
        return errorPosition;
    }

}

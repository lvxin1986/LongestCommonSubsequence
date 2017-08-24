import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class StringTool {


    //在动态规划矩阵生成方式当中，每生成一行，前面的那一行就已经没有用了，因此这里只需使用一维数组，而不是常用的二位数组
    public static String getLCString(char[] str1, char[] str2) {
        int len1, len2;
        len1 = str1.length;
        len2 = str2.length;
        int maxLen = len1 > len2 ? len1 : len2;

        int[] max = new int[maxLen];// 保存最长子串长度的数组
        int[] maxIndex = new int[maxLen];// 保存最长子串长度最大索引的数组
        int[] c = new int[maxLen];
        String returnStr = "";

        int i, j;
        for (i = 0; i < len2; i++) {
            for (j = len1 - 1; j >= 0; j--) {
                if (str2[i] == str1[j]) {
                    if ((i == 0) || (j == 0))
                        c[j] = 1;
                    else
                        c[j] = c[j - 1] + 1;//此时C[j-1]还是上次循环中的值，因为还没被重新赋值
                } else {
                    c[j] = 0;
                }

                // 如果是大于那暂时只有一个是最长的,而且要把后面的清0;
                if (c[j] > max[0]) {
                    max[0] = c[j];
                    maxIndex[0] = j;

                    for (int k = 1; k < maxLen; k++) {
                        max[k] = 0;
                        maxIndex[k] = 0;
                    }
                }
                // 有多个是相同长度的子串
                else if (c[j] == max[0]) {
                    for (int k = 1; k < maxLen; k++) {
                        if (max[k] == 0) {
                            max[k] = c[j];
                            maxIndex[k] = j;
                            break; // 在后面加一个就要退出循环了
                        }
                    }
                }
            }
//            for (int temp : c) {
//                System.out.print(temp);
//            }
//            System.out.println();
        }
        //打印最长子字符串
        for (j = 0; j < maxLen; j++) {
            if (max[j] > 0) {
                System.out.println("第" + (j + 1) + "个公共子串:");
                for (i = maxIndex[j] - max[j] + 1; i <= maxIndex[j]; i++) {
                    System.out.print(str1[i]);
                    returnStr = returnStr + str1[i];
                }
                System.out.println(" ");
            }
        }
        return returnStr;
    }


    public static int compute(char[] str1, char[] str2)
    {
        int size1 = str1.length;
        int size2 = str2.length;
        if (size1 == 0 || size2 == 0) return 0;

        // the start position of substring in original string
        int start1 = -1;
        int start2 = -1;
        // the longest length of common substring
        int longest = 0;

        // record how many comparisons the solution did;
        // it can be used to know which algorithm is better
        int comparisons = 0;

        for (int i = 0; i < size1; ++i)
        {
            int m = i;
            int n = 0;
            int length = 0;
            while (m < size1 && n < size2)
            {
                ++comparisons;
                if (str1[m] != str2[n])
                {
                    length = 0;
                }
                else
                {
                    ++length;
                    if (longest < length)
                    {
                        longest = length;
                        start1 = m - longest + 1;
                        start2 = n - longest + 1;
                    }
                }

                ++m;
                ++n;
            }
        }

        // shift string2 to find the longest common substring
        for (int j = 1; j < size2; ++j)
        {
            int m = 0;
            int n = j;
            int length = 0;
            while (m < size1 && n < size2)
            {
                ++comparisons;
                if (str1[m] != str2[n])
                {
                    length = 0;
                }
                else
                {
                    ++length;
                    if (longest < length)
                    {
                        longest = length;
                        start1 = m - longest + 1;
                        start2 = n - longest + 1;
                    }
                }

                ++m;
                ++n;
            }
        }
        System.out.printf("from %d of str1 and %d of str2, compared for %d times\n", start1, start2, comparisons);
        return longest;
    }

    public static boolean isNull(String str) {
        if (str == null || "".equals(str.trim())){
            return true;
        }
        return false;
    }

    public static String getLCString(List<String> strs) {
        String tmp = strs.get(0).trim();
        for (String str:strs){
            if (isNull(str)) {
                continue;
            }
            tmp = str;
            break;
        }
        for (String str:strs){
            if (isNull(str)) {
                continue;
            }
            System.out.println("processing " + tmp+ " and " +str);
            tmp = getLCString(tmp.toCharArray(), str.trim().toLowerCase().toCharArray());
        }
        return tmp;
    }
    public static void main(String[] args) throws IOException {
        List<String> lines= Files.readAllLines(Paths.get("D://haha.haha"), Charset.forName("UTF-8"));
        for(String line:lines){
            System.out.println(line);
        }


        System.out.println(" Start compute!!!");
        String ff = getLCString(lines);
        System.out.println("the final common string is: " + ff);
        System.out.println(" Stop compute!!!");

    }
}

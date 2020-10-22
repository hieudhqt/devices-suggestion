package hieu.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringHelper {

    private static final String[] IGNORE_TAGS = new String[] {"head", "noscript", "style", "iframe", "script", "meta", "select"};

    public static String refineHTML(String content) {
        content = removeUnnecessaryTags(content);
        content = fixString(content);
        content = removeUnnecessaryTags(content);
        return content;
    }

    private static String removeUnnecessaryTags(String content) {
        String expression = "<!--.*?-->";
        content = content.replaceAll(expression, "");

        expression = "&nbsp;";
        content = content.replaceAll(expression, "");
        
        expression = "<br>";
        content = content.replaceAll(expression, "");
        
        expression = "<br/>";
        content = content.replaceAll(expression, "");
        
        expression = "</br>";
        content = content.replaceAll(expression, "");
        
        for (String exp : IGNORE_TAGS) {
            expression = String.format("<%s.*?</%s>", exp, exp);
            content = content.replaceAll(expression, "");
        }
        return content;
    }
    
    private static boolean isAlphaChar(char x) {
        return (x >= 'a' && x <= 'z') || (x >= '0' && x <= '9') || (x >= 'A' && x <= 'Z');
    }
    
    private static String getTagName(String content) {
        if (content.charAt(content.length() - 2) == '/') {
            return null;
        }
        String res = "";
        int i = 1;
        if (content.charAt(1) == '/') {
            res = res + "/";
            i++;
        }
        while (isAlphaChar(content.charAt(i))) {
            res = res + content.charAt(i);
            i++;
        }
        if (res.length() == 0 || (res.length() == 1 && res.charAt(0) == '/')) {
            return null;
        }
        return res;
    }

    private static String fixString(String content) {
        List<String> stack = new ArrayList<>();
        List<Integer> li = new ArrayList<>();
        List<String> addTag = new ArrayList<>();

        int size = content.length();
        int[] mark = new int[size];
        Arrays.fill(mark, -1);

        int i = 0;
        while (i <content.length()) {
            if (content.charAt(i) == '<') {
                int j = i + 1;

                String tagTmp = "" + content.charAt(i);

                while (j < content.length() && content.charAt(j) != '>') {
                    tagTmp = tagTmp + content.charAt(j);
                    j++;
                }

                int curEnd = j;
                tagTmp = tagTmp + '>';
                i = j + 1;

                String tag = getTagName(tagTmp);

                if (tag != null) {
                    if (tag.charAt(0) != '/') {
                        stack.add(tag);
                        li.add(curEnd);
                    } else {
                        while (stack.size() > 0) {
                            if (stack.get(stack.size() - 1).equals(tag.substring(1))) {
                                stack.remove(stack.size() - 1);
                                break;
                            } else {
                                addTag.add(stack.get(stack.size() - 1));
                                mark[li.get(li.size() - 1)] = addTag.size() - 1;
                                stack.remove(stack.size() - 1);
                                li.remove(li.size() - 1);
                            }
                        }
                    }
                }
            } else {
                i++;
            }
        }
        while (stack.size() > 0) {
            addTag.add(stack.get(stack.size() - 1));
            mark[li.get(li.size() - 1)] = addTag.size() - 1;
            stack.remove(stack.size() - 1);
            li.remove(li.size() - 1);
        }
        String newContent = "";
        for (int j = 0; j < content.length(); j++) {
            newContent = newContent + content.charAt(j);
            if (mark[j] != -1) {
                newContent = newContent + "</" + addTag.get(mark[j]) + ">";
            }
        }

        return newContent;
    }
    
    public static String URLify(String url) {
        StringBuilder sb = new StringBuilder();
        for (char currentChar : url.toCharArray()) {
            if (currentChar == ' ') {
                sb.append("%20");
            } else {
                sb.append(currentChar);
            }
        }
        return sb.toString();
    }

}

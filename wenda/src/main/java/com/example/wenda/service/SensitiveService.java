package com.example.wenda.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.CharUtils;
/**
 * @author zyy
 * @date 2019/7/5 20:09
 */
@Service
public class SensitiveService implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader read = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineText;
            while((lineText=bufferedReader.readLine())!=null){
                addWord(lineText.trim());
            }
        } catch (IOException e) {
            logger.error("读取敏感词文件失败"+e.getMessage());
        }
    }

    //初始化字典树，添加敏感词
    public void addWord(String lineText){
        TireNode tempNode = rootNode;
        for(int i=0;i<lineText.length();i++){
            Character c = lineText.charAt(i);
            if(isSymbol(c)){
                continue;
            }
            TireNode node = tempNode.getSubNode(c);

            if(node == null){
                node = new TireNode();
                tempNode.addSubNode(c,node);
            }

            tempNode = node;

            if(i==lineText.length()-1){
                tempNode.end = true;
            }
        }
    }

    private class TireNode{
        //是不是关键词的结尾
        private boolean end = false;

        //当前节点下的所有子节点
        private Map<Character,TireNode> subNodes = new HashMap<>();

        private void addSubNode(Character key,TireNode node){
            subNodes.put(key,node);
        }

        private TireNode getSubNode(Character key){
            return subNodes.get(key);
        }

        boolean isKeywordEnd(){return end;}

        void setkeywordEnd(boolean end){
            this.end = end;
        }
    }

    private TireNode rootNode = new TireNode();

    //非正常字符
    private boolean isSymbol(char c){
        int ci = (int)c;
        //东亚文字 0x2EC8-0x9FFF
        return !CharUtils.isAsciiAlphanumeric(c)&&(ci<0x2EC8||ci>0x9FFF);
    }

    public String filter(String text){
        if(StringUtils.isEmpty(text)){
            return text;
        }
        StringBuilder result= new StringBuilder();
        int begin = 0;
        int position =0;
        TireNode node = rootNode;
        String repalcement = "***";

        while(position<text.length()){
            char c = text.charAt(position);
            if(isSymbol(c)){
                if(node == rootNode){
                    result.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }
            node = node.getSubNode(c);
            if(node ==null){
                result.append(text.charAt(begin));
                position=begin+1;
                begin = position;
                node = rootNode;
            }else if(node.isKeywordEnd()){
                //发现敏感词
                result.append(repalcement);
                position=position+1;
                begin = position;
                node = rootNode;
            }else{
                ++position;
            }
        }
        result.append(text.substring(begin));
        return result.toString();
    }

    public static void main(String[] args) {
        SensitiveService s = new SensitiveService();
        s.addWord("色-情");
        s.addWord("赌博");
        System.out.println(s.filter("你好色情不赌博"));
    }
}

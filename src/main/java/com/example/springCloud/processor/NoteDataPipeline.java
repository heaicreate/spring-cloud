package com.example.springCloud.processor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

public class NoteDataPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        String demoData = resultItems.get("note");
        String txtcontent = demoData.replaceAll("</?[^>]+>", ""); //剔出<html>的标签
        txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");//去除字
        System.out.println(txtcontent);
        Document document = Jsoup.parse(demoData);
        System.out.println(extractText(document));

    }


    private static String extractText(Node node) {
        /* TextNode直接返回结果 */
        if (node instanceof TextNode) {
            return ((TextNode) node).text();
        }
        /* 非TextNode的Node，遍历其孩子Node */
        List<Node> children = node.childNodes();
        StringBuffer buffer = new StringBuffer();
        for (Node child : children) {
            buffer.append(extractText(child));
        }
        return buffer.toString();
    }


}

package com.bigdata.controller;

import com.bigdata.search.Doc;
import com.bigdata.search.Esutil;
import com.bigdata.search.HbaseUtils;
import com.bigdata.utils.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class EsController {
    static final Logger logger = LoggerFactory.getLogger(EsController.class);

    public static String path = "F:/index";

    @RequestMapping("create.do")
    public String createIndex() throws Exception {
        return "./create.jsp";
    }

    @RequestMapping("welcome.do")
    public String welcome() throws Exception {
        return "./welcome.jsp";
    }

    @RequestMapping("json.do")
    @ResponseBody
//    public HashMap<String,String> json(@RequestBody  Person person) throws Exception {
    public HashMap<String,String> json() throws Exception {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("aaa","aaa");
        map.put("bbb","bbb");
        return map;
    }

    @RequestMapping("search.do")
    public String serachArticle(Model model,
                                @RequestParam(value = "keyWords", required = false) String keyWords,
                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        try {
            //if(pageNum > 1) {
            keyWords = new String(keyWords.getBytes("ISO_8859_1"), "UTF-8");
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }


        Map<String, Object> map = new HashMap<String, Object>();
        int count = 0;
        try {
            map = Esutil.search(keyWords, "test_hdcom1", "doc", (pageNum - 1) * pageSize, pageSize);
            count = Integer.parseInt(((Long) map.get("count")).toString());
        } catch (Exception e) {
            logger.error("查询索引错误!{}", e);
            e.printStackTrace();
        }
        PageUtil<Map<String, Object>> page = new PageUtil<Map<String, Object>>(String.valueOf(pageNum), String.valueOf(pageSize), count);
        List<Map<String, Object>> articleList = (List<Map<String, Object>>) map.get("dataList");

        page.setList(articleList);

        model.addAttribute("total", count);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("page", page);
        model.addAttribute("kw", keyWords);
        return "./index.jsp";
    }


    /**
     * 查看文章详细信息
     *
     * @return
     */
    @RequestMapping("/detailDocById/{id}.do")
    public String detailArticleById(@PathVariable(value = "id") String id, Model modelMap) throws IOException {
        //这里用的查询是直接从hbase中查询一条字符串出来做拆分封装，这里要求同学们用protobuffer
        HbaseUtils hbaseUtils = new HbaseUtils();
        Doc Doc = hbaseUtils.get(hbaseUtils.TABLE_NAME, id);
        modelMap.addAttribute("Doc", Doc);
        return "/detail.jsp";
    }
}

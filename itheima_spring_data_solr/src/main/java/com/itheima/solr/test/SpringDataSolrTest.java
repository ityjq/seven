package com.itheima.solr.test;

import com.pinyougou.pojo.TbItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-solr.xml")
public class SpringDataSolrTest {

    @Autowired
    private SolrTemplate solrTemplate;

    //添加/修改
    @Test
    public void add(){
        //SpringDataSolr简化了了Solrj操作的solr服务器，SpringDataSolr是操作对象来操作solr服务器的
        //SpringDataSolr操作对象：是因为可以把对象的属性名和业务域之间映射起来；
        //那么SpringDataSolr就可以只对对象进行操作了！

        List<TbItem> itemList = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            TbItem item = new TbItem();
            item.setId(1000l+i);
            item.setTitle("小米"+i);
            item.setPrice(new BigDecimal(1000+i));
            item.setSeller("xioami");
            item.setCategory("手机");
            item.setBrand("小米");
            item.setGoodsId(1000l+i);

            itemList.add(item);
        }

        solrTemplate.saveBeans(itemList);
        solrTemplate.commit();
    }

    //删除
    @Test
    public void dele(){
        //删除所有；在企业中不用；肾用！
        solrTemplate.delete(new SimpleQuery("item_goodsid:149187842867971"));
        //solrTemplate.deleteById("1000");
        solrTemplate.commit();
    }


    //查询
    @Test
    public void search(){
        //1、查询条件;查询所有的
        SimpleQuery query = new SimpleQuery("*:*");

        //设置分页的属性
        query.setOffset(1);//Internet类型的参数；设置分页的起始值（索引值）；默认为0
        //当我们自己让他来分页的时候，需要自己设置分页的起始页码数 ： (当前页-1)*每页显示记录数
        query.setRows(10);//nternet类型的参数；设置分页的每页显示的记录数；默认为10

        //对象
        //用Soletemplate来搜索。分页搜索默认其实值0，每页查询记录数10
        ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);

        //获取数据
        List<TbItem> itemList = page.getContent();
        long totalElements = page.getTotalElements();
        int totalPages = page.getTotalPages();//获取总记录数的
        //如果在搜索的时候没有设置分页属性的话，那么这个值默认为当前页

        System.err.println("总记录数=：" + totalElements + "; 总页数=" + totalPages);
        forList(itemList);

    }

    public void forList(List<TbItem> itemList){
        for(TbItem item : itemList){
            System.err.println("商品的Title：" + item.getTitle());
        }
    }

}

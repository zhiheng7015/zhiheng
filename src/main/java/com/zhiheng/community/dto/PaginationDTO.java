package com.zhiheng.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页显示分也所需的属性
 */
@Data
public class PaginationDTO {
    private List<QuestionDTO> questionDTOList;
    private boolean showPrevious;
    private boolean showFirstPage ;
    private boolean showNext ;
    private boolean showEndpage ;
    private Integer page;
    private List<Integer> pages=new ArrayList<>();
    private Integer totalPage;//页数

    public void setPagnation(Integer totalCount, Integer page, Integer size) {
        System.out.println(page+size);
        if (totalCount % size==0){
            totalPage=totalCount/size;
        }else {
            totalPage=totalCount/size+1;
        }
        //判断如果page为负数就等一大一总页数就等于最有一页
        if (page<1){
            page=1;
        }
        if (page>totalPage){
            page=totalPage;
        }
        //当前页负值
        this.page=page;
        //显示的页数
        pages.add(page);
        for (int i=1;i<=3;i++){
            if (page-i>0){
                pages.add(0,page-i);
            }
            if (page+i<=totalPage){
                pages.add(page+i);
            }
        }
        //是否展示上一页
        if (page==1){
            showPrevious=false;
        }else {
            showPrevious=true;
        }
        //是否展示下一页
        if (page==totalPage){
            showNext=false;
        }else {
            showNext=true;
        }
        //是否展示第一页
        if (pages.contains(1)){
            showFirstPage=false;
        }else {
            showFirstPage=true;
        }
        //是否展示最后一页
        if (pages.contains(totalPage)){
            showEndpage=false;
        }else {
            showEndpage=true;
        }
    }
}

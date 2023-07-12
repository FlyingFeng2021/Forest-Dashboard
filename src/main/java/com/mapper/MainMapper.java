package com.mapper;

import com.entity.DetailsData;
import com.entity.QueryInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface MainMapper {

    @Insert("insert into tree(start_time,end_time,time,tag) value(#{start_time},#{end_time},#{time},#{tag})")
    public void initData(DetailsData detailsData);

    @Insert("truncate table tree")
    public void delData();

    @Select("select * from tree order by start_time ASC limit #{pageNum},#{pageSize} ")
    public List<DetailsData> page(int pageNum,int pageSize);

    @Select("select count(*) from tree")
    public int getTotal();
}

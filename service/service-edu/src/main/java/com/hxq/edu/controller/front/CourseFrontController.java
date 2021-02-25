package com.hxq.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxq.commonutils.JwtUtils;
import com.hxq.commonutils.R;
import com.hxq.commonutils.ordervo.CourseWebVoOrder;
import com.hxq.edu.client.OrdersClient;
import com.hxq.edu.entity.Course;
import com.hxq.edu.entity.chapter.ChapterVo;
import com.hxq.edu.entity.frontvo.CourseFrontVo;
import com.hxq.edu.entity.frontvo.CourseWebVo;
import com.hxq.edu.service.ChapterService;
import com.hxq.edu.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/***
 *@author 何鑫强
 *@date 2021/1/12
 */
@RestController
@RequestMapping("/edu/courseFront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private OrdersClient ordersClient;

    //1.条件查询带分页查询课程
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false)CourseFrontVo courseFrontVo){
        Page<Course>coursePage = new Page<>(page,limit);
        Map<String,Object>map = courseService.getCourseFrontList(coursePage,courseFrontVo);
        //返回分页所有数据
        return R.ok().data(map);
    }

    //2.课程详情显示方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        //根据课程ID，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        //根据用户和课程id查询当前课程是否已经支付过了
        boolean buyCourse = ordersClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy",buyCourse);
    }

    //3.根据课程id查询课程信息 （被远程调用）
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id){
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}

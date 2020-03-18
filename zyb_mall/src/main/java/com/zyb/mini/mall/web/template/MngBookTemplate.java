package com.zyb.mini.mall.web.template;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.zyb.mini.mall.config.OSSConfig;
import com.zyb.mini.mall.framework.component.OssComponent;
import com.zyb.mini.mall.pojo.entity.BookImg;
import com.zyb.mini.mall.pojo.entity.GoodsBook;
import com.zyb.mini.mall.pojo.param.background.AddBookParam;
import com.zyb.mini.mall.pojo.param.background.ByIdParam;
import com.zyb.mini.mall.pojo.param.background.UpdateBookParam;
import com.zyb.mini.mall.service.BookImgService;
import com.zyb.mini.mall.service.GoodsBookService;
import com.zyb.mini.mall.service.background.BackgroundService;
import com.zyb.mini.mall.service.common.CommonService;
import com.zyb.mini.mall.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/11/10 星期日 12:22.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */
@RequestMapping("/template/mng/book")
@Controller
@Validated
public class MngBookTemplate {


    private final GoodsBookService goodsBookService;
    private final BackgroundService backgroundService;
    private final OssComponent oss;
    private final CommonService commonService;
    private final BookImgService bookImgService;

    public MngBookTemplate(GoodsBookService goodsBookService, BackgroundService backgroundService, OssComponent oss, CommonService commonService, BookImgService bookImgService) {
        this.goodsBookService = goodsBookService;
        this.backgroundService = backgroundService;
        this.oss = oss;
        this.commonService = commonService;
        this.bookImgService = bookImgService;
    }

    @GetMapping
    public String mngBook(Model model) {
        List<GoodsBook> list = goodsBookService.list();
        model.addAttribute("list", list);
        return "mng/book";
    }


    @PostMapping("/edit/{id}/save")
    public String editSave(UpdateBookParam param, MultipartFile file, List<MultipartFile> files,
                           @PathVariable("id") long id
    ) throws IOException {

        param.setId(id);
        if (!file.isEmpty()) {
            String mainImg = oss.upload(file.getInputStream(), FileUtils.getFileSuffix(file.getOriginalFilename()), OSSConfig.Dir.IMG);
            param.setMainImg(mainImg);
        }

        if (files.isEmpty()) {
            List<BookImg> list = bookImgService.list(new QueryWrapper<BookImg>().lambda().eq(BookImg::getGoodsBookId, id));
            param.setImgList(list);
        } else {
            param.setImgList(Lists.newArrayList());
            for (MultipartFile multipartFile : files) {
                if (StringUtils.isNotBlank(multipartFile.getOriginalFilename())) {
                    String sonImg = oss.upload(multipartFile.getInputStream(), FileUtils.getFileSuffix(multipartFile.getOriginalFilename()), OSSConfig.Dir.IMG);
                    BookImg bookImg = new BookImg();
                    bookImg.setImgName(sonImg.substring(sonImg.lastIndexOf("/")));
                    bookImg.setImgUrl(sonImg);
                    param.getImgList().add(bookImg);
                }
            }
        }

        backgroundService.updateBook(param);

        return "redirect:/template/mng/book";
    }


    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") long id) {


        // 获取图书信息
        GoodsBook book = goodsBookService.getById(id);
        List<BookImg> list = bookImgService.list(new QueryWrapper<BookImg>().lambda().eq(BookImg::getGoodsBookId, id));
        model.addAttribute("list", list);
        model.addAttribute("book", book);


        model.addAttribute("years", commonService.selectYearTypeBox());
        model.addAttribute("types", commonService.selectBookTypeBox());
        model.addAttribute("otypes", commonService.selectBookOtherTypeBox());
        model.addAttribute("phases", commonService.selectPhaseTypeBox());

        return "mng/book-edit";
    }

    @GetMapping("/add")
    public String pageAdd(Model model) {

        model.addAttribute("years", commonService.selectYearTypeBox());
        model.addAttribute("types", commonService.selectBookTypeBox());
        model.addAttribute("otypes", commonService.selectBookOtherTypeBox());
        model.addAttribute("phases", commonService.selectPhaseTypeBox());

        return "mng/book-add";
    }


    @PostMapping("/add/one")
    public String addOne(AddBookParam param, MultipartFile file, List<MultipartFile> files) throws IOException {

        String mainImg = oss.upload(file.getInputStream(), FileUtils.getFileSuffix(file.getOriginalFilename()), OSSConfig.Dir.IMG);
        param.setMainImg(mainImg);


        param.setImgList(Lists.newArrayList());
        for (MultipartFile multipartFile : files) {

            if (StringUtils.isNotBlank(multipartFile.getOriginalFilename())) {
                String sonImg = oss.upload(multipartFile.getInputStream(), FileUtils.getFileSuffix(multipartFile.getOriginalFilename()), OSSConfig.Dir.IMG);

                BookImg bookImg = new BookImg();
                bookImg.setImgName(sonImg.substring(sonImg.lastIndexOf("/")));
                bookImg.setImgUrl(sonImg);

                param.getImgList().add(bookImg);
            }
        }

        backgroundService.addBook(param);
        return "redirect:/template/mng/book";
    }

    @ResponseBody
    @PostMapping("/up/{up}/{id}")
    public void up(@PathVariable("id") long id, @PathVariable("up") boolean up) {
        GoodsBook book = goodsBookService.getById(id);
        book.setIsUp(up);
        goodsBookService.updateById(book);
    }

    @ResponseBody
    @PostMapping("/del/{id}")
    public void del(@PathVariable("id") long id) {
        ByIdParam param = new ByIdParam();
        param.setId(id);
        backgroundService.deleteBook(param);
    }

}

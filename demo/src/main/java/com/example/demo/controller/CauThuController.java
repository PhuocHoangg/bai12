package com.example.demo.controller;



import com.example.demo.Validate.CauThuValidate;
import com.example.demo.dto.CauThuDTO;
import com.example.demo.entity.CauThu;
import com.example.demo.entity.DoiTuyen;
import com.example.demo.repository.ICauThuRepository;
import com.example.demo.repository.IDoiTuyenRepository;
import com.example.demo.service.ICauThuService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/cauthu")
public class CauThuController {

    @Autowired
    private ICauThuService cauThuService;

    @Autowired
    private ICauThuRepository cauThuRepository;
    @Autowired
    private IDoiTuyenRepository doiTuyenRepository;


    @GetMapping("/list")
    public String list(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                       @RequestParam(name = "searchName", required = false, defaultValue = "") String searchName
    ,@CookieValue(value="lastImage",required = false)String lastImage,Model model) {
        Pageable pageable = PageRequest.of(page, 3, Sort.by("name").and(Sort.by("doiTuyen").ascending()));
        Page<CauThu> cauThu = cauThuService.findAllCauThu(searchName, pageable);
        model.addAttribute("cauThuList", cauThu.getContent());
        model.addAttribute("searchName", searchName);
        model.addAttribute("cauThuPage",cauThu);
        model.addAttribute("doiTuyen",doiTuyenRepository.findAll());
        model.addAttribute("lastImage",lastImage);

        return "list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("cauThuDto", new CauThuDTO());
        model.addAttribute("cauThu", new CauThu());
        model.addAttribute("doiTuyenList", doiTuyenRepository.findAll());
        return "add";
    }


    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("cauThuDto") CauThuDTO cauThuDTO,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      Model model) {
        CauThuValidate cauThuValidate = new CauThuValidate();
        cauThuValidate.validate(cauThuDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("doiTuyenList", doiTuyenRepository.findAll());
            return "add";
        }
        CauThu cauThu = new CauThu();
        BeanUtils.copyProperties(cauThuDTO, cauThu);
        int doiTuyenId = cauThu.getDoiTuyen().getId();
        DoiTuyen dt = doiTuyenRepository.findById(doiTuyenId).orElse(null);
        cauThu.setDoiTuyen(dt);
        cauThuService.addCauThu(cauThu);
        redirectAttributes.addFlashAttribute("mess", "Thêm cầu thủ thành công!");
        return "redirect:/cauthu/list";
    }



    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        CauThu cauThu = cauThuService.findCauThuByCauId(id);
        model.addAttribute("cauThu", cauThu);
        return "edit";
    }



    @PostMapping("/edit")
    public String update(@ModelAttribute("cauThu") CauThu cauThu,
                         RedirectAttributes redirectAttributes) {
        cauThuService.updateCauThu(cauThu);
        redirectAttributes.addFlashAttribute("mess", "Cập nhật cầu thủ thành công!");
        return "redirect:/cauthu/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        cauThuService.deleteCauThu(id);
        redirectAttributes.addFlashAttribute("mess", "Xóa cầu thủ thành công!");
        return "redirect:/cauthu/list";
    }
    @GetMapping("/details/{id}")
    public String details(@PathVariable (name="id")int id, Model model, HttpServletResponse httpServletResponse) {
        CauThu cauThu = cauThuService.findCauThuByCauId(id);
        if (cauThu.getImage() != null && !cauThu.getImage().trim().isEmpty()) {
            Cookie cookie = new Cookie("lastImage", cauThu.getImage());
            cookie.setMaxAge(3600);
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);
            model.addAttribute("cauThu", cauThu);

        }
        return "details";
    }
    @GetMapping("/favorite/list")
    public String showFavoriteList(HttpSession session, Model model) {
        List<CauThu> favoriteList = (List<CauThu>) session.getAttribute("favoriteList");
        model.addAttribute("favoriteList", favoriteList);
        return "favorite-list";
    }

    @GetMapping("/favorite/add/{id}")
    public String addToFavorite(@PathVariable("id") int id,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        CauThu cauThu = cauThuService.findCauThuByCauId(id);
        if (cauThu == null) {
            redirectAttributes.addFlashAttribute("mess", "Cầu thủ không tồn tại!");
            return "redirect:/cauthu/list";
        }

        List<CauThu> favoriteList = (List<CauThu>) session.getAttribute("favoriteList");
        if (favoriteList == null) {
            favoriteList = new ArrayList<>();
        }
        boolean exists = favoriteList.stream()
                .anyMatch(ct -> ct.getId() == cauThu.getId());
        if (!exists) {
            favoriteList.add(cauThu);
            redirectAttributes.addFlashAttribute("mess", "Đã thêm vào yêu thích!");
        } else {
            redirectAttributes.addFlashAttribute("mess", "Cầu thủ này đã có trong danh sách yêu thích!");
        }

        session.setAttribute("favoriteList", favoriteList);
        return "redirect:/cauthu/list";
    }


}

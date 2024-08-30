package com.joyfarm.farmstival.member.admin.controllers;

import com.joyfarm.farmstival.global.ListData;
import com.joyfarm.farmstival.global.Pagination;
import com.joyfarm.farmstival.global.Utils;
import com.joyfarm.farmstival.member.admin.services.AllMemberConfigInfoService;
import com.joyfarm.farmstival.member.admin.services.MemberConfigSaveService;
import com.joyfarm.farmstival.member.admin.services.MemberDeleteService;
import com.joyfarm.farmstival.member.constants.Authority;
import com.joyfarm.farmstival.member.controllers.MemberSearch;
import com.joyfarm.farmstival.member.entities.Member;
import com.joyfarm.farmstival.member.repositories.MemberRepository;
import com.joyfarm.farmstival.menus.Menu;
import com.joyfarm.farmstival.menus.MenuDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class AdminController  {
    private final AllMemberConfigInfoService memberConfigInfoService;
    private final MemberConfigSaveService memberConfigSaveService;
    private final MemberDeleteService memberDeleteService;
    private final HttpServletRequest request;

    private final Utils utils;
    private final MemberRepository memberRepository;


    @ModelAttribute("menuCode")
    public String getMenuCode() { // 주 메뉴 코드
        return "member";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() { // 서브 메뉴
        return Menu.getMenus("member");
    }

    @ModelAttribute("memberAuthorities")
    public List<String[]> memberAuthorities() {
        return Authority.getList(); //enum 상수 String으로...
    }

    @GetMapping
    public String index(@ModelAttribute MemberSearch search, Model model){

        commonProcess("list", model);

        //검색조건이 존재하면 검색 수행
        if(search != null && StringUtils.hasText(search.getSopt()) && StringUtils.hasText(search.getSkey())){
            List<Member> searchResults = memberConfigInfoService.searchMembers(search);
            model.addAttribute("items", searchResults);
        } else { //전체 조회
            ListData<Member> data = memberConfigInfoService.getList(search);
            List<Member> items = data.getItems();
            model.addAttribute("items", items);
            Pagination pagination = data.getPagination();
            model.addAttribute("pagination", pagination);
        }

        return "member/manage";
    }

    @GetMapping("/edit/{email}")
    public String edit(@PathVariable("email") String email, @ModelAttribute RequestMember form, Model model){

        request.setAttribute("addCss", List.of("editForm"));

        commonProcess("edit", model);
        form = memberConfigInfoService.getForm(email);
        form.setAuthorities(form.getAuthorities());
        model.addAttribute("requestMember", form);
        return "member/edit";
    }

    @PostMapping("/save")
    public String save(@Valid RequestMember form, Errors errors, Model model){
        String mode = form.getMode();
        commonProcess(mode,model);
//        memberFormValidator.validate(member, errors);

        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(System.out::println);
            return "member/manage" + mode;
        }

        memberConfigSaveService.save(form);

        return "redirect:" + utils.redirectUrl("/member");

    }

    /**
     * 회원 목록 - 수정
     *
     * @param chks
     * @return
     */
    @PatchMapping//PATCH요청.. 자원의 일부를 변경하고자 할 때 사용
    public String editList(@RequestParam("chk") List<Integer> chks, Model model) {
        //회원 목록에서 chk이름의 체크박스가 선택되면 폼 제출될때 해당 값 chks 리스트에 받아옴

        commonProcess("list", model);

        memberConfigSaveService.saveList(chks);

        model.addAttribute("script", "parent.location.reload()");
        return "common/_execute_script";
    }

    @DeleteMapping
    public String deleteList(@RequestParam("chk") List<Integer> chks, Model model) {
        commonProcess("list", model);

        memberDeleteService.deleteList(chks);

        model.addAttribute("script", "parent.location.reload();");
        return "common/_execute_script";
    }

    /**
     * 공통 처리
     * @param mode
     * @param model
     */
    private void commonProcess(String mode, Model model){
        String pageTitle = "회원 목록";

        mode = StringUtils.hasText(mode) ? mode : "list";

        if (mode.equals("add")) { //페이지 제목 동적으로 설정
            pageTitle = "회원 등록";

        } else if (mode.equals("edit")) {
            pageTitle = "회원 수정";

        }

        List<String> addScript = new ArrayList<>();

        model.addAttribute("pageTitle", pageTitle); //템플릿에서 ${pageTitle}로 참조
        model.addAttribute("subMenuCode", mode); //활성화된 서브 메뉴
    }
}

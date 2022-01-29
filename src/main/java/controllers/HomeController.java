package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/headers")
    public String headers(@RequestHeader(value="Host") String host,
                          @RequestHeader(value="User-Agent") String userAgent,
                          Model model) {
        List<String> dataList = new ArrayList<>();
        dataList.add("These are the headers for this request");
                dataList.add("Host: ".concat(host));
        dataList.add("User-Agent:".concat(userAgent));
        model.addAttribute("dataList", dataList);
        return "home/sandbox";
    }

    //provide access to request details: headers, request and session attributes.
    @GetMapping("/request")
    public String webRequest(WebRequest webRequest, Model model){
        List<String> dataList = new ArrayList<>();
        dataList.add("These are the details of this request: ");
        dataList.add(webRequest.getContextPath());
        webRequest.getHeaderNames().forEachRemaining(dataList:: add);
        dataList.add(webRequest.getDescription(true));
        model.addAttribute("dataList", dataList);
        return "home/sandbox";
    }

    @GetMapping("/response")
    public String webResponse(HttpServletResponse response, Model model){
        List<String> dataList = new ArrayList<>();
        dataList.add("Response was modified. Check the cookies.");
        model.addAttribute("dataList", dataList);
        response.addCookie(new Cookie("SANDBOX_COOKIE",
                "Delicious"));
        return "home/sandbox";
    }

    @GetMapping("/verifyRedirection")
    public String redirectData(final RedirectAttributes redirectAttributes) {
        List<String> dataList = new ArrayList<>();
        dataList.add("Data from HomeController.redirectData");
        redirectAttributes.addFlashAttribute("dataList",
                dataList);
        return "redirect:/home/data-list";
    }

    @GetMapping(value="/data-list")
    public String listData(@ModelAttribute("dataList") ArrayList<String> content) {
        return "home/sandbox";
    }
}
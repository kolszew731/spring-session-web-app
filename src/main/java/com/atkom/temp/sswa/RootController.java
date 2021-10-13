package com.atkom.temp.sswa;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RootController {

    private final static Logger logger = LogManager.getLogger(RootController.class);

    @Autowired
    private LocalHttpSessionStorage localSession;

    @SuppressWarnings("SameReturnValue")
    @GetMapping("/")
    public String process(Model model, HttpServletRequest request) {
        long l1 = System.nanoTime();
        HttpSession session = request.getSession();
        long l2 = System.nanoTime();
        long ld = l2 - l1;
        logger.info(ld);
        String sessionId = session.getId();

        @SuppressWarnings("unchecked")
        List<String> clusteredMessages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
        if (clusteredMessages == null) {
            clusteredMessages = new ArrayList<>();
            session.setAttribute("MY_SESSION_MESSAGES", clusteredMessages);
        }

        @SuppressWarnings("unchecked")
        List<String> localMessages = (List<String>) localSession.getAttribute(sessionId, "MY_SESSION_MESSAGES");
        if (localMessages == null) {
            localMessages = new ArrayList<>();
            localSession.setAttribute(sessionId, "MY_SESSION_MESSAGES", localMessages);
        }

        model.addAttribute("clMessages", clusteredMessages);
        model.addAttribute("clMessagesCl", "objID: " + Integer.toHexString(System.identityHashCode(clusteredMessages)));

        model.addAttribute("llMessages", localMessages);
        model.addAttribute("llMessagesCl", "objID: " + Integer.toHexString(System.identityHashCode(localMessages)));

        return "index";
    }

    @SuppressWarnings("SameReturnValue")
    @PostMapping("/persistMessage")
    public String persistMessage(@RequestParam("msg") String msg, HttpServletRequest request) {
        String sessionId = request.getSession().getId();

        @SuppressWarnings("unchecked")
        List<String> clusteredMessages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
        clusteredMessages.add(msg);
        request.getSession().setAttribute("MY_SESSION_MESSAGES", clusteredMessages);

        @SuppressWarnings("unchecked")
        List<String> localMessages = (List<String>) localSession.getAttribute(sessionId, "MY_SESSION_MESSAGES");
        localMessages.add(msg);

        return "redirect:/";
    }

    @SuppressWarnings("SameReturnValue")
    @PostMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }


}
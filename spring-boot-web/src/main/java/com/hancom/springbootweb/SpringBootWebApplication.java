package com.hancom.springbootweb;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SpringBootApplication
public class SpringBootWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebApplication.class, args);
	}
}

@Controller
class ViewController {
    @GetMapping("/get-pdfviewer")
    public RedirectView redirectToDocumentViewer(
        @RequestParam("lbryDocMtNo") String _lbryDocMtNo,
        @RequestParam("userTpcd") String userTpcd,
        @RequestParam("userId") String userId,
        @RequestParam("cstmSgn") String cstmSgn, 
        @RequestParam("dvsnCd") String dvsnCd
    ) {
        class UserBscsInfoVo {
            public String userTpcd;
            public String userId;
            public String cstmSgn;
            public String dvsnCd;
            public String sessionId;
            
            public UserBscsInfoVo(
                String userTpcd,
                String userId,
                String cstmSgn,
                String dvsnCd,
                String sessionId
            ) {
                this.userTpcd = userTpcd;
                this.userId = userId;
                this.cstmSgn = cstmSgn;
                this.dvsnCd = dvsnCd;
                this.sessionId = sessionId;        
            }
        }

        // 콘솔 메시지 출력
        System.out.printf(" lbryDocMtNo: %s, userTpcd: %s, userId: %s, cstmSgn: %s, dvsnCd: %s", 
            _lbryDocMtNo, 
            userTpcd,
            userId,
            cstmSgn,
            dvsnCd
        );

        String lbryDocMtNo = _lbryDocMtNo;
        String dwnlPath = "dwnlPath";
        String sessionId = "6";
        UserBscsInfoVo nowUserBlngInfo = new UserBscsInfoVo(
            userTpcd,
            userId,
            cstmSgn,
            dvsnCd, 
            sessionId
        );

        // PDF사본 다운로드(http)
        // String fullFilePath = getPDFForExternal(
        //     lbryDocMtNo,
        //     dwnlPath,
        //     nowUserBlngInfo
        // );

        // 네이버로 리다이렉트
        return new RedirectView("https://www.hancom.com");
    }

    @PostMapping("/post-pdfviewer")
    @ResponseBody
    public Map<String, String> processFormData(
        @RequestBody Map<String, String> formData
    ) {
        // 콘솔 메시지 출력
        System.out.println("FormData: " + formData);
        Set<Map.Entry<String, String>> entrySet = formData.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            System.out.println("entry: " + entry);
        }
        Map<String, String> response = new HashMap<>();
        response.put("url", "https://www.hancom.com");
        return response;
    }

    @PostMapping("/link")
    @ResponseBody
    public Map<String, String> handleFormSubmit(
        @RequestParam Map<String, String> formData
    ) {
        // 콘솔 메시지 출력
        System.out.println("FormData: " + formData);
        Set<Map.Entry<String, String>> entrySet = formData.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            System.out.println("entry: " + entry);
        }
        Map<String, String> response = new HashMap<>();
        response.put("url", "https://www.hancom.com");
        return response;
    }
}
